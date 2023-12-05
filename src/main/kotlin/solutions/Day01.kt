package solutions

class Day01 : Solution() {
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

    private fun String.safeSubstring(start: Int, end: Int): String? {
        if (start < 0 || end > this.length) {
            return null
        }

        return substring(start, end)
    }

    private fun extractDigit(str: String, reverse: Boolean = false, enableNumberWords: Boolean) : Char {
        val indices = if (reverse) str.indices.reversed() else str.indices

        for (i in indices) {
            val char = str[i]

            if (!char.isDigit() && enableNumberWords) {
                for (len in 3..5) {
                    val substring =
                        if (reverse) str.safeSubstring(i - len + 1, i + 1) else str.safeSubstring(i, i + len)


                    val num = digitWords[substring]
                    if (num != null) {
                        return num
                    }
                }
            }

            if (char.isDigit()) {
                return char
            }
        }

        throw RuntimeException("No digit found")
    }

    private fun extractNumber(str: String, enableNumberWords: Boolean = false): Int {
        val first = extractDigit(str, enableNumberWords = enableNumberWords)
        val last = extractDigit(str, reverse = true, enableNumberWords = enableNumberWords)


        return (String() + first + last).toInt()
    }

    override fun answerPart1(): Any {
        return inputLines.map(::extractNumber).sum()
    }

    override fun answerPart2(): Any {
        return inputLines.sumOf { extractNumber(it, enableNumberWords = true) }
    }

    override val sampleInput = """
        1abc2
        pqr3stu8vwx
        a1b2c3d4e5f
        treb7uchet
    """.trimIndent()
}