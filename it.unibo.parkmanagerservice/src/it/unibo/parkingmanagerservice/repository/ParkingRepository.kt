package it.unibo.parkingmanagerservice.repository

import it.unibo.parkingmanagerservice.repository.user.IUserRepository
import it.unibo.parkingmanagerservice.repository.parkingslot.IParkingSlotRepository
import it.unibo.parkingmanagerservice.repository.user.UserRepository
import it.unibo.parkingmanagerservice.repository.parkingslot.ParkingSlotRepository
import it.unibo.parkingmanagerservice.entity.parkingslot.ParkingSlot
import it.unibo.parkingmanagerservice.entity.parkingslot.ParkingSlotState
import it.unibo.parkingmanagerservice.repository.door.IDoorQueueRepository
import it.unibo.parkingmanagerservice.repository.door.DoorQueueRepository
import it.unibo.parkingmanagerservice.entity.door.DoorType
import it.unibo.parkingmanagerservice.repository.door.IDoorManagerRepository
import it.unibo.parkingmanagerservice.repository.door.DoorManagerRepository

object ParkingRepository {
	@JvmStatic private var userRepository : IUserRepository? = null
	@JvmStatic private var parkingSlotRepository : IParkingSlotRepository? = null
	@JvmStatic private var indoorQueueRepository : IDoorQueueRepository? = null
	@JvmStatic private var outdoorQueueRepository : IDoorQueueRepository? = null
	@JvmStatic private var doorManagerRepository : IDoorManagerRepository? = null
		
	@JvmStatic fun createParking(num : Int) {
		if (null == userRepository)
			userRepository = UserRepository()
		
		if (null == parkingSlotRepository)
			parkingSlotRepository = ParkingSlotRepository()
		
		for (i in 1..num) {
			parkingSlotRepository?.create(ParkingSlot(i.toLong(), ParkingSlotState.FREE, null))
		}
	}
	
	@JvmStatic fun getUserRepository() : IUserRepository {
		if (null == userRepository)
			userRepository = UserRepository()
		
		return userRepository!!
	}
	
	@JvmStatic fun getParkingSlotRepository() : IParkingSlotRepository {
		if (null == parkingSlotRepository)
			parkingSlotRepository = ParkingSlotRepository()
		
		return parkingSlotRepository!!
	}
	
	@JvmStatic fun getQueueRepository(doorType : DoorType) : IDoorQueueRepository {
		return when(doorType) {
			DoorType.INDOOR -> getIndoorQueueRepository()
			DoorType.OUTDOOR -> getOutdoorQueueRepository()
		}
	}
	
	@JvmStatic fun getIndoorQueueRepository() : IDoorQueueRepository {
		if (null == indoorQueueRepository)
			indoorQueueRepository = DoorQueueRepository()
		
		return indoorQueueRepository!!
	}
	
	@JvmStatic fun getOutdoorQueueRepository() : IDoorQueueRepository {
		if (null == outdoorQueueRepository)
			outdoorQueueRepository = DoorQueueRepository()
		
		return outdoorQueueRepository!!
	}
	
	@JvmStatic fun getDoorManagerRepository() : IDoorManagerRepository {
		if (null == doorManagerRepository)
			doorManagerRepository = DoorManagerRepository()
		
		return doorManagerRepository!!
	}
}