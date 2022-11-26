import kotlin.math.absoluteValue

internal interface RNG {
    fun nextInt(): Pair<Int, RNG>
}

internal fun nonNegativeInt(rng: RNG): Pair<Int, RNG> {
    val random = rng.nextInt()
    return random.first.absoluteValue to random.second
}

internal fun double(rng: RNG): Pair<Double, RNG> {
    val random = nonNegativeInt(rng)
    return (random.first / (Int.MAX_VALUE.toDouble() + 1)) to random.second
}

internal fun intDouble(rng: RNG): Pair<Pair<Int, Double>, RNG> {
    val intRand = rng.nextInt()
    val doubleRand = double(intRand.second)
    return Pair(intRand.first, doubleRand.first) to doubleRand.second
}

internal fun doubleInt(rng: RNG): Pair<Pair<Double, Int>, RNG> {
    val doubleRand = double(rng)
    val intRand = doubleRand.second.nextInt()
    return Pair(doubleRand.first, intRand.first) to intRand.second

}

internal fun double3(rng: RNG): Pair<Triple<Double, Double, Double>, RNG> {
    val doubleRand1 = double(rng)
    val doubleRand2 = double(doubleRand1.second)
    val doubleRand3 = double(doubleRand2.second)

    return Triple(doubleRand1.first, doubleRand2.first, doubleRand3.first) to doubleRand3.second
}

internal fun ints(count: Int, rng: RNG): Pair<List<Int>, RNG> {
    return if (count <= 0) {
        Nil to rng
    } else {
        val random = rng.nextInt()
        val random2 = ints(count - 1, random.second)
        Cons(random.first, random2.first) to random2.second
    }
}

internal typealias Rand<A> = (RNG) -> Pair<A, RNG>

internal val intR: Rand<Int> = { rng -> rng.nextInt() }