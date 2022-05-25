package it.unibo.parkingmanagerservice.entity.timer

import java.util.concurrent.locks.ReentrantLock
import java.nio.file.Paths
import java.nio.file.Files
import org.json.JSONObject

data class Timer(val ITOCC : Long, val DTFREE : Long,
				 val INDOOR_POLLING : Long, val OUTDOOR_POLLING : Long) {
	companion object {
		@JvmStatic private var timer : Timer? = null
		@JvmStatic private val CONFIG_FILE_PATH = "config/timer.json"
		@JvmStatic private val resourceLock = ReentrantLock()
		
		@JvmStatic fun get() : Timer {
			resourceLock.lock()
			try {
				val configFile = Paths.get(CONFIG_FILE_PATH)
				
				if (!Files.exists(configFile)) {
					println("Timer | Unable to get configuration file at ${configFile.toAbsolutePath()}")
					println("Timer | Default configuration (120 seconds for ITOCC/DTFREE, 1 second for POLLING) will be used")
					timer = Timer(120000L, 120000L, 1000L, 1000L)
				} else {
					val jsonObj : JSONObject = JSONObject(Files.newBufferedReader(configFile).readLine())
					timer = Timer(
						jsonObj.getLong("itocc_sec") * 1000,
						jsonObj.getLong("dtfree_sec") * 1000,
						jsonObj.getLong("indoor_polling_sec") * 1000,
						jsonObj.getLong("outdoor_polling_sec") * 1000,
					)
				}
			} catch (e : Exception) {
				println("Timer | Error while reading configuration file")
				e.printStackTrace()
				println("Timer | Default configuration (120 seconds for ITOCC/DTFREE, 1 second for POLLING) will be used")
				timer = Timer(120000L, 120000L, 1000L, 1000L)
			} finally {
				resourceLock.unlock()
			}
			
			return timer!!
		}
	}
}