package solutions.y2023

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals


typealias Type = Day07.HandType

class Day07Test {


    @Test
    fun `cards are parsed correctly`() {
        assertEquals(Type.HIGH_CARD, Type.fromString("AK234"))
        assertEquals(Type.ONE_PAIR, Type.fromString("A23A4"))
        assertEquals(Type.TWO_PAIR, Type.fromString("A43A4"))
        assertEquals(Type.THREE_KIND, Type.fromString("443A4"))
        assertEquals(Type.FULL_HOUSE, Type.fromString("44AA4"))
        assertEquals(Type.FOUR_OF_KIND, Type.fromString("44A44"))
        assertEquals(Type.FIVE_OF_KIND, Type.fromString("44444"))
    }

    @Test
    fun `cards with jokers are parsed correctly`() {
        assertAll("Multiple assertions",
            { assertEquals(Type.FIVE_OF_KIND, fromStringJoker("JJJJJ")) },
            { assertEquals(Type.FOUR_OF_KIND, fromStringJoker("JJJ12")) },
            { assertEquals(Type.FULL_HOUSE, fromStringJoker("AAKKJ")) },
            { assertEquals(Type.FIVE_OF_KIND, fromStringJoker("AAAJJ")) },
            { assertEquals(Type.FOUR_OF_KIND, fromStringJoker("AAJJ1")) },
            { assertEquals(Type.ONE_PAIR, fromStringJoker("A123J")) },
            { assertEquals(Type.THREE_KIND, fromStringJoker("A12JJ")) })

    }

    private fun fromStringJoker(s: String): Type {
        return Type.fromString(s, true)
    }
}