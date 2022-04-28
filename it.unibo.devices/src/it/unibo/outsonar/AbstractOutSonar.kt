package it.unibo.outsonar

import it.unibo.devices.DeviceType
import it.unibo.devices.AbstractDevice
import java.nio.file.Files
import java.nio.file.Paths
import org.json.JSONObject

abstract class AbstractOutSonar(id : String) : AbstractDevice(id, DeviceType.OUTSONAR) {
	companion object {
		@JvmStatic private val CONFIG_FILE_PATH = "config/outsonar.conf"
		@JvmStatic private var THRESHOLD_DISTANCE = 100
		
		init {
			val configFilePath = Paths.get(CONFIG_FILE_PATH)
			
			if (!Files.exists(configFilePath)) {
				println("OutSonar | File ${configFilePath.toAbsolutePath().toString()} does not exists")
				println("OutSonar | Default configuration will be used: THRESHOLD_DISTANCE = $THRESHOLD_DISTANCE")
				System.exit(-1)
			}
			println("OutSonar | Config file ${configFilePath.toAbsolutePath().toString()} found")
			
			val reader = Files.newBufferedReader(configFilePath)
			
			val jsonObj = JSONObject(reader.readLine())
			if (jsonObj.has("threshold_dist")) {
				THRESHOLD_DISTANCE = jsonObj.getInt("threshold_dist")
				println("OutSonar | Found configuration: THRESHOLD_DISTANCE = $THRESHOLD_DISTANCE")
			} else println("OutSonar | Configuration not found. Default value will be used: THRESHOLD_DISTANCE = $THRESHOLD_DISTANCE")
		}
		
		fun getThresholdDistance() : Int {
			return THRESHOLD_DISTANCE
		}
	}
	
	abstract fun readDistance() : Int
}