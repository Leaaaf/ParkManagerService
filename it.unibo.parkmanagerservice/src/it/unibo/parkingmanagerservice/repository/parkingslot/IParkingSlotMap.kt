package it.unibo.parkingmanagerservice.repository.parkingslot

interface IParkingSlotMap {
	fun getAllSlotnum() : IntArray
	fun getSlotnumPosition(slotnum : String) : Pair<Int, Int>?
	fun getAdiacentAllowedPositionBySlotnum(slotnum : String) : Pair<Int, Int>?
	fun getSlotnumDirection(slotnum : String) : String?
	fun getDirection(x : Int, y : Int) : String? 
	fun isNearSlot(x : Int, y : Int) : Int?
}