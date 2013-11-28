package sprossen.events.storage

data class Identifier(val value: String) {
	{
		require(value.isNotEmpty() && value.trim().isNotEmpty(), "Identifier cannot be null or empty")
	}
}


