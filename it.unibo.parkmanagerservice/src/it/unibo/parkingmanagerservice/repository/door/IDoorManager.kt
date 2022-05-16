package it.unibo.parkingmanagerservice.repository.door

import it.unibo.parkingmanagerservice.entity.door.DoorType
import it.unibo.parkingmanagerservice.entity.door.DoorState
import it.unibo.parkingmanagerservice.entity.user.User

interface IDoorManagerRepository {
	fun getState(doorType : DoorType) : DoorState
	fun setState(doorType : DoorType, doorState : DoorState)
	fun getUserByDoorType(doorType : DoorType) : User?
	fun setUserAtDoorType(user : User, doorType : DoorType)
	fun reserveDoorUser(user : User, doorType : DoorType)
	fun setDoorFreeNoUser(doorType : DoorType)
}