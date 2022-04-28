package it.unibo.weightsensor

object WeightSensorFactory {
	fun create(id : String, address : String?) : AbstractWeightSensor? {
		return if (null != address) WsWeightSensor(id, address) else null
	}
}