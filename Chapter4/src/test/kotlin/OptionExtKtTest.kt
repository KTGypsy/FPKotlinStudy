import org.junit.Test

internal class OptionExtKtTest {
    @Test
    fun `should return none when any of list element is none`() {
        // given
        val input = Cons(Some(2), Cons(Some(3), Cons(None, Nil)))

        // when
        val result = sequence(input)

        // then
        assert(result == None)
    }
}