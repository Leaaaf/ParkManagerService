package it.unibo.parkingmanagerservice.repository.parkingslot

import it.unibo.parkingmanagerservice.entity.parkingslot.ParkingSlot
import it.unibo.parkingmanagerservice.entity.parkingslot.ParkingSlotState
import java.util.Collections

class ParkingSlotRepository : IParkingSlotRepository {
	private val parkingSlotMap = mutableMapOf<Long, ParkingSlot>()
	
	override fun create(parkingSlot : ParkingSlot) {
		parkingSlotMap.put(parkingSlot.id, parkingSlot)
	}
	
	override fun update(parkingSlot : ParkingSlot) {
		parkingSlotMap.set(parkingSlot.id, parkingSlot)
	}
	
	override fun delete(id : Long) {
		parkingSlotMap.remove(id)
	}
	
	override fun getAll() : Collection<ParkingSlot> {
		return Collections.unmodifiableCollection(parkingSlotMap.values)
	}
	
	override fun getBySlotNum(id : Long) : ParkingSlot? {
		return parkingSlotMap.get(id)
	}
	
	override fun getByState(state : ParkingSlotState) : Collection<ParkingSlot> {
		return parkingSlotMap.values.filter { it.state.equals(state) }
	}
	
	override fun getReservedByUser(userId : Long) : ParkingSlot? {
		return parkingSlotMap.values.find { it.user?.id == userId }
	}
	
	override fun getReservedByToken(token : String) : ParkingSlot? {
		return parkingSlotMap.values.find { it.user?.token?.equals(token) ?: false }
	}
}