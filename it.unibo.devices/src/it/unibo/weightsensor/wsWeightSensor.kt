package it.unibo.weightsensor

import it.unibo.utility.WebSocketUpdater
import org.json.JSONObject

class WsWeightSensor(id : String, address : String) : AbstractWeightSensor(id) {
	
	private val updater = WebSocketUpdater<Double>(0.0, address, {
		s : String -> JSONObject(s).getDouble("data")
	})
	
	init {
		updater.start()
	}
	
	override fun readWeight() : Double {
		return updater.get()
	}
}