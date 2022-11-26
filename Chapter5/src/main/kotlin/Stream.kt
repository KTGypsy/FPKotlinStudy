sealed class Stream<out A> {
    data class Cons<out A>(
        val head: () -> A,
        val tail: () -> Stream<A>
    ) : Stream<A>()

    object Empty : Stream<Nothing>()
}
