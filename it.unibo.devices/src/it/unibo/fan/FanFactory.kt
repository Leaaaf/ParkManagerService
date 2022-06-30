package it.unibo.fan

import it.unibo.fan.WsFan

object FanFactory {
	
	@JvmStatic
	fun create(id: String, address : String?) : AbstractFan? {
		if (null != address)
			return WsFan(id, address)
		else return null
	}
}