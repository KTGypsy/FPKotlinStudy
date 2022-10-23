import org.junit.Test

internal class ListKtTest {

    @Test
    fun `exc37`() {
        val result = Cons(1, Cons(2, Cons(6, Nil))).length
        println(result)
    }
}
