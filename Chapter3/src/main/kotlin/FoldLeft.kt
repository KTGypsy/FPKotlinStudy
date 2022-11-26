tailrec fun <A, B> foldLeft(xs: List<A>, z: B, f: (B, A) -> B): B {
    return when (xs) {
        is Nil -> z
        is Cons -> foldLeft(xs.tail, f(z, xs.head), f)
    }
}

fun sumLeft(xs: List<Int>) = foldLeft(xs, 0) { a, b -> a + b }

fun productLeft(xs: List<Int>) = foldLeft(xs, 1) { a, b -> a * b }

fun lengthLeft(xs: List<Any>) = foldLeft(xs, 0) { a, _ -> a + 1 }

fun <A> reverseLeft(xs: List<A>) = foldLeft(xs, Nil as List<A>) { a, b -> Cons(b, a) }

fun <A> append(xs: List<A>, items: List<A>) = xs.foldRight(items) { a, b ->
    Cons(a, b)
}

fun concatenate(lists: List<List<Any>>) = lists.foldRight(empty<Any>()) { a, b ->
    append(a, b)
}

fun add1ToEach(list: List<Int>) = list.foldRight(empty<Int>()) { a, b ->
    Cons(a + 1, b)
}

fun convertDoublesToStrings(list: List<Double>) = list.foldRight(empty<String>()) { a, b ->
    Cons(a.toString(), b)
}

fun <A, B> map(xs: List<A>, f: (A) -> (B)): List<B> = xs.foldRight(empty()) { a, b ->
    Cons(f(a), b)
}

fun <A> filter(xs: List<A>, f: (A) -> Boolean): List<A> = xs.foldRight(empty()) { a, b ->
    if (f(a)) {
        Cons(a, b)
    } else b
}

fun <A, B> flatMap(xs: List<A>, f: (A) -> List<B>): List<B> = xs.foldRight(empty()) { a, b ->
    append(f(a), b)
}

fun <A> flatMapFilter(xs: List<A>, f: (A) -> Boolean): List<A> = flatMap(xs) {
    if (f(it)) Cons(it, Nil) else empty<A>()
}

fun sumElements(xa: List<Int>, xs: List<Int>): List<Int> =
    when (xa) {
        is Nil -> Nil
        is Cons -> when (xs) {
            is Nil -> Nil
            is Cons -> Cons(xa.head + xs.head, sumElements(xa.tail, xs.tail))
        }
    }

fun <A, B> zipWith(xa: List<A>, xs: List<A>, f: (A, A) -> B): List<B> {
    return when (xa) {
        is Nil -> Nil
        is Cons -> when (xs) {
            is Nil -> Nil
            is Cons -> Cons(f(xa.head, xs.head), zipWith(xa.tail, xs.tail, f))
        }
    }
}

tailrec fun <A> hasSubsequence(xs: List<A>, sub: List<A>): Boolean = when (xs) {
    is Nil -> sub is Nil
    is Cons -> when (sub) {
        is Nil -> true
        is Cons -> if (xs.head == sub.head) {
            hasSubsequence(xs.tail, sub.tail)
        } else {
            hasSubsequence(xs.tail, sub)
        }
    }
}