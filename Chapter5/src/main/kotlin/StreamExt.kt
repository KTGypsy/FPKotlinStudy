import Cons
import Cons as ListCons
import List as FPList

fun <A> Stream<A>.toList(): FPList<A> {
    return when (this) {
        is Stream.Empty -> Nil
        is Stream.Cons -> ListCons(head(), tail().toList())
    }
}

fun <A, B> foldLeft(xs: Stream<A>, z: B, f: (B, A) -> B): B {
    tailrec fun go(xsa: Stream<A>, acc: B): B = when (xsa) {
        is Stream.Empty -> acc
        is Stream.Cons -> go(xsa.tail(), f(acc, xsa.head()))
    }
    return go(xs, z)
}

fun <A, B> Stream<A>.foldRight(z: B, f: (A, B) -> B): B {
    tailrec fun go(xsa: Stream<A>, acc: B): B = when (xsa) {
        is Stream.Empty -> acc
        is Stream.Cons -> go(xsa.tail(), f(xsa.head(), acc))
    }
    return go(this, z)
}

fun <A> Stream<A>.toListSafe(): FPList<A> {
    tailrec fun go(xs: Stream<A>, acc: FPList<A>): FPList<A> = when (xs) {
        is Stream.Empty -> acc
        is Stream.Cons -> go(xs.tail(), Cons(xs.head(), acc))
    }
    return reverseLeft(go(this, empty()))
}

fun <A> Stream<A>.take(n: Int): Stream<A> {
    fun go(goN: Int, xs: Stream<A>): Stream<A> = when {
        goN <= 0 -> Stream.Empty
        xs is Stream.Cons -> Stream.Cons(xs.head) { go(goN - 1, xs.tail()) }
        else -> xs
    }
    return go(n, this)
}

fun <A> Stream<A>.drop(n: Int): Stream<A> {
    tailrec fun go(xsGo: Stream<A>, goN: Int): Stream<A> = when {
        goN <= 0 -> xsGo
        xsGo is Stream.Cons -> go(xsGo.tail(), goN - 1)
        else -> xsGo
    }
    return go(this, n)
}

fun <A> Stream<A>.takeWhile(p: (A) -> Boolean): Stream<A> {
    return when (this) {
        is Stream.Cons -> if (p(head())) {
            Stream.Cons(head) { tail().takeWhile(p) }
        } else {
            Stream.Empty
        }

        is Stream.Empty -> Stream.Empty
    }
}

fun <A, B> Stream<A>.foldRight2(
    z: () -> B,
    f: (A, () -> B) -> B
): B = when (this) {
    is Stream.Cons -> f(this.head()) {
        tail().foldRight2(z, f)
    }

    is Stream.Empty -> z()
}

fun <A> Stream<A>.exists2(p: (A) -> Boolean): Boolean = foldRight2({ false }) { a, b ->
    p(a) || b()
}

fun <A> Stream<A>.forAll(p: (A) -> Boolean): Boolean {
    return this.foldRight2({ true }) { a: A, function: () -> Boolean ->
        p(a) && function()
    }
}

fun <A> Stream<A>.takeWhile2(p: (A) -> Boolean): Stream<A> {
    return this.foldRight2({ Stream.Empty as Stream<A> }) { a, b ->
        if (p(a)) {
            Stream.Cons({ a }, b)
        } else b()
    }
}

fun <A> Stream<A>.headOption(): Option<A> {
    return this.foldRight2({ None as Option<A> }) { a, _ ->
        Some(a)
    }
}

fun <A> emptyStream(): Stream<A> = Stream.Empty

fun <A, B> Stream<A>.map(f: (A) -> (B)) = foldRight2(
    { emptyStream() },
    { h: A, t: () -> Stream<B> -> Stream.Cons({ f(h) }, t) }
)

fun <A> Stream<A>.filter(p: (A) -> Boolean) = foldRight2(
    { emptyStream() },
    { h: A, t: () -> Stream<A> ->
        if (p(h)) {
            Stream.Cons({ h }, t)
        } else {
            t()
        }
    }
)

fun <A> Stream<A>.append(sa: () -> Stream<A>) = foldRight2(sa) { h: A, t: () -> Stream<A> ->
    Stream.Cons({ h }, t)
}

fun <A, B> Stream<A>.flatMap(f: (A) -> Stream<B>) = foldRight2(
    { emptyStream<B>() },
    { h: A, t: () -> Stream<B> -> f(h).append(t) }
)

fun <A> constant(a: A): Stream<A> = Stream.Cons({ a }, { constant(a) })
fun from(n: Int): Stream<Int> = Stream.Cons({ n }, { from(n.inc()) })
fun fibs(): Stream<Int> {
    fun go(n1: Int, n2: Int): Stream<Int> = Stream.Cons({ n2 }, { go(n2, n2 + n1) })

    return go(0, 1)
}

fun <A, S> unfold(z: S, f: (S) -> Option<Pair<A, S>>): Stream<A> {
    fun go(n: S): Stream<A> {
        return when (val a = f(n)) {
            is Some -> Stream.Cons({ a.get.first }, { go(a.get.second) })
            is None -> Stream.Empty
        }
    }
    return go(z)
}

fun unfoldFibs() = unfold(Pair(0, 1)) { (a, b) ->
    Some(a to Pair(b, a + b))
}

fun unfoldFrom(n: Int) = unfold(n) { Some(it to it + 1) }

fun unfoldConstant(n: Int) = unfold(n) { Some(it to it) }
fun unfoldOnes() = unfold(1) { Some(1 to 1) }

fun <A, B> Stream<A>.mapUnfold(f: (A) -> B): Stream<B> = unfold(this) { element: Stream<A> ->
    when (element) {
        is Stream.Cons -> Some(f(element.head()) to element.tail())
        is Stream.Empty -> None
    }
}

fun <A> Stream<A>.takeUnfold(n: Int): Stream<A> = unfold(this) { element ->
    when (element) {
        is Stream.Empty -> None
        is Stream.Cons -> if (n == 0) {
            None
        } else {
            Some(element.head() to element.tail().takeUnfold(n - 1))
        }
    }
}

fun <A, B, C> Stream<A>.zipWith(
    that: Stream<B>,
    f: (A, B) -> C
): Stream<C> = unfold(this to that) { (a, b) ->
    when (a) {
        is Stream.Empty -> None
        is Stream.Cons -> when (b) {
            is Stream.Empty -> None
            is Stream.Cons -> Some(f(a.head(), b.head()) to (a.tail() to b.tail()))
        }
    }
}

fun <A, B> Stream<A>.zipAll(that: Stream<B>): Stream<Pair<Option<A>, Option<B>>> = unfold(this to that) { (a, b) ->
    when (a) {
        is Stream.Cons -> when (b) {
            is Stream.Empty -> Some(Pair(Some(a.head()) to None, a.tail() to emptyStream<B>()))
            is Stream.Cons -> Some(Pair(Some(a.head()) to Some(b.head()), Pair(a.tail(), b.tail())))
        }

        is Stream.Empty -> when (b) {
            is Stream.Empty -> None
            is Stream.Cons -> Some(Pair(None to Some(b.head()), emptyStream<A>() to b.tail()))
        }
    }
}

// TODO 5.14
fun <A> Stream<A>.startsWith(that: Stream<A>): Boolean = true

// TODO 5.15
fun <A> Stream<A>.tails(): Stream<Stream<A>> = emptyStream()

fun <A> Stream<A>.hasSubsequence(s: Stream<A>) : Boolean = tails().exists2 { it.startsWith(s) }