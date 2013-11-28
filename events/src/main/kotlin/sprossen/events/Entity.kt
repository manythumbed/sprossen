package sprossen.events

import java.util.HashMap

abstract class Entity<T : Event>(private val events: List<T>) {

	{
		for(event: T in events) {
			handle(event)
		}
	}

	abstract protected fun handle(event: T): Unit
}
