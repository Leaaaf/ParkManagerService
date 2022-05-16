package it.unibo.parkingmanagerservice.repository.door

import it.unibo.parkingmanagerservice.entity.user.User

class DoorQueueRepository : IDoorQueueRepository {
	private val queue = ArrayDeque<User>()
			
	override fun addUser(user : User) {
		if (!queue.contains(user))
			queue.add(user)
	}
	
	override fun pullUser() : User? {
		return queue.removeFirstOrNull()
	}
	
	override fun getSize() : Int {
		return queue.size
	}
}