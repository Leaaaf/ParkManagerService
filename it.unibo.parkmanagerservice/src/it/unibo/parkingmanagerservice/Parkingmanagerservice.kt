/* Generated by AN DISI Unibo */ 
package it.unibo.parkingmanagerservice

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Parkingmanagerservice ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
				it.unibo.parkingmanagerservice.repository.ParkingRepository.createParking(6)
				it.unibo.parkingmanagerservice.usecase.ParkManagerServiceUseCase.create(
					it.unibo.parkingmanagerservice.repository.ParkingRepository.getUserRepository(),
					it.unibo.parkingmanagerservice.repository.ParkingRepository.getParkingSlotRepository(),
					it.unibo.parkingmanagerservice.repository.ParkingRepository.getIndoorQueueRepository(),
					it.unibo.parkingmanagerservice.repository.ParkingRepository.getOutdoorQueueRepository(),
					it.unibo.parkingmanagerservice.repository.ParkingRepository.getDoorManagerRepository()
				)
				
				val MANAGER = it.unibo.parkingmanagerservice.usecase.ParkManagerServiceUseCase.get()
				
				// Timers
				val timers = it.unibo.parkingmanagerservice.entity.timer.Timer.get()
				val ITOCC = timers.ITOCC
				val DTFREE = timers.DTFREE
				val INDOOR_POLLING = timers.INDOOR_POLLING
				val OUTDOOR_POLLING = timers.OUTDOOR_POLLING
				
				// ParkingSlot
				var SLOTNUM : Long = 0
				var PARKING_SLOT : it.unibo.parkingmanagerservice.entity.parkingslot.ParkingSlot?
				var PARKING_SLOT_ERROR : Pair<it.unibo.parkingmanagerservice.entity.parkingslot.ParkingSlot?,
												it.unibo.parkingmanagerservice.entity.ParkingManagerServiceError?>
				
				// User - ParkingSlot
				var USER_SLOT : Pair<it.unibo.parkingmanagerservice.entity.user.User?,
										it.unibo.parkingmanagerservice.entity.parkingslot.ParkingSlot?>
									
				// User
				var USER : it.unibo.parkingmanagerservice.entity.user.User?
				var USER_ERROR : Pair<it.unibo.parkingmanagerservice.entity.user.User?,
										it.unibo.parkingmanagerservice.entity.ParkingManagerServiceError?>
								
				// Door Type
				val INDOOR = it.unibo.parkingmanagerservice.entity.door.DoorType.INDOOR
				val OUTDOOR = it.unibo.parkingmanagerservice.entity.door.DoorType.OUTDOOR
								
				// Notify
												
				var PAYLOAD : String = ""
				
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("$name | Started")
						updateResourceRep( "{\"door\": \"indoor\", \"state\": \"FREE\"}"  
						)
						updateResourceRep( "{\"door\": \"outdoor\", \"state\": \"FREE\"}"  
						)
					}
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("work") { //this:State
					action { //it:State
						println("$name | Waiting for request...")
						updateResourceRep( "work"  
						)
					}
					 transition(edgeName="t0",targetState="handleEnter",cond=whenRequest("enter"))
					transition(edgeName="t1",targetState="handleEnterCar",cond=whenRequest("entercar"))
					transition(edgeName="t2",targetState="handlePickup",cond=whenRequest("pickup"))
					transition(edgeName="t3",targetState="handleIndoorOccupied",cond=whenEvent("weighton"))
					transition(edgeName="t4",targetState="handleIndoorFree",cond=whenEvent("weightoff"))
					transition(edgeName="t5",targetState="handleOutdoorOccupied",cond=whenEvent("outsonaron"))
					transition(edgeName="t6",targetState="handleOutdoorFree",cond=whenEvent("outsonaroff"))
				}	 
				state("handleEnter") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						 SLOTNUM = 0  
						if( checkMsgContent( Term.createTerm("enter(EMAIL)"), Term.createTerm("enter(EMAIL)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												try {
													USER = MANAGER.createUser(payloadArg(0))
													SLOTNUM = MANAGER.setSlotToUser(USER!!)
													
													if (SLOTNUM > 0) {
														MANAGER.putUserReserveDoorQueue(USER!!, INDOOR)
														if (MANAGER.reserveDoor(INDOOR) != null) {
															PAYLOAD = "{\"slotnum\": \"$SLOTNUM\", \"indoor\": \"FREE\", \"time\": \"${ITOCC}\"}"
								updateResourceRep( "{\"door\": \"indoor\", \"state\": \"RESERVED\"}"  
								)
								updateResourceRep( "{\"slot\": \"${SLOTNUM}\", \"user\": \"${USER!!.email}\", \"state\": \"RESERVED\"}"  
								)
								forward("dopolling", "dopolling($INDOOR_POLLING)" ,"weightsensoractor" ) 
								forward("startitocc", "startitocc(START)" ,"itoccactor" ) 
								 
														} else {
															PAYLOAD = "{\"slotnum\": \"$SLOTNUM\", \"indoor\": \"OCCUPIED\"}"
														}
													}
												} catch (e : java.sql.SQLException) {
													PAYLOAD = "{\"error\": \"${e.getLocalizedMessage()}\"}"
												}				
						}
						println("$name | Reply with slotnum: ${PAYLOAD!!}")
						answer("enter", "slotnum", "slotnum($PAYLOAD)"   )  
					}
				}	 
				state("handleEnterCar") { //this:State
					action { //it:State
					}
				}	 
				state("handlePickup") { //this:State
					action { //it:State
					}
				}	 
				state("handleIndoorOccupied") { //this:State
					action { //it:State
					}
				}	 
				state("handleIndoorFree") { //this:State
					action { //it:State
					}
				}	 
				state("handleOutdoorOccupied") { //this:State
					action { //it:State
					}
				}	 
				state("handleOutdoorFree") { //this:State
					action { //it:State
					}
				}	 
			}
		}
}
