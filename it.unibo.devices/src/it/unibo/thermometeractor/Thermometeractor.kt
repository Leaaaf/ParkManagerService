/* Generated by AN DISI Unibo */ 
package it.unibo.thermometeractor

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Thermometeractor ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
				val thermometer = it.unibo.devices.DeviceManager.getDevice("thermometer")
				var tempState = `it.unibo.thermometer`.ThermometerState.NORMAL
				var temp = 0.0
				var POLLING_TIME = it.unibo.thermometer.AbstractThermometer.getPollingMs()
				var CRITICAL_TEMP = it.unibo.thermometer.AbstractThermometer.getCriticalTemp()
				var jsonData: String
				
				if (thermometer == null) {
					println("$name | Unable to use thermometer")
					System.exit(-1)
				}
				
				thermometer as it.unibo.thermometer.AbstractThermometer
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("$name | Started...")
					}
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("work") { //this:State
					action { //it:State
						
									temp = thermometer.readTemperature()
									jsonData = "{\"data\": \"${temp}\"}"
									if (temp >= CRITICAL_TEMP && tempState == `it.unibo.thermometer`.ThermometerState.NORMAL) {
										tempState = `it.unibo.thermometer`.ThermometerState.CRITICAL
						emit("criticaltemp", "criticaltemp(CRITICAL)" ) 
						
									} else if (temp < CRITICAL_TEMP && tempState == `it.unibo.thermometer`.ThermometerState.CRITICAL) {
										tempState = `it.unibo.thermometer`.ThermometerState.NORMAL
						emit("criticaltemp", "criticaltemp(NORMAL)" ) 
						
									} 
						updateResourceRep( jsonData  
						)
						stateTimer = TimerActor("timer_work", 
							scope, context!!, "local_tout_thermometeractor_work", POLLING_TIME )
					}
					 transition(edgeName="t06",targetState="work",cond=whenTimeout("local_tout_thermometeractor_work"))   
					transition(edgeName="t07",targetState="work",cond=whenDispatch("updatethermometer"))
				}	 
			}
		}
}
