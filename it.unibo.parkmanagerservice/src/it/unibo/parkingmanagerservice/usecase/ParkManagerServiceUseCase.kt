package it.unibo.parkingmanagerservice.usecase

import it.unibo.parkingmanagerservice.repository.user.UserRepository
import it.unibo.parkingmanagerservice.repository.parkingslot.ParkingSlotRepository
import it.unibo.parkingmanagerservice.repository.door.IDoorQueueRepository
import it.unibo.parkingmanagerservice.repository.door.IDoorManagerRepository
import it.unibo.parkingmanagerservice.entity.user.User
import it.unibo.parkingmanagerservice.entity.door.DoorType
import it.unibo.parkingmanagerservice.entity.parkingslot.ParkingSlot
import it.unibo.parkingmanagerservice.entity.ParkManagerServiceError
import it.unibo.parkingmanagerservice.entity.user.UserState
import java.sql.SQLException
import it.unibo.parkingmanagerservice.entity.door.DoorState

class ParkManagerServiceUseCase (
	userRepository : UserRepository,
	parkingSlotRepository : ParkingSlotRepository,
	indoorQueueRepository : IDoorQueueRepository,
	outdoorQueueRepository : IDoorQueueRepository,
	doorManagerRepository : IDoorManagerRepository
) : IParkManagerServiceUseCase {
	
	private val userRepository = userRepository
	private val parkignSlotRepository = parkingSlotRepository
	private val indoorQueueRepository = Pair<DoorType, IDoorQueueRepository>(DoorType.INDOOR, indoorQueueRepository)
	private val outdoorQueueRepository = Pair<DoorType, IDoorQueueRepository>(DoorType.OUTDOOR, outdoorQueueRepository)
	private val doorManagerRepository = doorManagerRepository
	
	@Throws(SQLException::class)
	override fun createUser(email : String) : User? {
		println("ParkManagerServiceUseCase | createUser | email: $email")
		return userRepository.create(User(email=email, state=UserState.CREATED))
	}
	
	override fun deleteUser(user : User) {
		println("ParkManagerServiceUseCase | deleteUser | email: ${user.email}")
		userRepository.delete(user.id)		
	}
	
	override fun setSlotToUser(user : User) : Long {
		
		return 0
	}
	
	override fun putUserReserveDoorQueue(user : User, doorType : DoorType) {}
	
	override fun getReserveDoorQueue(doorType: DoorType) : IDoorQueueRepository {
		return when(doorType) {
			DoorType.INDOOR -> indoorQueueRepository.component2()
			DoorType.OUTDOOR -> outdoorQueueRepository.component2()
		}
	}
	
	override fun getDoorManager() : IDoorManagerRepository {
		return doorManagerRepository
	}
	
	override fun reserveDoor(doorType: DoorType) : User? {
		var user : User? = null
		val queue = getReserveDoorQueue(doorType)
		
		if (doorManagerRepository.getState(doorType) == DoorState.FREE && queue.getSize() > 0) {
			user = queue.pullUser()
		}
		
		return user
	}
	
	override fun setDoorOccupied(doorType : DoorType) : User? {
		return null
	}
	
	override fun setDoorFree(doorType : DoorType) {}
	
	override fun setSlotFree() : Pair<User?, ParkingSlot?> {
		return Pair(null, null)
	}
	
	override fun getSlotReservedByUser(user : User) : ParkingSlot? {
		return null
	}
	
	override fun parkFromIndoor() : User? {
		return null
	}
	
	override fun leaveParkOutdoor() : User? {
		return null
	}
	
	override fun setTokenUserIndoor(token : String, email : String) : Pair<User?, ParkManagerServiceError?> {
		return Pair(null, null)
	}
	
	override fun validateToken(token : String, email : String) : Pair<ParkingSlot?, ParkManagerServiceError?> {
		return Pair(null, null)
	}
}