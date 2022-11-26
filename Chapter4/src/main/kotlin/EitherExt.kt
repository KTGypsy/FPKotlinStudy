import List as FPList

fun <E, A, B> Either<E, A>.map(f: (A) -> B): Either<E, B> {
    return when (this) {
        is Left -> this
        is Right -> Right(f(value))
    }
}

fun <E, A, B> Either<E, A>.flatMap(f: (A) -> Either<E, B>): Either<E, B> {
    return when (this) {
        is Left -> this
        is Right -> f(value)
    }
}

fun <E, A> Either<E, A>.orElse(f: () -> Either<E, A>): Either<E, A> {
    return when (this) {
        is Left -> f()
        is Right -> this
    }
}

fun <E, A, B, C> map2(ae: Either<E, A>, be: Either<E, B>, f: (A, B) -> C): Either<E, C> {
    return ae.flatMap { a -> be.map { b -> f(a, b) } }
}

fun <E, A, B> traverse(xs: FPList<A>, f: (A) -> Either<E, B>): Either<E, FPList<B>> {
    return when (xs) {
        is Nil -> Right(Nil)
        is Cons -> map2(f(xs.head), traverse(xs.tail, f)) { a: B, b: FPList<B> ->
            Cons(a, b)
        }
    }
}
// fun <E, A> sequence(xs: FPList<Either<E, A>>): Either<E, FPList<A>>  = traverse(xs) { it }
fun <E, A> sequence(xs: FPList<Either<E, A>>): Either<E, FPList<A>> {
    return xs.foldRight(Right(Nil)) { x: Either<E, A>, y: Either<E, FPList<A>> ->
        map2(x, y) { x2: A, y2: FPList<A> -> Cons(x2, y2) }
    }
}
