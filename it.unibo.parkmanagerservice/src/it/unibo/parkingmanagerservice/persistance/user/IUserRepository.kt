package it.unibo.parkingmanagerservice.persistance.user

import it.unibo.parkingmanagerservice.entity.user.User
import it.unibo.parkingmanagerservice.entity.user.UserState

interface IUserRepository {
	// CUD
	fun create(user : User)
	fun update(user : User)
	fun delete(id : Long)
	
	fun getById(id : Long) : User?
	fun getByEmail(email : String) : User?
	fun getByToken(token : String) : User?
	fun getByState(userState : UserState) : Collection<User>
	fun getByFirstState(userState : UserState) : User?
}