sealed class Tree<out A>
data class Leaf<A>(val value: A) : Tree<A>()
data class Branch<A>(val left: Tree<A>, val right: Tree<A>) : Tree<A>()

fun <A> Tree<A>.size(): Int = when (this) {
    is Leaf -> 1
    is Branch -> 1 + left.size() + right.size()
}

fun Tree<Int>.maximum(): Int = when (this) {
    is Leaf -> value
    is Branch -> maxOf(left.maximum(), right.maximum())
}

fun <A> Tree<A>.depth(): Int = when (this) {
    is Leaf -> 0
    is Branch -> 1 + maxOf(left.depth(), right.depth())
}

fun <A, B> Tree<A>.map(f: (A) -> B): Tree<B> = when (this) {
    is Leaf -> Leaf(f(value))
    is Branch -> Branch(left.map(f), right.map(f))
}

fun <A, B> Tree<A>.fold(l: (A) -> B, b: (B, B) -> B): B = when (this) {
    is Leaf -> l(value)
    is Branch -> b(left.fold(l, b), right.fold(l, b))
}

fun <A> Tree<A>.sizeF(): Int = fold({ 1 }, { a, b -> 1 + a + b })
fun Tree<Int>.maximumF(): Int = fold({ it }, { a, b -> maxOf(a, b) })
fun <A> Tree<A>.depthF(): Int = fold({ 0 }, { a, b -> 1 + maxOf(a, b) })
fun <A, B> Tree<A>.mapF(f: (A) -> B): Tree<B> = fold(
    { v: A -> Leaf(f(v)) },
    { l: Tree<B>, r: Tree<B> -> Branch(l, r) }
)
