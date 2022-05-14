package it.unibo.parkingmanagerservice.entity.user

import java.sql.Timestamp
import java.nio.file.Paths
import java.nio.file.Files
import org.json.JSONObject
import java.util.Optional

data class User (
	var id : Long,
	var email : String,
	var state : UserState,
	var token : String? = null,
	var time : Timestamp? = null
) {
	companion object {
		private var ADMIN : User? = null
		private var FILE_PATH_CONFIG_ADMIN = "config/admin.json"
		
		init {
			val adminConfig = Paths.get(FILE_PATH_CONFIG_ADMIN)
			if (!Files.exists(adminConfig)) {
				println("ParkingManagerService | User | Unable to find admin file configuration ${adminConfig.toAbsolutePath()}")
			} else {
				try {
					val jsonObj = JSONObject(Files.newBufferedReader(adminConfig).readLine())
					ADMIN = User(Long.MAX_VALUE, jsonObj.getString("email"), UserState.CREATED)
				} catch (e : Exception) {
					println("ParkingManagerService | User | Error parsing admin configuration file: ${e.printStackTrace()}")
				} finally {}
			}
		}
		
		fun getAdmin() : User? {
			return ADMIN?.copy()
		}
	}
}