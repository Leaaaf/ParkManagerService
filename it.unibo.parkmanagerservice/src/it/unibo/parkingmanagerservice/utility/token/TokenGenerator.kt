package it.unibo.parkingmanagerservice.utility.token

import it.unibo.parkingmanagerservice.entity.parkingslot.ParkingSlot
import it.unibo.parkingmanagerservice.entity.user.User

class TokenGenerator : ITokenGenerator {
	override fun generateToken(user : User, parkingSlot : ParkingSlot) : String {
		return "U${user.id}S${parkingSlot.id}"
	} 
}