fun fib(i: Int): Int {
    tailrec fun go(counter: Int, current: Int, next: Int): Int  {
        return if(counter == 0) {
            current
        } else go(counter = counter - 1, current = next, next = current + next)
    }
    return go(i, 0, 1)
}
