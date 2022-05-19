package it.unibo.parkingmanagerservice.usecase

import it.unibo.parkingmanagerservice.repository.door.IDoorQueueRepository
import it.unibo.parkingmanagerservice.repository.door.IDoorManagerRepository
import it.unibo.parkingmanagerservice.entity.user.User
import it.unibo.parkingmanagerservice.entity.door.DoorType
import it.unibo.parkingmanagerservice.entity.parkingslot.ParkingSlot
import it.unibo.parkingmanagerservice.entity.ParkManagerServiceError
import it.unibo.parkingmanagerservice.entity.user.UserState
import java.sql.SQLException
import it.unibo.parkingmanagerservice.entity.door.DoorState
import it.unibo.parkingmanagerservice.entity.parkingslot.ParkingSlotState
import it.unibo.parkingmanagerservice.entity.Error
import it.unibo.parkingmanagerservice.utility.token.ITokenGenerator
import java.lang.IllegalStateException
import it.unibo.parkingmanagerservice.repository.user.IUserRepository
import it.unibo.parkingmanagerservice.repository.parkingslot.IParkingSlotRepository

class ParkManagerServiceUseCase (userRepository : IUserRepository, parkingSlotRepository : IParkingSlotRepository,
		indoorQueueRepository : IDoorQueueRepository, outdoorQueueRepository : IDoorQueueRepository,
		doorManagerRepository : IDoorManagerRepository) : IParkManagerServiceUseCase {

	private val userRepository = userRepository
	private val parkingSlotRepository = parkingSlotRepository
	private val indoorQueueRepository = Pair<DoorType, IDoorQueueRepository>(DoorType.INDOOR, indoorQueueRepository)
	private val outdoorQueueRepository = Pair<DoorType, IDoorQueueRepository>(DoorType.OUTDOOR, outdoorQueueRepository)
	private val doorManagerRepository = doorManagerRepository
	private val tokenGenerator = ITokenGenerator.get()
	
	companion object {
		@JvmStatic private var parkManagerServiceUseCase : IParkManagerServiceUseCase? = null

		@JvmStatic fun create(userRepository : IUserRepository,
							  parkingSlotRepository : IParkingSlotRepository,
							  indoorQueueRepository : IDoorQueueRepository,
							  outdoorQueueRepository : IDoorQueueRepository,
							  doorManagerRepository : IDoorManagerRepository) {
			
			parkManagerServiceUseCase = ParkManagerServiceUseCase(userRepository, parkingSlotRepository,
					indoorQueueRepository, outdoorQueueRepository, doorManagerRepository)
		}
		
		@JvmStatic fun get() : IParkManagerServiceUseCase {
			if (null == parkManagerServiceUseCase) {
				throw IllegalStateException("ParkManagerServiceUseCase | Create ParkManagerServiceUseCase first.")
			}
			return parkManagerServiceUseCase!!
		}
	}
	
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
		var parkingSlot = parkingSlotRepository.getByState(ParkingSlotState.FREE).firstOrNull()
		
		if (null != parkingSlot) {
			parkingSlot.state = ParkingSlotState.RESERVED
			parkingSlot.user = user
			parkingSlotRepository.update(parkingSlot)
			
			user.state = UserState.ENTER_INTEREST
			userRepository.update(user)
			
			println("ParkManagerServiceUseCase | setSlotToUser | Reserved parking slot ${parkingSlot.id} for user ${user.email}")
			return parkingSlot.id
		}
		
		println("ParkManagerServiceUseCase | setSlotToUser | There aren't available parking slots")
		return 0
	}
	
	override fun putUserReserveDoorQueue(user : User, doorType : DoorType) {	
		when(doorType) {
			DoorType.INDOOR -> {
				indoorQueueRepository.component2().addUser(user)
				user.state = UserState.ENTER_INTEREST
			}
			DoorType.OUTDOOR -> {
				outdoorQueueRepository.component2().addUser(user)
				user.state = UserState.OUT_INTEREST
			}
		}
		
		println("ParkManagerServiceUseCase | putUserReserveDoorQueue | Enqueued user ${user.email} to ${doorType.toString()} queue")
	}
	
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
			val expectedState = UserState.getIntrestByDoorType(doorType)
			
			if (user!!.state != expectedState && queue.getSize() > 0) {
				user = queue.pullUser()
			}
			
			if (user!!.state == expectedState) {
				doorManagerRepository.reserveDoorUser(user, doorType)
				user.state = UserState.getReservationByDoorType(doorType)
				userRepository.update(user)
				
				println("ParkManagerServiceUseCase | reserveDoor | Reserved ${doorType.toString()} to user ${user.email}")
			}
		}
		
		return user
	}
	
	override fun setDoorOccupied(doorType : DoorType) : User? {
		doorManagerRepository.setState(doorType, DoorState.OCCUPIED)
		return doorManagerRepository.getUserByDoorType(doorType)
	}
	
	override fun setDoorFree(doorType : DoorType) {
		doorManagerRepository.setDoorFreeNoUser(doorType)
	}
	
	override fun setSlotFree() : Pair<User?, ParkingSlot?> {
		val user = doorManagerRepository.getUserByDoorType(DoorType.OUTDOOR)
		
		if (null == user) {
			println("ParkManagerServiceUseCase | setSlotFree | Unable to free parking slot: no user at the outdoor")
			return Pair(null, null)
		}
		
		var parkingSlot = parkingSlotRepository.getReservedByUser(user.id)		
		if (null != parkingSlot) {
			parkingSlot.state = ParkingSlotState.FREE
			parkingSlot.user = null
			parkingSlotRepository.update(parkingSlot)
			
			println("ParkManagerServiceUseCase | setSlotFree | Parking slot ${parkingSlot.id} is now free")
			return Pair(user, parkingSlot)
		}
		
		println("ParkManagerServiceUseCase | setSlotFree | No parking slot has been released")
		return Pair(user, null)
	}
	
	override fun getSlotReservedByUser(user : User) : ParkingSlot? {
		return parkingSlotRepository.getReservedByUser(user.id)
	}
	
	override fun parkFromIndoor() : User? {
		val user = doorManagerRepository.getUserByDoorType(DoorType.INDOOR)
		
		if (DoorState.OCCUPIED == doorManagerRepository.getState(DoorType.INDOOR) && null != user) {
			doorManagerRepository.setDoorFreeNoUser(DoorType.INDOOR)
			user.state = UserState.PARKED
			userRepository.update(user)
			
			var parkingSlot = parkingSlotRepository.getReservedByUser(user.id)
			parkingSlot!!.state = ParkingSlotState.OCCUPIED
			parkingSlotRepository.update(parkingSlot)
			
			println("ParkManagerServiceUseCase | parkFromIndoor | Parked car for user ${user.email}")
			return user
		}
		
		return null
	}
	
	override fun leaveParkOutdoor() : User? {
		val user = doorManagerRepository.getUserByDoorType(DoorType.OUTDOOR)
		
		if (DoorState.OCCUPIED == doorManagerRepository.getState(DoorType.OUTDOOR) && null != user) {
			doorManagerRepository.setDoorFreeNoUser(DoorType.OUTDOOR)
			user.state = UserState.PICKEDUP
			userRepository.update(user)
			
			println("ParkManagerServiceUseCase | leaveParkOutdoor | Picked up car for user ${user.email}")
			return user
		}
		
		return null
	}
	
	override fun setTokenUserIndoor(slotnum : String, email : String) : Pair<User?, ParkManagerServiceError?> {
		val user = doorManagerRepository.getUserByDoorType(DoorType.INDOOR)
		
		if (null == user) {
			println("ParkManagerServiceUseCase | setTokenUserIndoor | Unable to assign token: no user at indoor")
			return Pair(null, ParkManagerServiceError(Error.NO_DOOR_RESERVED, "Indoor is not reserved. Please take a reservation."))
		}
		
		val parkingSlot = parkingSlotRepository.getReservedByUser(user.id)
		if (null == parkingSlot) {
			println("ParkManagerServiceUseCase | setTokenUserIndoor | Unable to assign token: user into the indoor has no slot reserved")
			return Pair(null, ParkManagerServiceError(Error.NO_RESERVATION, "Parking slot is not reserved. Please take a reservation."))
		}
		
		if (DoorState.OCCUPIED == doorManagerRepository.getState(DoorType.INDOOR)) {
			if (user.email.equals(email) && parkingSlot.id == slotnum.toLong()) {
				user.token = tokenGenerator.generateToken(user, parkingSlot)
				userRepository.update(user)
				
				println("ParkManagerServiceUseCase | setTokenUserIndoor | Assigned token ${user.token} to user ${email}")
				return Pair(user, null)
			}
			println("ParkManagerServiceUseCase | setTokenUserIndoor | Invalid email or slotnum")
			return Pair(user, ParkManagerServiceError(Error.INVALID_EMAIL, "Invalid email or slotnum. Please insert correct data."))	
		}
		
		println("ParkManagerServiceUseCase | setTokenUserIndoor | Unable to assign token: ${user.email} car isn't at the indoor")
		return Pair(user, ParkManagerServiceError(Error.NO_USER_AT_DOOR, "Please move the car to indoor before press ENTER CAR."))
	}
	
	override fun validateToken(token : String, email : String) : Pair<ParkingSlot?, ParkManagerServiceError?> {
		val parkingSlot = parkingSlotRepository.getReservedByToken(token)
		
		if (null == parkingSlot) {
			println("ParkManagerServiceUseCase | validateToken | Unable to find slot assigned to $email with token: $token")
            return Pair(null, ParkManagerServiceError(Error.INVALID_TOKEN, "Invalid token. Please try again."))
		}
		
		val user = parkingSlot.user!!
		
		if (!user.email.equals(email)) {
			println("ParkManagerServiceUseCase | validateToken | $email not valid")
            return Pair(parkingSlot, ParkManagerServiceError(Error.INVALID_EMAIL, "Invalid email. Please try again."))
		}
		if (user.email.equals(email) && ParkingSlotState.RELEASE == parkingSlot.state) {
			if (doorManagerRepository.getUserByDoorType(DoorType.OUTDOOR) == user)
				return Pair(parkingSlot, ParkManagerServiceError(Error.INVALID_TOKEN, "Pick-up is already requested. Please move your car to outdoor."))
			else
				return Pair(parkingSlot, ParkManagerServiceError(Error.INVALID_TOKEN, "Pick-up is already requested and outdoor already engaged. Please wait."))
		}
		
		println("ParkManagerServiceUseCase | validateToken | Token OK")
		parkingSlot.state = ParkingSlotState.RELEASE
		parkingSlotRepository.update(parkingSlot)
		
		return Pair(parkingSlot, null)
	}
}













