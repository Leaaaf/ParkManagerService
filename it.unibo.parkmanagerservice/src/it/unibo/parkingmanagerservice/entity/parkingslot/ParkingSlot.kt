package it.unibo.parkingmanagerservice.entity.parkingslot

import it.unibo.parkingmanagerservice.entity.user.User
import java.util.Optional

data class ParkingSlot (
	val id : Long,
	var state : ParkingSlotState,
	var user : User?
)