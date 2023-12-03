package solutions

import org.junit.jupiter.api.assertAll
import kotlin.test.Test
import kotlin.test.assertEquals

class SolutionsTest {
    @Test
    fun `day 1 is correct`() = checkSolution(1, 53194, 54249)
    @Test
    fun `day 2 is correct`() = checkSolution(2, 2528, 67363)

    private fun checkSolution(day: Int, vararg correctAnswers: Any) {
        val solution = runSolution(day)

        val assertions = solution.results
            .map { it.first }
            .zip(correctAnswers)
            .map {
                { assertEquals(it.second, it.first) }
            }

        assertAll(assertions)
    }
}