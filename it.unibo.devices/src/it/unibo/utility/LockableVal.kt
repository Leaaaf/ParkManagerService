package it.unibo.utility

import java.util.concurrent.locks.ReentrantLock

class LockableVal<T>(v : T) {
	private val lock = ReentrantLock()
	private var value = v
	
	fun get() : T {
		return value;
	}
	
	fun safeGet() : T {
		lock.lock()
		try {
			return value
		} finally {
			lock.unlock()
		}
	}
	
	fun safeSet(value : T) {
		lock.lock()
		try {
			this.value = value
		} finally {
			lock.unlock()
		}
	}
}