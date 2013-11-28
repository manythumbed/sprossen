package sprossen.events

import junit.framework.TestCase
import kotlin.test.assertEquals

class StoreTest : TestCase()    {
	fun testSomething() {

		assertEquals(1, 1, "1 == 1 not 2")
	}
}

class Thing(events: List<TestEvent>) : Entity<TestEvent>(events) {
	override fun handle(event: TestEvent) {
		throw UnsupportedOperationException()
	}
}

class TestEvent() : Event()