import org.junit.Test

internal class StreamExtKtTest {

    @Test
    fun `should run foldLeft with sum`() {
        // given
        val result = unfold(5) { a ->
         Some(a to a + 1)
        }.take(5).toListSafe()

            // then
            println(result)
        }
    }
