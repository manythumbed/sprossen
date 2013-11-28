package sprossen.events.storage

import junit.framework.TestCase
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import sprossen.events.Event
import kotlin.test.assertFalse

class SimpleStoreTest : TestCase()  {
	private val calendar = object : Calendar  {
		override fun now(): DateTime {
			return DateTime(DateTimeZone.UTC)
		}
	}

	fun testFetch() {
		val store = SimpleStore(calendar)
		val events = store.fetch(Identifier("1"))

		assertTrue(events.empty, "An empty list should be returned for unknown identifier")
	}

	fun testStore() {
		val store = SimpleStore(calendar)
		val stored = store.store(Identifier("1"), 0, TestEvent())

		val error = store.store(Identifier("1"), 0, TestEvent())

		assertTrue(stored.success, "Should have stored succcessfully")
		assertFalse(error.success, "Should not have stored")
		assertEquals(stored.sequence, 1.toLong(), "Should increment sequence number on successful store")

		val events = store.fetch(Identifier("1"))

		assertTrue(events.size == 1, "An list of one element should be returned for a known identifier")
	}

	private class TestEvent() : Event()
}


