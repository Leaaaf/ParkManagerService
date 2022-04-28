package it.unibo.weightsensor

import it.unibo.devices.DeviceType
import it.unibo.devices.AbstractDevice
import java.nio.file.Paths
import java.nio.file.Files
import org.json.JSONObject

abstract class AbstractWeightSensor(id : String) : AbstractDevice(id, DeviceType.WHEIGHT_SENSOR) {
	companion object {
		@JvmStatic private val CONFIG_FILE_PATH = "config/weightsensor.conf"
		@JvmStatic private var MIN_WEIGHT = 0.1
		
		init {
			val configFilePath = Paths.get(CONFIG_FILE_PATH)
			
			if (!Files.exists(configFilePath)) {
				println("WeightSensor | File ${configFilePath.toAbsolutePath().toString()} does not exists")
				println("WeightSensor | Default configuration will be used: MIN_WEIGHT = $MIN_WEIGHT")
				System.exit(-1)
			}
			println("WeightSensor | Config file ${configFilePath.toAbsolutePath().toString()} found")
			
			val reader = Files.newBufferedReader(configFilePath)
			
			val jsonObj = JSONObject(reader.readLine())
			if (jsonObj.has("min_weight")) {
				MIN_WEIGHT = jsonObj.getDouble("min_weith")
				println("WeightSensor | Found configuration: MIN_WEIGHT = $MIN_WEIGHT")
			} else println("WeightSensor | Configuration not found. Default value will be used: MIN_WEIGHT = $MIN_WEIGHT")
		}
		
		fun getMinWeight() : Double {
			return MIN_WEIGHT
		}
	}
	
	abstract fun readWeight() : Double
}