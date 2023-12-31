package solutions

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.assertAll
import util.currentAdventDay
import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.time.Duration

class SolutionsTest {
    private val correctAnswers: List<List<Any>> = listOf(
        listOf(53194, 54249),
        listOf(2528, 67363),
        listOf(525181, 84289137),
        listOf(25183, 5667240),
        listOf(650599855L, 1240035L),
        listOf(220320, 34454850),
        listOf(253638586L, 253253225L),
        listOf(23147, BigInteger("22289513667691")),
        listOf(2105961943L, 1019L),
        listOf(6823, 0),
        listOf(9214785L, 613686987427L)
    )

    @Test
    fun `test all days`() {
        val days = currentAdventDay(2023)

        val solutions = (1..days).map { runSolution(it, doWarmup = true) }
        assertAll(solutions.flatMap(::getAssertions))
    }

    @Disabled
    @Test
    fun `all solutions are fast enough`() {
        val days = currentAdventDay(2023)

        val solutions = (1..days).map { runSolution(it, doWarmup = true) }
        assertAll(solutions.flatMap(::getAssertions))

        assertTrue(solutions.flatMap { it.results }.all { it.second < Duration.parse("5ms") })
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