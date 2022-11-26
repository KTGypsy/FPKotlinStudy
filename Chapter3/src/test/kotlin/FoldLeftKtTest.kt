import org.junit.Test

internal class FoldLeftKtTest {
    @Test
    fun `should run foldLeft`() {
        val input = Cons(1, Cons(2, Cons(6, Nil)))
        val result = foldLeft(input, 11) { a, b ->
            a + b
        }

        println(result)
    }

    @Test
    fun `should run reverseLeft`() {
        val input = Cons(1, Cons(2, Cons(6, Nil)))
        val result = reverseLeft(input)

        println(result)
    }

    @Test
    fun `should run append`() {
        val input = Cons(1, Cons(2, Cons(6, Nil)))
        val input2 = Cons(9, Cons(10, Cons(11, Nil)))
        val result = append(input, input2)

        println(result)
    }

    @Test
    fun `should run concatenate`() {
        val input = Cons(1, Cons(2, Cons(6, Nil)))
        val input2 = Cons(9, Cons(10, Cons(11, Nil)))
        val input3 = Cons(13, Cons(14, Cons(15, Nil)))
        val input4 = Cons(16, Cons(17, Cons(18, Nil)))
        val inputFinal = Cons(input, Cons(input2, Cons(input3, Cons(input4, Nil))))
        val result = concatenate(inputFinal)

        println(result)
    }

    @Test
    fun `should run add1ToEach`() {
        val input = Cons(1, Cons(2, Cons(6, Nil)))
        val result = add1ToEach(input)

        println(result)
    }

    @Test
    fun `should run covnertDoublesToStrings`() {
        val input = Cons(1.0, Cons(2.0, Cons(6.0, Nil)))
        val result = convertDoublesToStrings(input)

        println(result)
    }

    @Test
    fun `should run filter odd numbers`() {
        val input = Cons(1, Cons(2, Cons(6, Cons(7, Nil))))
        val result = filter(input) {
            it % 2 == 0
        }

        println(result)
    }

    @Test
    fun `should run hasSubsequence`() {
        val input = Cons(1, Cons(2, Cons(3, Cons(4, Nil))))
        val input2 = Cons(1, Cons(2, Nil))
        val input3 = Cons(2, Cons(3, Nil))
        val input4 = Cons(4, Nil)
        val input5 = Cons(2, Cons(1, Nil))

        assert(hasSubsequence(input, input2))
        assert(hasSubsequence(input, input3))
        assert(hasSubsequence(input, input4))
        assert(hasSubsequence(input, input5).not())
    }
}