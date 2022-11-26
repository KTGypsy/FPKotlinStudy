import kotlin.collections.List
import kotlin.math.pow
import List as FPList

fun <A, B> Option<A>.map(f: (A) -> B): Option<B> = when (this) {
    is None -> this
    is Some -> Some(f(get))
}

fun <A, B> Option<A>.flatMap(f: (A) -> Option<B>): Option<B> = when (this) {
    is None -> this
    is Some -> f(get)
}

fun <A, B> Option<A>.getOrElse(default: () -> A): A = when (this) {
    is None -> default()
    is Some -> get
}

fun <A, B> Option<A>.orElse(ob: () -> Option<A>): Option<A> = when (this) {
    is None -> ob()
    is Some -> this
}

fun <A, B> Option<A>.filter(f: (A) -> Boolean): Option<A> = when (this) {
    is None -> this
    is Some -> if (f(get)) {
        this
    } else {
        None
    }
}

fun mean(xs: List<Double>): Option<Double> = if (xs.isEmpty()) {
    None
} else {
    Some(xs.sum() / xs.size)
}

fun variance(xs: List<Double>): Option<Double> = mean(xs).flatMap { m ->
    mean(xs.map { x ->
        (x - m).pow(2)
    })
}

fun <A, B, C> map2(a: Option<A>, b: Option<B>, f: (A, B) -> C): Option<C> {
    return a.flatMap { a -> b.map { b -> f(a, b) } }
}

fun <A> sequence(xs: FPList<Option<A>>): Option<FPList<A>> {
    return xs.foldRight(Some(Nil)) { o: Option<A>, ol: Option<FPList<A>> ->
        map2(o, ol) { o1: A, ol2: FPList<A> ->
            Cons(o1, ol2)
        }
    }
}

fun <A, B> traverse(xs: FPList<A>, f: (A) -> Option<B>): Option<FPList<B>> {
    return when(xs) {
        is Nil -> Some(Nil)
        is Cons -> map2(f(xs.head), traverse(xs.tail, f)) { b, xb ->
            Cons(b, xb)
        }
    }
}
