package solutions.y2023

import solutions.PuzzleInput
import solutions.Solution
import java.math.BigInteger

class Day08 : Solution {
    override fun answerPart1(input: PuzzleInput): Any {
        val lines = input.lines
        val instructions = lines[0]
        val map = createMap(lines.subList(2, lines.size))

        var steps = 0
        var current = map.getValue("AAA")
        while (true) {
            val step = instructions[steps % instructions.length]
            steps++
            val nextKey = when (step) {
                'L' -> current.first
                'R' -> current.second
                else -> throw Error("Invalid step $step")
            }
            if (nextKey == "ZZZ") break
            current = map.getValue(nextKey)
        }

        return steps
    }


    override fun answerPart2(input: PuzzleInput): Any {
        val lines = input.lines
        val instructions = lines[0]
        val map = createMap(lines.subList(2, lines.size))

        val currents = map.filter { it.key.endsWith('A') }.keys

        val individualPaths = currents.map { BigInteger.valueOf(findZedEnding(it, instructions, map)) }

        return multiLcm(individualPaths)
    }

    private fun multiLcm(numbers: List<BigInteger>) : BigInteger {
        if (numbers.size == 0) return BigInteger.ZERO
        if (numbers.size == 1) return numbers[0]
        if (numbers.size == 2) return lcm(numbers[0], numbers[1])
        val newList = mutableListOf<BigInteger>()
        for (i in numbers.indices step 2) {
            val a = numbers[i]
            val b = numbers.getOrNull(i+1)
            if (b == null) {
                newList.add(a)
                break
            }

            newList.add(lcm(a, b))
        }

        return multiLcm(newList)
    }

    private fun lcm(a: BigInteger, b: BigInteger) : BigInteger {
        return (a * b) / a.gcd(b)
    }

    private fun findZedEnding(node: String, instructions: String, map: Map<String, Pair<String, String>>): Long {
        var steps = 0
        var current = map.getValue(node)
        while (true) {
            val step = instructions[steps % instructions.length]
            steps++
            val nextKey = when (step) {
                'L' -> current.first
                'R' -> current.second
                else -> throw Error("Invalid step $step")
            }
            if (nextKey.endsWith('Z')) {
                return steps.toLong()
            }
            current = map.getValue(nextKey)
        }
    }

    private fun createMap(lines: List<String>): Map<String, Pair<String, String>> {
        val travelMap = mutableMapOf<String, Pair<String, String>>()
        val regex = Regex("([A-Z0-9]{3})")

        for (line in lines) {
            val (source, targets) = line.split("=")

            val nodes = regex.findAll(targets)
            val (n1, n2) = nodes.map { it.value }.toList()
            travelMap[source.trim()] = Pair(n1, n2)
        }

        return travelMap
    }

    override val sampleInput: String
        get() = """
            LLR

            AAA = (BBB, BBB)
            BBB = (AAA, ZZZ)
            ZZZ = (ZZZ, ZZZ)
        """.trimIndent()
}