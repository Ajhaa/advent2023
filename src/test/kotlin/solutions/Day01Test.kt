package solutions

import kotlin.test.Test
import kotlin.test.assertEquals

class Day01Test {

    @Test
    fun `extractNumber parses number words correctly`() {
        val d1 = Day01()
        assertEquals("13", d1.answerPart2("abcone2threexyz"))

        assertEquals("76", d1.answerPart2("7pqrstsixteen"))

        assertEquals("71", d1.answerPart2("sevenntgvnrrqfvxh2ttnkgffour8fiveone"))
    }
}