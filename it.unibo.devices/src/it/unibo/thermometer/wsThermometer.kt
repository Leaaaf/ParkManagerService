package it.unibo.thermometer

import org.json.JSONObject
import it.unibo.utility.WebSocketUpdater

class WsThermometer(id : String, address : String) : AbstractThermometer(id) {
	
	private val updater = WebSocketUpdater<Double>(0.0, address, {
		s : String -> JSONObject(s).getDouble("data")
	})
	
	init {
		updater.start()
	}
	
	override fun readTemperature() : Double {
		return updater.get()
	}
}