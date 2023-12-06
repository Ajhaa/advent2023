package solutions

class Day06 : Solution() {

    data class Race(
        val time: Long,
        val distance: Long,
    )

    private fun raceFromPair(pair: Pair<Long, Long>) : Race {
        return Race(pair.first, pair.second)
    }

    private fun countSolutions(race: Race) : Int {
        var solutionCount = 0
        for (accelerationTime in 1..race.time) {
            val remainingTime = race.time - accelerationTime
            val speed = accelerationTime

            if (remainingTime * speed > race.distance) {
                solutionCount++
            }
        }

        return solutionCount
    }

    private val whitespaceRegex = Regex("\\s+")
    private fun parseNumberStrings(str: String): List<String> {
        return str.split(":")[1]
            .split(whitespaceRegex)
            .filter { it.isNotBlank() }
    }

    override fun answerPart1(): Any {
        val lines = inputLines
        val times = parseNumberStrings(lines[0]).map(String::toLong)
        val distances = parseNumberStrings(lines[1]).map(String::toLong)

        val races = times.zip(distances).map(::raceFromPair)
        return races.map(::countSolutions).reduce(Int::times)
    }

    override fun answerPart2(): Any {
        val lines = inputLines
        val time = parseNumberStrings(lines[0]).joinToString("").toLong()
        val distance = parseNumberStrings(lines[1]).joinToString("").toLong()
        return countSolutions(Race(time, distance))
    }

    override val sampleInput = """
        Time:      7  15   30
        Distance:  9  40  200
    """.trimIndent()
}