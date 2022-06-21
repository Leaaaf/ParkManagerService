package it.unibo.devices

import java.util.concurrent.locks.ReentrantLock
import java.nio.file.Paths
import java.nio.file.Files
import org.json.JSONObject
import it.unibo.basicfan.WsFan
import it.unibo.fan.AbstractFan
import it.unibo.fan.FanFactory
import it.unibo.weightsensor.AbstractWeightSensor
import it.unibo.weightsensor.WeightSensorFactory
import it.unibo.thermometer.AbstractThermometer
import it.unibo.thermometer.ThermometerFactory
import it.unibo.outsonar.AbstractOutSonar
import it.unibo.outsonar.OutSonarFactory

/**
 * Thread safe class to obatain a device
 */
object DeviceManager {
	@JvmStatic val CONFIG_FILE_PATH = "config/devices.conf"
	
	private val devicesMap = mutableMapOf<String, AbstractDevice?>()
	private val resourceLock = ReentrantLock()
	
	init {
		resourceLock.lock()
		try {
			println("DeviceManager | Starting...")
			val configFilePath = Paths.get(CONFIG_FILE_PATH)
			
			if (!Files.exists(configFilePath)) {
				println("DeviceManager | ERROR: config file ${configFilePath.toAbsolutePath().toString()} does not exists")
				System.exit(-1)
			}
			println("DeviceManager | Config file ${configFilePath.toAbsolutePath().toString()} found")

			val reader = Files.newBufferedReader(configFilePath)
			
			var line : String? = reader.readLine()
			var jsonObj : JSONObject
			
			var id : String
			var deviceType : DeviceType
			
			while (line != null) {
				if (!line.startsWith("#")) {
					jsonObj = JSONObject(line)
					
					if (jsonObj.has("id")) {
						id = jsonObj.getString("id")
						
						if (jsonObj.has("device")) {
							deviceType = DeviceType.valueOf(jsonObj.getString("device").toUpperCase())
							println("DeviceManager | Found device config ${id} [$deviceType]")
							
							when (deviceType) {
								DeviceType.FAN -> devicesMap.put(id, getFan(jsonObj))
								DeviceType.WHEIGHT_SENSOR -> devicesMap.put(id, getWeightSensor(jsonObj))
								DeviceType.THERMOMETER -> devicesMap.put(id, getThermometer(jsonObj))
								DeviceType.OUTSONAR -> devicesMap.put(id, getOutSonar(jsonObj))
							}							
						} else {
							println("DeviceManager | ERROR: config file not contains a type for id: ${id}")
							System.exit(-1)
						}
					} else {
						println("DeviceManager | ERROR: config file not contains an id")
						System.exit(-1)
					}
				}
				line = reader.readLine()
			}
			println("DeviceManager | All devices configs has been loaded")
		} finally {
			resourceLock.unlock()
		}
	}
	
	private fun getFan(jsonObj : JSONObject) : AbstractFan? {
		println("DeviceManager | getFan | ${jsonObj.toString()}")
		val res = FanFactory.create(jsonObj.getString("id"),
									if(jsonObj.has("address")) jsonObj.getString("address") else null)
		
		if (null != res)
			println("DeviceManager | Fan loaded")
		else
			println("DeviceManager | Unable to load Fan with config: ${jsonObj.toString()}")
		
		return res		
	}
	
	private fun getWeightSensor(jsonObj : JSONObject) : AbstractWeightSensor? {
		val res = WeightSensorFactory.create(jsonObj.getString("id"),
											if (jsonObj.has("address")) jsonObj.getString("address") else null)
		
		if (null != res)
			println("DeviceManager | WeightSensor loaded")
		else
			println("DeviceManager | Unable to load WeightSensor with config: ${jsonObj.toString()}")
		
		return res
	}
	
	private fun getThermometer(jsonObj : JSONObject) : AbstractThermometer? {
		val res = ThermometerFactory.create(jsonObj.getString("id"),
											if (jsonObj.has("address")) jsonObj.getString("address") else null)
		
		if (null != res)
			println("DeviceManager | Thermometer loaded")
		else
			println("DeviceManager | Unable to load Thermometer with config: ${jsonObj.toString()}")
		
		return res
	}
	
	private fun getOutSonar(jsonObj : JSONObject) : AbstractOutSonar? {
		val res = OutSonarFactory.create(jsonObj.getString("id"),
											if (jsonObj.has("address")) jsonObj.getString("address") else null)
		
		if (null != res)
			println("DeviceManager | OutSonar loaded")
		else
			println("DeviceManager | Unable to load OutSonar with config: ${jsonObj.toString()}")
		
		return res
	}
	
	fun getDevice(id : String) : AbstractDevice? {
		resourceLock.lock()
		try {
			println("DeviceManager | Get device $id")
			return devicesMap.get(id)
		} finally {
			resourceLock.unlock()
		}
	}
}