package it.unibo.devices

enum class DeviceType {
	FAN, WHEIGHT_SENSOR, THERMOMETER, OUTSONAR
}

abstract class AbstractDevice(id : String, deviceType : DeviceType) {
	private val id = id
	private val deviceType = deviceType
	
	fun getId() : String {
		return id
	}
	
	fun getDeviceType() : DeviceType {
		return deviceType
	}
	
	override fun equals(other: Any?) : Boolean {
		if (other?.javaClass != javaClass)
			return false
		if (this == other)
			return true
		
		other as AbstractDevice
		
		if (other.id != id)
			return false
		if (other.deviceType != deviceType)
			return false
		
		return true
	}
}