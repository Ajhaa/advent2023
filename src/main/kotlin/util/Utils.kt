package util

import java.time.LocalDate
import java.time.Month

fun currentAdventDay(year: Int) : Int {
    val now = LocalDate.now()

    return if (now.isAfter(LocalDate.of(year, 12, 25))) {
        25
    } else if (now.month == Month.DECEMBER) {
        now.dayOfMonth
    } else {
        0
    }
}

fun <K>String.splitMap(splitBy: String, mapFun: (String) -> K) : List<K> {
    return this.split(splitBy).map(mapFun)
}