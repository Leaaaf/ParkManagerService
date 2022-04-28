package it.unibo.utility

abstract class AbstractUpdater<T>(value : T) : Updater<T> {
	
	protected val value = LockableVal(value)
	
	override fun get() : T {
		return value.safeGet()
	}
}