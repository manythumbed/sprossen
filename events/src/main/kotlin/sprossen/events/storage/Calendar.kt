package sprossen.events.storage

import org.joda.time.DateTime

trait Calendar {
	fun now() : DateTime
}
