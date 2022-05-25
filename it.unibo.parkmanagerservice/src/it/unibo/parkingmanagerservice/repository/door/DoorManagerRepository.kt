package it.unibo.parkingmanagerservice.repository.door

import it.unibo.parkingmanagerservice.entity.door.DoorState
import it.unibo.parkingmanagerservice.entity.user.User
import it.unibo.parkingmanagerservice.entity.door.DoorType

class DoorManagerRepository : IDoorManagerRepository {
	private var indoor = Pair<DoorState, User?>(DoorState.FREE, null)
	private var outdoor = Pair<DoorState, User?>(DoorState.FREE, null)
	
	override fun getState(doorType : DoorType) : DoorState {
		return when(doorType) {
			DoorType.INDOOR -> indoor.first
			DoorType.OUTDOOR -> outdoor.first
		}
	}
	
	override fun setState(doorType : DoorType, doorState : DoorState) {
		when(doorType) {
			DoorType.INDOOR -> indoor = indoor.copy(first=doorState)
			DoorType.OUTDOOR -> outdoor = outdoor.copy(first=doorState)
		}
	}
	
	override fun getUserByDoorType(doorType : DoorType) : User? {
		return when(doorType) {
			DoorType.INDOOR -> indoor.second
			DoorType.OUTDOOR -> outdoor.second
		}
	}
	
	override fun setUserAtDoorType(user : User, doorType : DoorType) {
		when(doorType) {
			DoorType.INDOOR -> indoor = indoor.copy(second=user)
			DoorType.OUTDOOR -> outdoor = outdoor.copy(second=user)
		}
	}
	
	override fun reserveDoorUser(user : User, doorType : DoorType) {
		when(doorType) {
			DoorType.INDOOR -> indoor = indoor.copy(first=DoorState.RESERVED, second=user)
			DoorType.OUTDOOR -> outdoor = outdoor.copy(first=DoorState.RESERVED, second=user)
		}
	}
	
	override fun setDoorFreeNoUser(doorType : DoorType) {
		when(doorType) {
			DoorType.INDOOR -> indoor = indoor.copy(first=DoorState.FREE, second=null)
			DoorType.OUTDOOR -> outdoor = outdoor.copy(first=DoorState.FREE, second=null)
		}
	}
}