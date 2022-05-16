package it.unibo.parkingmanagerservice.usecase

import it.unibo.parkingmanagerservice.entity.user.User
import it.unibo.parkingmanagerservice.entity.door.DoorType
import it.unibo.parkingmanagerservice.entity.ParkManagerServiceError
import it.unibo.parkingmanagerservice.entity.parkingslot.ParkingSlot
import it.unibo.parkingmanagerservice.repository.door.IDoorQueueRepository
import it.unibo.parkingmanagerservice.repository.door.IDoorManagerRepository

interface IParkManagerServiceUseCase {
	fun createUser(email : String) : User?
	fun deleteUser(user : User)
	fun setSlotToUser(user : User) : Long
	fun putUserReserveDoorQueue(user : User, doorType : DoorType)
	fun getReserveDoorQueue(doorType: DoorType) : IDoorQueueRepository
	fun getDoorManager() : IDoorManagerRepository
	fun reserveDoor(doorType: DoorType) : User?
	fun setDoorOccupied(doorType : DoorType) : User?
	fun setDoorFree(doorType : DoorType)
	fun setSlotFree() : Pair<User?, ParkingSlot?>
	fun getSlotReservedByUser(user : User) : ParkingSlot?
	fun parkFromIndoor() : User?
	fun leaveParkOutdoor() : User?
	fun setTokenUserIndoor(token : String, email : String) : Pair<User?, ParkManagerServiceError?>
	fun validateToken(token : String, email : String) : Pair<ParkingSlot?, ParkManagerServiceError?>
}