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
}