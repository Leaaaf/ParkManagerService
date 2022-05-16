package it.unibo.parkingmanagerservice.repository

import it.unibo.parkingmanagerservice.repository.user.IUserRepository
import it.unibo.parkingmanagerservice.repository.parkingslot.IParkingSlotRepository
import it.unibo.parkingmanagerservice.repository.user.UserRepository
import it.unibo.parkingmanagerservice.repository.parkingslot.ParkingSlotRepository
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