package solutions

@Suppress("unused")
class Day03 : Solution() {
    private var line = 0
    private var col = 0

    private val starMap: MutableMap<Pair<Int, Int>, MutableList<Int>> = mutableMapOf()

    private fun addToStar(line: Int, col: Int, number: Int) {
        val pair = Pair(line, col)

        if (starMap[pair] == null) {
            starMap[pair] = mutableListOf()
        }

        starMap[pair]!!.add(number)
    }

    private fun check(number: Int, startIndex: Int, endIndex: Int) : Boolean {
        var foundSpecialChar = false
        for (offset in -1..1) {
            val targetLine = line + offset
            if (targetLine < 0 || targetLine >= inputLines.size) {
                continue
            }

            for (pos in (startIndex - 1)..(endIndex)) {
                val char = inputLines[targetLine].getOrNull(pos)
                if (char != null && !char.isDigit() && char != '.') {
                    if (char == '*') {
                        addToStar(targetLine, pos, number)
                    }

                    foundSpecialChar = true
                }
            }
        }

        return foundSpecialChar
    }

    private fun extractAndCheck() : Int {
        val startIndex = col
        val currentLine = inputLines[line]
        var char: Char? = currentLine[col]

        while (char != null && char.isDigit() && col < currentLine.length) {
            col++
            char = currentLine.getOrNull(col)
        }

        val number = currentLine.substring(startIndex, col).toInt()

        return if (check(number, startIndex, col)) {
            number
        } else {
            0
        }
    }

    override fun answerPart1(): Any {
        var sum = 0
        while (line < inputLines.size) {
            col = 0
            while (col < inputLines[line].length) {
                if (inputLines[line][col].isDigit()) {
                    sum += extractAndCheck()
                }
                col++
            }
            line++
        }

        return sum
    }

    override fun answerPart2(): Any {
        if (starMap.isEmpty()) {
            answerPart1()
        }

        return starMap.values
            .filter { it.size == 2 }
            .sumOf { it[0] * it[1] }
    }
}