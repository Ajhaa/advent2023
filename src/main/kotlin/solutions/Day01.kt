package solutions

import java.lang.Exception

class Day01 : Solution {
    private val digitWords = mapOf(
        "one" to '1',
        "two" to '2',
        "three" to '3',
        "four" to '4',
        "five" to '5',
        "six" to '6',
        "seven" to '7',
        "eight" to '8',
        "nine" to '9',
    )

    private fun extractNumber(str: String, enableNumberWords: Boolean = false): Int {
        var first: Char? = null
        var latest: Char? = null

        for (i in str.indices) {
            var char = str[i]

            if (!char.isDigit() && enableNumberWords) {
                for (key in digitWords.keys) {
                    val substr = try {
                        str.substring(i, i + key.length)
                    } catch (e: Exception) {
                        continue
                    }

                    if (substr == key) {
                        char = digitWords[key]!!
                        break
                    }
                }
            }

            if (char.isDigit()) {
                if (first == null) {
                    first = char
                }
                latest = char
            }
        }

        return (String() + first + latest).toInt()
    }

    override fun answerPart1(input: String): String {
        return input.split("\n")
            .map(::extractNumber)
            .sum()
            .toString()
    }

    override fun answerPart2(input: String): String {
        return input.split("\n")
            .sumOf { extractNumber(it, enableNumberWords = true) }
            .toString()
    }
}