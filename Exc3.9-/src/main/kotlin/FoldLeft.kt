tailrec fun <A, B>foldLeft(xs: List<A, B>, z: B, f: (B, A) -> B): B {
    return when(xs) {
        is Nil -> z
        is Cons ->
    }
}