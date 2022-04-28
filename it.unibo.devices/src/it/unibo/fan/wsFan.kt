package it.unibo.basicfan

import org.json.JSONObject
import it.unibo.utility.WebSocketUpdater
import it.unibo.fan.FanState
import it.unibo.fan.AbstractFan

class WsFan(id: String, address : String) : AbstractFan(id) {
	
	val updater = WebSocketUpdater<FanState>(FanState.OFF, address,
		{s : String -> FanState.valueOf(JSONObject(s).getString("data"))})
	
	init {
		updater.start()
	}
	
	override fun setState(state : FanState) {
		when(state) {
			FanState.ON -> updater.send("{\"data\": \"ON\"}")
			FanState.OFF -> updater.send("{\"data\": \"OFF\"}")
		}		
	}
	
	override fun powerOn() {
		updater.send("{\"data\": \"ON\"}")
	}
	
	override fun powerOff() {
		updater.send("{\"data\": \"OFF\"}")
	}
	
}