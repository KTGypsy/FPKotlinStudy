val <T> List<T>.tail: List<T>
    get() = drop(1)

val <T> List<T>.head: T
    get() = first()

fun <A> isSorted(elements: List<A>, order: (A, A) -> Boolean): Boolean {
    tailrec fun loop(currentHead: A, currentTail: List<A>): Boolean {
        return when {
            currentTail.size <= 1 -> true
            order(currentHead, currentTail.head) -> loop(currentTail.head, currentTail.tail)
            else -> false
        }
    }
    return loop(elements.head, elements.tail)
}