package sprossen.events.storage

import sprossen.events.Event
import org.joda.time.DateTime
import java.util.HashMap
import java.util.concurrent.locks.LockSupport
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class SimpleStore(private val calendar: Calendar) : Store {
	private val storage: MutableMap<Identifier, List<Event>> = HashMap<Identifier, List<Event>>()
	private val lock = ReentrantLock()

	override fun store(identifier: Identifier, sequence: Long, event: Event): Stored {
		return lock.withLock<Stored> {
			super<Store>.store(identifier, sequence, event)
		}
	}

	override fun valid(identifier: Identifier, sequence: Long): Valid {
		val events = storage.get(identifier)
		if(events != null)  {
			val actualSequence = events.size().toLong()
			if(sequence == actualSequence) {
				return Valid(true, actualSequence)
			}

			return Valid(false, actualSequence)
		}

		return Valid(true, 0)
	}

	override fun fetch(identifier: Identifier): List<Event> {
		val events = storage.get(identifier)
		if(events != null)  {
			return events
		}

		return listOf()
	}
	override fun timestamp(): DateTime {
		return calendar.now()
	}
	override fun write(event: StoredEvent) {
		if(storage.containsKey(event.identifier))  {
			val list = storage.get(event.identifier)
			if(list != null)  {
				storage.put(event.identifier, list.plus(event.event))
			}
		}
		else  {
			storage.put(event.identifier, listOf(event.event))
		}
	}
}
