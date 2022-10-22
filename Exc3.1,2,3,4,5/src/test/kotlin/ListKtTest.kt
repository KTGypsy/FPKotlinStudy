import org.junit.Test

internal class ListKtTest {

    @Test
    fun `should init without last item`() {
        // given
        val initialList = Cons(head = 1, tail = Cons(head = 2, tail = Cons(head = 3, tail = Nil)))

        // when
        val copiedListWithoutLastElement = initialList.init()

        // then
        assert(false)
    }
}