package solutions.y2023

import solutions.PuzzleInput
import solutions.Solution

class Day07 : Solution {
    private var enableJoker = false

    override fun answerPart1(input: PuzzleInput): Any {
        val lines = input.lines
        val hands = lines
            .map { Hand(it.split(" ")[0]) }
            .sorted()


        val handValues = mutableMapOf<String, Long>()

        for ((i, hand) in hands.withIndex()) {
            handValues[hand.cards] = i + 1L
        }

        return lines
            .sumOf {
                val (hand, score) = it.split(" ")
                val orderValue = handValues.getValue(hand)
                score.toLong() * orderValue
            }
    }

    override fun answerPart2(input: PuzzleInput): Any {
        enableJoker = true
        val answer = answerPart1(input)
        enableJoker = false
        return answer
    }

    fun compareCard(first: Char, second: Char): Int {
        if (first == second) return 0
        if (enableJoker && first == 'J') return -1
        if (enableJoker && second == 'J') return 1

        if (first.isDigit()) {
            if (second.isDigit()) {
                return first.compareTo(second)
            }
            return -1
        }

        if (second.isDigit()) {
            return 1
        }

        return order.indexOf(first) - order.indexOf(second)
    }

    enum class HandType : Comparable<HandType> {
        FIVE_OF_KIND,
        FOUR_OF_KIND,
        FULL_HOUSE,
        THREE_KIND,
        TWO_PAIR,
        ONE_PAIR,
        HIGH_CARD;

        companion object {
            fun fromString(hand: String, jokerEnabled: Boolean = false): HandType {
                val cards = mutableMapOf<Char, Int>().withDefault { 0 }
                for (char in hand) {
                    val oldVal = cards.getValue(char)
                    cards[char] = oldVal + 1
                }

                val filtered  = cards.filter { !jokerEnabled || it.key != 'J' }
                val highestAmount = if (filtered.isEmpty()) 0 else filtered.maxOf { it.value }

                val type =  when (highestAmount) {
                    5 -> FIVE_OF_KIND
                    4 -> FOUR_OF_KIND
                    3 -> if (filtered.any { it.value == 2 }) FULL_HOUSE else THREE_KIND
                    2 -> if (filtered.count { it.value == 2 } == 2) TWO_PAIR else ONE_PAIR
                    else -> HIGH_CARD
                }
                val jokerCount = cards.getValue('J')

                if (!jokerEnabled) return type

                return upgrade(type, jokerCount)
            }

            private fun upgrade(type: HandType, jokerCount: Int) : HandType {
                if (jokerCount == 0) return type
                return when (type) {
                    FOUR_OF_KIND -> FIVE_OF_KIND
                    THREE_KIND -> if (jokerCount == 1) FOUR_OF_KIND else FIVE_OF_KIND
                    TWO_PAIR -> FULL_HOUSE
                    ONE_PAIR -> upgrade(THREE_KIND, jokerCount - 1)
                    HIGH_CARD -> upgrade(ONE_PAIR, jokerCount - 1)
                    else -> type
                }
            }
        }
    }

    inner class Hand(val cards: String) : Comparable<Hand> {
        val type = HandType.fromString(cards, enableJoker)

        override fun compareTo(other: Hand): Int {
            val typeCompare = other.type.compareTo(this.type)
            if (typeCompare == 0) {
                for (i in cards.indices) {
                    val cardDifference = compareCard(cards[i], other.cards[i])
                    if (cardDifference != 0) return cardDifference
                }

                return 0
            }

            return typeCompare
        }

        override fun toString(): String {
            return "$type: $cards"
        }
    }

    private val order = listOf(
        'A', 'K', 'Q', 'J', 'T'
    ).reversed()

    override val sampleInput: String
        get() = """
            32T3K 765
            T55J5 684
            KK677 28
            KTJJT 220
            QQQJA 483
        """.trimIndent()
}