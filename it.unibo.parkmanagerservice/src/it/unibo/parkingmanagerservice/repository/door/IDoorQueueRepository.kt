package it.unibo.parkingmanagerservice.repository.door

import it.unibo.parkingmanagerservice.entity.user.User

interface IDoorQueueRepository {
	fun addUser(user : User)
	fun pullUser() : User?
	fun getSize() : Int
}