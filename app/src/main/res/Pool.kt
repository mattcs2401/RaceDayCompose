data class Pool(
    val Abandoned: Boolean,
    val Available: Boolean,
    val Dividends: List<Dividend>,
    val JackpotIn: Double,
    val JackpotOut: Double,
    val Legs: List<Leg>,
    val MinPoolSize: Double,
    val MinPoolTopUp: Double,
    val PoolTotal: Double,
    val PoolType: String,
    val Status: String
)