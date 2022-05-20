package it.unibo.parkingmanagerservice.entity

enum class Error {
	EMPTY_EMAIL, MAIL_EXISTS, INVALID_EMAIL,
	NO_RESERVATION, NO_USER_AT_DOOR, NO_DOOR_RESERVED,
	INVALID_TOKEN, INVALID_SLOTNUM
}

data class ParkingManagerServiceError (
	val error : Error,
	val msg : String
) {}