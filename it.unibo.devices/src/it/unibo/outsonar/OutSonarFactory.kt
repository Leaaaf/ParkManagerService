package it.unibo.outsonar

object OutSonarFactory {
	fun create(id : String, address : String?) : AbstractOutSonar? {
		return if (null != address) WsOutSonar(id, address) else null
	}
}