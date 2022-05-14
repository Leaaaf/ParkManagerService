package it.unibo.parkingmanagerservice.persistance.user

import it.unibo.parkingmanagerservice.entity.user.User
import it.unibo.parkingmanagerservice.entity.user.UserState
import java.util.concurrent.atomic.AtomicLong
import java.sql.SQLException

class UserRepository : IUserRepository {
	private val idSequence = AtomicLong(0)
	private val userMap = mutableMapOf<Long, User>()
	
	@Throws(SQLException::class)
	override fun create(user : User) {
		if (userMap.values.filter({ it.email.equals(user.email) }).firstOrNull() != null) {
			throw SQLException("Email already exists.")
		}
		
		user.id = idSequence.getAndIncrement()		
		userMap.put(user.id, user)
	}
	
	override fun update(user : User) {
		userMap.set(user.id, user)
	}
	
	override fun delete(id : Long) {
		userMap.remove(id)
	}
	
	override fun getById(id : Long) : User? {
		return userMap.get(id)
	}
	
	override fun getByEmail(email : String) : User? {
		return userMap.values.find { it.email.equals(email) }
	}
	
	override fun getByToken(token : String) : User? {
		return userMap.values.find { it.token.equals(token) }
	}
	
	override fun getByState(userState : UserState) : Collection<User> {
		return userMap.values.filter { it.state.equals(userState) }
	}
	
	override fun getByFirstState(userState : UserState) : User? {
		return userMap.values.filter { it.state.equals(userState) }.sortedBy { it.time }.firstOrNull()
	}
}