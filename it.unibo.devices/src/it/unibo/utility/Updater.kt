package it.unibo.utility

import java.io.Closeable

interface Updater<out T> : Closeable, AutoCloseable {
	fun get() : T
	fun start()
	fun suspend()
	override fun close()
}