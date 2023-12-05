package solutions

@Suppress("unused")
class Day05 : Solution() {

    data class Range(
        val start: Long,
        val size: Long,
    )

    data class RangeMap(
        val sourceStart: Long,
        val destinationStart: Long,
        val size: Long,
    ) {
        fun map(num: Long): Long? {
            if (sourceStart <= num && num < (sourceStart + size)) {
                return destinationStart + (num - sourceStart)
            }

            return null
        }
    }

    private fun mapToNext(values: List<Long>, rangeMaps: List<RangeMap>): List<Long> {
        val nexts = mutableListOf<Long>()

        for (value in values) {
            var result: Long? = null
            for (range in rangeMaps) {
                result = range.map(value)
                if (result != null) break
            }

            if (result != null) {
                nexts.add(result)
            } else {
                nexts.add(value)
            }
        }

        return nexts
    }

    private fun createRanges(ranges: List<String>) : List<RangeMap> {
        return ranges.map {
            val (destination, source, size) = it.split(" ").map(String::toLong)
            RangeMap(source, destination, size)
        }
    }

    private fun calculateLowestLocation(lines: List<String>, seeds: List<Long>) : Long {
        var mapped = seeds
        var startIndex = 3
        for (i in 2..<lines.size) {
            if (lines[i].isBlank()) {
                val ranges = createRanges(
                    lines.subList(
                        startIndex, i
                    )
                )
                mapped = mapToNext(mapped, ranges)

                startIndex = i + 2
            }
        }

        return mapped.min()
    }
    override fun answerPart1(): Any {
        val lines = inputLines + ""
        val seeds = lines[0]
            .split(":")[1]
                .split(" ")
                .filter(String::isNotBlank)
                .map(String::toLong)

        return calculateLowestLocation(lines, seeds)
    }

    private fun parseSeedRanges(seeds: List<Long>) : List<Range> {
        val seedRanges = mutableListOf<Range>()
        for (i in seeds.indices step 2) {
            val (start, size) = seeds.subList(i, i + 2)
            seedRanges.add(Range(start, size))
        }

        return seedRanges
    }

    override fun answerPart2(): Any {
        val lines = inputLines + ""

        val seeds = lines[0]
            .split(":")[1]
            .split(" ")
            .filter(String::isNotBlank)
            .map(String::toLong)

        //return calculateLowestLocation(lines, parseSeedRanges(seeds))
        return 0L
    }

    override val sampleInput = """
        seeds: 79 14 55 13

        seed-to-soil map:
        50 98 2
        52 50 48

        soil-to-fertilizer map:
        0 15 37
        37 52 2
        39 0 15

        fertilizer-to-water map:
        49 53 8
        0 11 42
        42 0 7
        57 7 4

        water-to-light map:
        88 18 7
        18 25 70

        light-to-temperature map:
        45 77 23
        81 45 19
        68 64 13

        temperature-to-humidity map:
        0 69 1
        1 0 69

        humidity-to-location map:
        60 56 37
        56 93 4
    """.trimIndent()
}