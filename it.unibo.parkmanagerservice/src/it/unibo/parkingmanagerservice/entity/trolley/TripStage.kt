package it.unibo.parkingmanagerservice.entity.trolley

data class TripStage (
	val type : TripStageType,
	val destination : Pair<Int, Int>
)