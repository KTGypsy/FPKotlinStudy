import org.junit.Test

internal class IsSortedKtTest {
    @Test
    fun `should return true when int list is sorted`() {
        // given
        val sortedIntegers = listOf(1, 2, 3, 4, 5)
        val sortCheck = { a: Int, b: Int -> a <= b }

        // when
        val sorted = isSorted(sortedIntegers, sortCheck)

        // then
        assert(sorted)
    }

    @Test
    fun `should return false when int list is not sorted`() {
        // given
        val unsortedIntegers = listOf(1, 2, 4, 3, 5)
        val sortCheck = { a: Int, b: Int -> a <= b }

        // when
        val sorted = isSorted(unsortedIntegers, sortCheck)

        // then
        assert(sorted.not())
    }

    @Test
    fun `should return true when string list is sorted by length`() {
        // given
        val sortedStrings = listOf("aadasdas", "aasda", "bba", "ca", "a")
        val sortCheck = { a: String, b: String -> a.length >= b.length }

        // when
        val sorted = isSorted(sortedStrings, sortCheck)

        // then
        assert(sorted)
    }

    @Test
    fun `should return false when string list is not sorted by length`() {
        // given
        val unsortedStrings = listOf("a", "aasda", "b", "ca", "a")
        val sortCheck = { a: String, b: String -> a.length >= b.length }

        // when
        val sorted = isSorted(unsortedStrings, sortCheck)

        // then
        assert(sorted.not())
    }
}