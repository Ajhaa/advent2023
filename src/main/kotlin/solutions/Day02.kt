package solutions

import kotlin.math.max

enum class Color(val defaultLimit: Int) {
    RED(12), GREEN(13), BLUE(14)
}

typealias GameRound = Map<Color, Int>

@Suppress("unused")
class Day02 : Solution {
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

    override fun answerPart1(input: List<String>): Any {
        return input.filter(::checkGame)
            .sumOf(::extractIndex)
    }

    override fun answerPart2(input: List<String>): Any {
        return input.sumOf(::calculateGamePower)
    }
}