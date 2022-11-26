sealed class Option<out A>
data class Some<out A>(val get: A) : Option<A>()
object None : Option<Nothing>()