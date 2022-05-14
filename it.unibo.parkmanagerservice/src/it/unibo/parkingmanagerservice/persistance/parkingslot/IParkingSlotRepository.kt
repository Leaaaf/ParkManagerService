package it.unibo.parkingmanagerservice.persistance.parkingslot

import it.unibo.parkingmanagerservice.entity.parkingslot.ParkingSlot
import it.unibo.parkingmanagerservice.entity.parkingslot.ParkingSlotState

interface IParkingSlotRepository {
	// CUD
	fun create(parkingSlot : ParkingSlot)
	fun update(parkingSlot : ParkingSlot)
	fun delete(id : Long)
	
	fun getAll() : Collection<ParkingSlot>
	fun getBySlotNum(id: Long) : ParkingSlot?
	fun getByState(state : ParkingSlotState) : Collection<ParkingSlot>
	fun getReservedByUser(userId : Long) : ParkingSlot?
	fun getReservedByToken(token : String) : ParkingSlot?
}