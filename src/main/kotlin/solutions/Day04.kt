package solutions

import kotlin.math.min

@Suppress("unused")
class Day04 : Solution() {
    private fun extractNumbersList(str: String) =
        str.split(" ").filter(String::isNotBlank).map(String::toInt)

    private fun parseAndScore(card: String): Int {
        val matches = parseMatches(card)
        if (matches == 0) {
            return 0
        }

        return 1 shl (matches - 1)
    }

    private fun parseMatches(str: String): Int {
        val game = str.split(":")[1]
        val numberStrings = game.split("|")
        val winningNumbers = extractNumbersList(numberStrings[0])
        val numbers = extractNumbersList(numberStrings[1])

        return numbers.toSet().intersect(winningNumbers.toSet()).size
    }

    override fun answerPart1(): Any {
        return inputLines.sumOf(::parseAndScore)
    }

    override fun answerPart2(): Any {
        val extraCards = MutableList(inputLines.size) { 1 }
        var totalCardAmount = 0

        inputLines.forEachIndexed { index, card ->
            val cardAmount = extraCards[index]
            totalCardAmount += cardAmount

            val result = parseMatches(card)

            val target = min(index + result, extraCards.size - 1)
            for (i in (index + 1)..target) {
                extraCards[i] += cardAmount
            }

        }

        return totalCardAmount
    }

    override val sampleInput = """
        Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
        Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
        Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
        Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
        Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
        Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
    """.trimIndent()
}