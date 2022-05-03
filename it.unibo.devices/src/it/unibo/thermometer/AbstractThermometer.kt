package it.unibo.thermometer

import it.unibo.devices.AbstractDevice
import it.unibo.devices.DeviceType
import java.nio.file.Paths
import java.nio.file.Files
import org.json.JSONObject

abstract class AbstractThermometer(id : String) : AbstractDevice(id, DeviceType.THERMOMETER) {
	companion object {
		@JvmStatic private val CONFIG_FILE_PATH = "config/thermometer.conf"
		@JvmStatic private var CRITICAL_TEMP = 25
		@JvmStatic private var POLLING_MS : Long = 1000
		
		init {
			val configFilePath = Paths.get(CONFIG_FILE_PATH)
			
			if (!Files.exists(configFilePath)) {
				println("Thermometer | File ${configFilePath.toAbsolutePath().toString()} does not exists")
				println("Thermometer | Default configuration will be used: CRITICAL_TEMP = $CRITICAL_TEMP :: POLLING_MS = $POLLING_MS")
				System.exit(-1)
			}
			println("Thermometer | Config file ${configFilePath.toAbsolutePath().toString()} found")
			
			val reader = Files.newBufferedReader(configFilePath)
			
			val jsonObj = JSONObject(reader.readLine())
			if (jsonObj.has("critical_temp")) {
				CRITICAL_TEMP = jsonObj.getInt("critical_temp")
				println("Thermometer | Found configuration: CRITICAL_TEMP = $CRITICAL_TEMP")
			} else println("Thermometer | Configuration not found. Default value will be used: CRITICAL_TEMP = $CRITICAL_TEMP")
			
			if (jsonObj.has("polling_ms")) {
				POLLING_MS = jsonObj.getLong("polling_ms")
				println("Thermometer | Found configuration: POLLING_MS = $POLLING_MS")
			} else println("Thermometer | Configuration not found. Default value will be used: POLLING_MS = $POLLING_MS")
		}
		
		fun getCriticalTemp() : Int {
			return CRITICAL_TEMP
		}
		
		fun getPollingMs() : Long {
			return POLLING_MS
		}
	}
	
	abstract fun readTemperature() : Double
}