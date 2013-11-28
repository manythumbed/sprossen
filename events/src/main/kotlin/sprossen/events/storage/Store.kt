package sprossen.events.storage

import sprossen.events.Event
import org.joda.time.DateTime
import java.util.concurrent.atomic.AtomicBoolean

class Stored(val success: Boolean, val identifier: Identifier, val sequence: Long)

class Valid(val valid: Boolean, val sequence: Long)

trait Store {
	fun store(identifier: Identifier, sequence: Long, event: Event): Stored {
		val valid = valid(identifier, sequence)
		if(valid.valid)  {
			write(StoredEvent(identifier, timestamp(), sequence, event))

			return Stored(true, identifier, sequence + 1)
		}

		return Stored(false, identifier, valid.sequence)
	}

	fun valid(identifier: Identifier, sequence: Long): Valid

	fun timestamp(): DateTime

	fun write(event: StoredEvent)

	fun fetch(identifier: Identifier): List<Event>
}
