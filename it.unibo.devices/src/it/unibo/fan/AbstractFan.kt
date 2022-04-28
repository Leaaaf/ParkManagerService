package it.unibo.fan

import it.unibo.devices.AbstractDevice
import it.unibo.devices.DeviceType

abstract class AbstractFan(id : String) : AbstractDevice(id, DeviceType.FAN) {
	abstract fun setState(state: FanState)
	abstract fun powerOn()
	abstract fun powerOff()
}