package solutions

import org.junit.jupiter.api.assertAll
import java.time.LocalDate
import kotlin.test.Test

class SolutionsTest {
    private val correctAnswers = listOf(
        listOf(53194, 54249),
        listOf(2528, 67363),
        listOf(525181, 84289137),
        listOf(25183, 5667240)
    )

    @Test
    fun `test all days`() {
        val currentDate = LocalDate.now()

        val days = if (currentDate.isAfter(LocalDate.of(2023, 12, 25))) {
            25
        } else {
            currentDate.dayOfMonth
        }

        val solutions = (1..days).map { runSolution(it) }
        assertAll(solutions.flatMap(::getAssertions))
    }

    private fun getAssertions(solution: SolutionResult): List<() -> Unit> {
        val dayAnswers = correctAnswers[solution.day - 1]
        return solution.results
            .map { it.first }
            .zip(dayAnswers)
            .map {
                {
                    assert(it.second == it.first) { "day ${solution.day} incorrect. expected ${it.second}, got ${it.first}" }
                }
            }
    }
}