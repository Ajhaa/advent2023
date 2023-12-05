package solutions

import kotlin.math.min

@Suppress("unused")
class Day05 : Solution() {

    data class Range(
        var start: Long,
        var end: Long,
    )

    data class RangeMap(
        val sourceStart: Long,
        val destinationStart: Long,
        val size: Long,
    ) {

        val sourceEnd: Long
            get() = sourceStart + size - 1

        val destinationEnd: Long
            get() = destinationStart + size - 1

        fun map(num: Long): Long? {
            if (sourceStart <= num && num < (sourceStart + size)) {
                return destinationStart + (num - sourceStart)
            }

            return null
        }

        fun mapTo(range: Range, target: MutableList<Range>) : Boolean {
            if (range.start > sourceEnd) return false

            if (range.start >= sourceStart && range.end <= sourceEnd ) {
                val offset = range.start - sourceStart
                val endDistance = range.end - range.start
                range.start = destinationStart + offset
                range.end = range.start + endDistance

                target.add(range)
                return true
            }

            // range starts in mapping range but ends after it
            if (range.start in sourceStart..sourceEnd) {
                val offset = range.start - sourceStart

                target.add(Range(destinationStart + offset, destinationEnd))

                range.start = sourceStart + size
                return false
            }

            // range starts before mapping range
            if (range.end >= sourceStart) {
                target.add(Range(range.start, sourceStart - 1))
                range.start = sourceStart
                return mapTo(range, target)
            }

            return false
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


    // How to handle range split in half by rangemap
    private fun mapRangesToNext(ranges: List<Range>, rangeMaps: List<RangeMap>) : List<Range> {
        val nexts = mutableListOf<Range>()

        for (range in ranges) {
            var consumedRange = false
            for (rangeMap in rangeMaps) {
                consumedRange = rangeMap.mapTo(range, nexts)
                if (consumedRange) break
            }

            if (!consumedRange) {
                nexts.add(range)
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

    private fun calculateRangeLowestLocation(lines: List<String>, seeds: List<Range>) : Long {
        var mapped = seeds
        var startIndex = 3
        for (i in 2..<lines.size) {
            if (lines[i].isBlank()) {
                val ranges = createRanges(
                    lines.subList(
                        startIndex, i
                    )
                )
                mapped = mapRangesToNext(mapped, ranges.sortedBy { it.sourceStart })

                startIndex = i + 2
            }
        }

        return mapped.minOf { it.start }
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
            seedRanges.add(Range(start, start + size - 1))
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

        return calculateRangeLowestLocation(lines, parseSeedRanges(seeds))
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

    val testInput = """
        seeds: 30 10 5 6 20 10

        seed-to-soil map:
        25 1 10
        15 17 5
        5 33 5
    """.trimIndent().lines()
}