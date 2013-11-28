package sprossen.events.storage

import sprossen.events.Event
import org.joda.time.DateTime

class StoredEvent(val identifier: Identifier, val timestamp: DateTime, val sequence: Long, val event: Event)

