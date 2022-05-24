package it.unibo.parkingmanagerservice.utility

import it.unibo.parkingmanagerservice.entity.trolley.TripStage
import it.unibo.parkingmanagerservice.entity.trolley.TripStageType

class TripBuilder(inX : Int, inY : Int, outX : Int, outY : Int) {
	private var inPosition = Pair(inX, inY)
	private var outPosition = Pair(outX, outY)
	private var stages = ArrayDeque<TripStage>()
	
	fun addStage(type : TripStageType, xDest : Int, yDest : Int) : TripBuilder {
        stages.add(TripStage(type, Pair(xDest, yDest)))
        return this
    }

    fun addLoadCar() : TripBuilder {
        stages.add(TripStage(TripStageType.LOAD_CAR, Pair(-1, -1)))
        return this
    }

    fun addUnloadCar() : TripBuilder {
        stages.add(TripStage(TripStageType.UNLOAD_CAR, Pair(-1, -1)))
        return this
    }

    fun addReturnToHome() : TripBuilder {
        stages.add(TripStage(TripStageType.MOVING_TO_HOME, Pair(0, 0)))
        return this
    }

    fun addMoveToSlot(x : Int, y : Int) : TripBuilder {
        stages.add(TripStage(TripStageType.MOVING_TO_SLOT, Pair(x, y)))
        return this
    }

    fun addMoveToIn() : TripBuilder {
        stages.add(TripStage(TripStageType.MOVING_TO_IN, inPosition))
        return this
    }

    fun addMoveToOut() : TripBuilder {
        stages.add(TripStage(TripStageType.MOVING_TO_OUT, outPosition))
        return this
    }

    fun clear() : TripBuilder {
        stages.clear()
        return this
    }

    fun addParkTrip(parkX : Int, parkY : Int) : TripBuilder {
        return this.addMoveToIn().addLoadCar().addMoveToSlot(parkX, parkY)
                        .addUnloadCar().addReturnToHome()
    }

    fun addPickupTrip(pickX : Int, pickY : Int) : TripBuilder {
        return this.addMoveToSlot(pickX, pickY).addLoadCar()
                        .addMoveToOut().addUnloadCar().addReturnToHome()
    }

    fun build() : Iterator<TripStage> {
        return stages.iterator()
    }
}