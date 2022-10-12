import org.junit.Test

internal class FibonacciKtTest {
    @Test
    fun `should get 6th fibonacci number`() {
        // when
        val fifthNumber = fib(6)

        //
        assert(fifthNumber == 8)
    }

    @Test
    fun `should get 14th fibonacci number`() {
        // when
        val fifthNumber = fib(14)

        //
        assert(fifthNumber == 377)
    }
}