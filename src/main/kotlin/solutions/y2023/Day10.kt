package solutions.y2023

import solutions.PuzzleInput
import solutions.Solution

class Day10 : Solution {
    override fun answerPart1(input: PuzzleInput): Any {
        val map = createNeighborList(input.lines).withDefault { mutableListOf() }

        return bfs(map)
    }

    override fun answerPart2(input: PuzzleInput): Any {
        return 0
    }

    class Node(
        val x: Int, val y: Int, val type: Char
    ) {
        companion object {
            fun createIntoMap(
                node: Node, x: Int, y: Int, input: List<String>, target: MutableMap<Node, MutableList<Node>>
            ) {
                if (y < 0 || y >= input.size || x < 0 || x >= input[y].length) return
                val type = input[y][x]
                if ("|-LF7JS".contains(type)) {
                    val list = target.getValue(node)
                    list.add(Node(x, y, type))
                    target[node] = list
                }
            }
        }

        override fun hashCode(): Int {
            return x.hashCode() + 31 * y.hashCode()
        }

        override fun equals(other: Any?): Boolean {
            val o = other as Node
            return this.x == o.x && this.y == o.y
        }
    }

    private fun bfs(map: Map<Node, List<Node>>): Int {
        val visited = mutableMapOf<Node, Boolean>().withDefault { false }
        val depths = mutableMapOf<Node, Int>().withDefault { 1 }
        val startKey = map.keys.find { it.type == 'S' }
        val start = map[startKey]!!
        visited[startKey!!] = true
        val queue = ArrayDeque<Node>(start)

        var current: Node? = null

        while (queue.isNotEmpty()) {
            current = queue.removeFirst()
            visited[current] = true

            for (next in map[current]!!) {
                if (!visited.getValue(next)) {
                    depths[next] = depths.getValue(current) + 1
                    queue.addLast(next)
                }
            }

        }

        return depths[current]!!
    }


    private fun createNeighborList(input: List<String>): Map<Node, List<Node>> {
        val map = mutableMapOf<Node, MutableList<Node>>().withDefault { mutableListOf<Node>() }

        for ((y, line) in input.withIndex()) {
            for ((x, pipe) in line.withIndex()) {
                val node = Node(x, y, pipe)
                when (pipe) {
                    'F' -> {
                        Node.createIntoMap(node, x + 1, y, input, map)
                        Node.createIntoMap(node, x, y + 1, input, map)
                    }

                    'J' -> {
                        Node.createIntoMap(node, x - 1, y, input, map)
                        Node.createIntoMap(node, x, y - 1, input, map)
                    }

                    'L' -> {
                        Node.createIntoMap(node, x + 1, y, input, map)
                        Node.createIntoMap(node, x, y + -1, input, map)
                    }

                    '7' -> {
                        Node.createIntoMap(node, x - 1, y, input, map)
                        Node.createIntoMap(node, x, y + 1, input, map)
                    }

                    '-' -> {
                        Node.createIntoMap(node, x + 1, y, input, map)
                        Node.createIntoMap(node, x - 1, y, input, map)
                    }

                    '|' -> {
                        Node.createIntoMap(node, x, y + 1, input, map)
                        Node.createIntoMap(node, x, y - 1, input, map)
                    }
                }
            }
        }

        val startNeighbors = mutableListOf<Node>()
        var start: Node? = null
        for ((key, value) in map) {
            if (value.any { it.type == 'S' }) {
                start = value.find { it.type == 'S' }!!
                startNeighbors.add(key)
            }
        }

        map[start!!] = startNeighbors

        return map
    }

    override val sampleInput: String
        get() = """
            ..F7.
            .FJ|.
            SJ.L7
            |F--J
            LJ...
        """.trimIndent()
}