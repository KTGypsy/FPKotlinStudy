sealed class List<out A>
object Nil : List<Nothing>()
data class Cons<out A>(val head: A, val tail: List<A>) : List<A>()

fun <A> List<A>.tail(): List<A> {
    return when (this) {
        is Nil -> throw IllegalStateException()
        is Cons -> tail
    }
}

fun <A> List<A>.setHead(x: A): List<A> {
    return when (this) {
        is Nil -> throw IllegalStateException()
        is Cons -> Cons(head = x, tail = tail)
    }
}

tailrec fun <A> List<A>.drop(n: Int): List<A> {
    return if (n == 0) {
        this
    } else {
        tail().drop(n - 1)
    }
}

fun <A> List<A>.dropWhile(predicate: (A) -> Boolean): List<A> {
    fun Cons<A>.loop() = if (predicate(head)) {
        tail.dropWhile(predicate)
    } else {
        this
    }

    return when (this) {
        is Cons -> loop()
        Nil -> throw IllegalStateException()
    }
}

fun <A> List<A>.init(): List<A> {
    return when (this) {
        is Nil -> this
        is Cons<A> -> if (tail is Nil) {
            Nil
        } else {
            Cons(head, tail.init())
        }
    }
}

fun <A, B> List<A>.foldRight(z: B, f: (A, B) -> B): B =
    when (this) {
        is Nil -> z
        is Cons -> f(head, tail.foldRight(z, f))
    }

fun <A> empty(): List<A> = Nil

val <A> List<A>.length: Int
    get() = foldRight(0) { _, y -> y + 1 }

