package it.unibo.outsonar

import it.unibo.utility.WebSocketUpdater
import org.json.JSONObject

class WsOutSonar(id : String, address : String) : AbstractOutSonar(id) {
	
	private val updater = WebSocketUpdater<Int>(0, address, {
		s : String -> JSONObject(s).getInt("data")
	})
	
	init {
		updater.start()
	}
	
	override fun readDistance() : Int {
		return updater.get()
	}
}