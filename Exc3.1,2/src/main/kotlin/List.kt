sealed class List<out A> {
    companion object {
        fun <A> tail(xs: List<A>): List<A> {
            return when (xs) {
                is Nil -> throw IllegalStateException()
                is Cons -> xs.tail
            }
        }

        fun <A> setHead(xs: List<A>, x: A): List<A> {
            return when (xs) {
                is Nil -> throw IllegalStateException()
                is Cons -> Cons(head = x, tail = xs.tail)
            }
        }
    }
}

object Nil : List<Nothing>()
data class Cons<out A>(val head: A, val tail: List<A>) : List<A>()

