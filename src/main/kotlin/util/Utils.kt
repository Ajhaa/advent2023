package util

import java.time.LocalDate

fun getChristmasDay(year: Int) : Int {
    val now = LocalDate.now()

    return if (now.isAfter(LocalDate.of(year, 12, 25))) {
        25
    } else {
        now.dayOfMonth
    }
}