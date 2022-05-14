package it.unibo.parkingmanagerservice.persistance

import it.unibo.parkingmanagerservice.persistance.user.IUserRepository
import it.unibo.parkingmanagerservice.persistance.parkingslot.IParkingSlotRepository
import it.unibo.parkingmanagerservice.persistance.user.UserRepository
import it.unibo.parkingmanagerservice.persistance.parkingslot.ParkingSlotRepository
import it.unibo.parkingmanagerservice.entity.parkingslot.ParkingSlot
import it.unibo.parkingmanagerservice.entity.parkingslot.ParkingSlotState

object ParkingRepository {
	@JvmStatic private var userRepository : IUserRepository? = null
	@JvmStatic private var parkingSlotRepository : IParkingSlotRepository? = null
	
	init {
		if (userRepository == null) 
			userRepository = UserRepository()
		
		if (parkingSlotRepository == null)
			parkingSlotRepository = ParkingSlotRepository()
	}
	
	@JvmStatic fun create(num : Int) {
		for (i in 1..num) {
			parkingSlotRepository?.create(ParkingSlot(i.toLong(), ParkingSlotState.FREE, null))
		}
	}
	
	@JvmStatic fun getUserRepository() : IUserRepository? {
		return userRepository
	}
	
	@JvmStatic fun getParkingSlotRepository() : IParkingSlotRepository? {
		return parkingSlotRepository
	}
}