/* Generated by AN DISI Unibo */ 
package it.unibo.fanactor

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Fanactor ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
				val fan = it.unibo.devices.DeviceManager.getDevice("fan")
			 	lateinit var state : it.unibo.fan.FanState
			 	var json : String
			 	
			 	if (fan == null) {
			 		println("$name | Unable to use fan")
			 		System.exit(-1)
			 	}
			 	
			 	fan as it.unibo.fan.AbstractFan
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("$name | Started...")
						
									fan.powerOff()
									state = `it.unibo.fan`.FanState.OFF
					}
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("work") { //this:State
					action { //it:State
						 json = "{\"data\": \"${state}\"}"  
						updateResourceRep( state.toString()  
						)
						println("$name | Fan state: ${state.toString()}")
					}
					 transition(edgeName="t00",targetState="poweron",cond=whenDispatch("fanon"))
					transition(edgeName="t01",targetState="poweroff",cond=whenDispatch("fanoff"))
				}	 
				state("poweron") { //this:State
					action { //it:State
						
									fan.powerOn()
									state = `it.unibo.fan`.FanState.ON
					}
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("poweroff") { //this:State
					action { //it:State
						
									fan.powerOff()
									state = `it.unibo.fan`.FanState.OFF
					}
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
			}
		}
}
