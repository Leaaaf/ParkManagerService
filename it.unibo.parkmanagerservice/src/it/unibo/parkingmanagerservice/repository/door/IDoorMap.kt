package it.unibo.parkingmanagerservice.repository.door

import it.unibo.parkingmanagerservice.entity.door.DoorType

interface IDoorMap {
	fun getDoorPosition(door : DoorType) : Pair<Int, Int>?
	fun getAdiacentAllowedPositionByDoorType(door : DoorType) : Pair<Int, Int>?
	fun getDirectionByDoorType(door : DoorType) : String?
	fun getDirection(x : Int, y : Int) : String?
	fun isNearDoor(x : Int, y : Int) : DoorType?
}