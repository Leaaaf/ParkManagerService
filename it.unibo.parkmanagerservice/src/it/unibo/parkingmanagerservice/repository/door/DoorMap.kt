package it.unibo.parkingmanagerservice.repository.door

import it.unibo.parkingmanagerservice.entity.door.DoorType
import java.nio.file.Paths
import java.nio.file.Files
import org.json.JSONObject
import org.json.JSONException

object DoorMap : IDoorMap {
	private val CONFIG_FILE_PATH = "config/doors_map.json"
	private val doorPosition = mutableMapOf<DoorType, Pair<Int, Int>>()
	private val allowedDoorPosition = mutableMapOf<DoorType, Pair<Int, Int>>()
	private val doorDirection = mutableMapOf<DoorType, String>()
	
	init {
		val configFile = Paths.get(CONFIG_FILE_PATH)
		if (!Files.exists(configFile)) {
			println("DoorMap | Unable to find the configuration file at ${configFile.toAbsolutePath()}")
            System.exit(-1)
		}
		
		var jsonObj : JSONObject
		var door : DoorType
		Files.lines(configFile).forEach {
			try {
				jsonObj = JSONObject(it)
				door = DoorType.valueOf(jsonObj.getString("door"))
				
				doorPosition[door] = Pair(jsonObj.getInt("x"), jsonObj.getInt("y"))
				allowedDoorPosition[door] = Pair(jsonObj.getInt("allowedX"), jsonObj.getInt("allowedY"))
				doorDirection[door] = jsonObj.getString("direction")
			} catch (e : JSONException) {
				println("DoorMap | Error: ${e.printStackTrace()}")
			}
		}
		
		println("DoorMap | Config completed")
	}
	
	override fun getDoorPosition(door : DoorType) : Pair<Int, Int>? {
		return doorPosition[door]
	}
	
	override fun getAdiacentAllowedPositionByDoorType(door : DoorType) : Pair<Int, Int>?{
		return allowedDoorPosition[door]
	}
	
	override fun getDirectionByDoorType(door : DoorType) : String? {
		return doorDirection[door]
	}
	
	override fun getDirection(x : Int, y : Int) : String? {
		return doorDirection[isNearDoor(x, y)]
	}
	
	override fun isNearDoor(x : Int, y : Int) : DoorType? {
		return allowedDoorPosition.entries.find { it.value == Pair(x, y) }?.key
	}
}