package it.unibo.parkingmanagerservice.utility.token

import it.unibo.parkingmanagerservice.entity.user.User
import it.unibo.parkingmanagerservice.entity.parkingslot.ParkingSlot

interface ITokenGenerator {
	companion object {
		private var tokenGenerator : ITokenGenerator? = null
		
		fun get() : ITokenGenerator {
			if (null == tokenGenerator) {
				tokenGenerator = TokenGenerator()
			}
			return tokenGenerator!!
		}
	}
	
	fun generateToken(user : User, parkingSlot : ParkingSlot) : String
}