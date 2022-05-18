package it.unibo.parkingmanagerservice.entity.user

import it.unibo.parkingmanagerservice.entity.door.DoorType

enum class UserState {
	CREATED, ENTER_INTEREST, INDOOR_RESERVED, PARKED, OUT_INTEREST, OUTDOOR_RESERVED, PICKEDUP;
	
	companion object {
		@JvmStatic fun getReservationByDoorType(doorType : DoorType) : UserState {
			return when(doorType) {
				DoorType.INDOOR -> UserState.INDOOR_RESERVED
				DoorType.OUTDOOR -> UserState.OUTDOOR_RESERVED
			}
		}
		
		@JvmStatic fun getIntrestByDoorType(doorType : DoorType) : UserState {
			return when(doorType) {
				DoorType.INDOOR -> UserState.ENTER_INTEREST
				DoorType.OUTDOOR -> UserState.OUT_INTEREST
			}
		}
	}
}