package it.unibo.thermometer

object ThermometerFactory {
	fun create(id : String, address : String?) : AbstractThermometer? {
		return if (null != address) WsThermometer(id, address) else null
	}
}