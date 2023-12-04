package solutions

import kotlin.math.max

enum class Color(val defaultLimit: Int) {
    RED(12), GREEN(13), BLUE(14)
}

typealias GameRound = Map<Color, Int>

@Suppress("unused")
class Day02 : Solution() {
    private fun gameRoundFromStr(str: String): GameRound {
        val gameRound: MutableMap<Color, Int> = mutableMapOf()

        for (colorAmount in str.split(",")) {
            val (amountStr, color) = colorAmount.trim().split(" ")

            val amount = amountStr.toInt()
            val colorValue = color.trim().uppercase()

            gameRound[Color.valueOf(colorValue)] = amount
        }

        return gameRound
    }

    private fun parseGame(gameStr: String): List<GameRound> {
        return gameStr.split(":")[1]
            .split(";")
            .map(::gameRoundFromStr)
    }

    private fun checkGame(gameStr: String): Boolean {
        return parseGame(gameStr)
            .flatMap { it.entries }
            .none { it.value > it.key.defaultLimit }
    }

    private fun extractIndex(gameStr: String): Int {
        return gameStr.split(":")[0]
            .split(" ")[1].toInt()
    }

    private fun calculateGamePower(gameStr: String): Int {
        val minimums = Color.entries.associateWith { 0 }
            .toMutableMap()

        val game = parseGame(gameStr)

        for (round in game) {
            round.entries.forEach {
                val (color, amount) = it
                minimums[color] = max(amount, minimums.getValue(color))
            }
        }

        return minimums.values.reduce(Int::times)
    }

    override fun answerPart1(): Any {
        return inputLines.filter(::checkGame)
            .sumOf(::extractIndex)
    }

    override fun answerPart2(): Any {
        return inputLines.sumOf(::calculateGamePower)
    }

    override val sampleInput = """
        Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
        Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
        Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
        Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
    """.trimIndent()
}