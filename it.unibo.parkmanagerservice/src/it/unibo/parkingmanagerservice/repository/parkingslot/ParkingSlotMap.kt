package it.unibo.parkingmanagerservice.repository.parkingslot

import java.nio.file.Paths
import java.nio.file.Files
import org.json.JSONObject
import org.json.JSONException

object ParkingSlotMap : IParkingSlotMap {
	private val CONFIG_FILE_PATH = "config/parking_slot_map.json"
	private val parkingSlotPosition = mutableMapOf<Int, Pair<Int, Int>>()
	private val allowedParkingSlotPosition = mutableMapOf<Int, Pair<Int, Int>>()
	private val parkingSlotDirection = mutableMapOf<Int, String>()
	
	init {
		val configFile = Paths.get(CONFIG_FILE_PATH)
        if(!Files.exists(configFile)) {
            println("ParkingSlotMap | Unable to find the configuration file at ${configFile.toAbsolutePath()}")
            System.exit(-1)
        }

        var jsonObj : JSONObject
        var slotnum = 0
        Files.lines(configFile).forEach {
            try {
                jsonObj = JSONObject(it)
                slotnum = jsonObj.getInt("slotnum")
				
                parkingSlotPosition[slotnum] = Pair(jsonObj.getInt("x"), jsonObj.getInt("y"))
                allowedParkingSlotPosition[slotnum] = Pair(jsonObj.getInt("allowedX"), jsonObj.getInt("allowedY"))
                parkingSlotDirection[slotnum] = jsonObj.getString("direction")
            } catch (e : JSONException) {
                println("ParkingSlotMap | ${e.printStackTrace()}")
                System.exit(-1)
            }
        }

        println("ParkingSlotMap | Configuration completed")
	}
	
	override fun getAllSlotnum() : IntArray {
		return parkingSlotPosition.keys.stream().mapToInt{ it -> it}.toArray()
	}
	
	override fun getSlotnumPosition(slotnum : String) : Pair<Int, Int>? {
		return parkingSlotPosition[slotnum.toInt()]
	}
	
	override fun getAdiacentAllowedPositionBySlotnum(slotnum : String) : Pair<Int, Int>? {
		return allowedParkingSlotPosition[slotnum.toInt()]
	}
	
	override fun getSlotnumDirection(slotnum : String) : String? {
		return parkingSlotDirection[slotnum.toInt()]
	}
	
	override fun getDirection(x : Int, y : Int) : String? {
		return parkingSlotDirection[isNearSlot(x, y)]
	}
	
	override fun isNearSlot(x : Int, y : Int) : Int? {
		return allowedParkingSlotPosition.entries.find { it.value == Pair(x, y) }?.key
	}
}