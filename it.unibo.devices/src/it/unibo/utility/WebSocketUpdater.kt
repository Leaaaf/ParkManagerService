package it.unibo.utility

import okhttp3.WebSocket
import okhttp3.OkHttpClient
import okhttp3.WebSocketListener
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.locks.ReentrantLock
import okhttp3.Response
import okhttp3.Request

class WebSocketUpdater<T>(value : T, address : String, converter : (String) -> T) : AbstractUpdater<T>(value) {
	
	private var webSocket : WebSocket? = null
	private val address = address
	private val working = AtomicBoolean(false)
	private val listener = ValueWebSocketListener(this.value, converter)
	private val client = OkHttpClient()
				
	override fun start() {
		if (!working.getAndSet(true)) {
			val request = Request.Builder()
							.url(address)
							.build()
			webSocket = client.newWebSocket(request, listener)
			listener.waitConnected()
		}
	}
	
	override fun suspend() {
		if (working.getAndSet(false)) {
			webSocket?.close(1000, "app_terminated")
		}
	}
	
	override fun close() {
		suspend()
		client.dispatcher.executorService.shutdown()
		client.connectionPool.evictAll()
	}
	
	fun send(payload: String) {
		if (webSocket?.send(payload) == true) {
			println("WebSocketUpdater::${address} | Sended: ${payload}")
		}
	}
	
}

private class ValueWebSocketListener<T>(value : LockableVal<T>, converter : (String) -> T) : WebSocketListener() {
	
	lateinit var url : String
	val value = value
	val converter = converter
	val lock = ReentrantLock()
	val cond = lock.newCondition()
	var connected = false
	
	override fun onOpen(webSocket : WebSocket, response : Response) {
		url = response.request.url.toString()
		println("WebSocketUpdater::${url} | WebSocket connected")
		lock.lock()
		try {
			connected = true
			cond.signalAll()
		} finally {
			lock.unlock()
		}
	}

	override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
		println("WebSocketUpdater::${url} | WebSocket error: ${t.localizedMessage}")
	}
	
	fun waitConnected() {
		lock.lock()
		try {
			while(!connected)
				cond.await()
		} finally {
			lock.unlock()
		}
	}
	
	override fun onMessage(webSocket: WebSocket, payload: String) {
		println("WebSocketUpdater::${url} | Received ${payload}")
		value.safeSet(converter(payload))
	}
	
}