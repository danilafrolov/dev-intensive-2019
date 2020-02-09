package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.utils.Utils
import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR


fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val diff = date.time - this.time
    return when {
        diff < -360 * DAY -> "более чем через год"
        diff in -360 * DAY until -26 * HOUR -> "через ${TimeUnits.DAY.plural((diff / -DAY).toInt())}"
        diff in -26 * HOUR until -22 * HOUR - 1 -> "через день"
        diff in -22 * HOUR until -75 * MINUTE - 1 -> "через ${TimeUnits.HOUR.plural((diff / -HOUR).toInt())}"
        diff in -75 * MINUTE until -45 * MINUTE - 1 -> "через час"
        diff in -45 * MINUTE until -75 * SECOND - 1 -> "через ${TimeUnits.MINUTE.plural((diff / -MINUTE).toInt())}"
        diff in -75 * SECOND until -45 * SECOND - 1 -> "через минуту"
        diff in -45 * SECOND until -SECOND - 1 -> "через несколько секунд"
        diff in -SECOND until SECOND - 1 -> "только что"
        diff in SECOND until 45 * SECOND -> "несколько секунд назад"
        diff in 45 * SECOND until 75 * SECOND - 1 -> "минуту назад"
        diff in 75 * SECOND until 45 * MINUTE - 1 -> "${TimeUnits.MINUTE.plural((diff / MINUTE).toInt())} назад"
        diff in 45 * MINUTE until 75 * MINUTE - 1 -> "час назад"
        diff in 75 * MINUTE until 22 * HOUR - 1 -> "${TimeUnits.HOUR.plural((diff / HOUR).toInt())} назад"
        diff in 22 * HOUR until 26 * HOUR - 1 -> "день назад"
        diff in 26 * HOUR until 360 * DAY -> "${TimeUnits.DAY.plural((diff / DAY).toInt())} назад"
        diff >= 360 * DAY -> "более года назад"
        else -> ""
    }
}

enum class TimeUnits {
    SECOND {
        override fun plural(value: Int): String {
            return when (value) {
                in 5..20 -> "$value секунд"
                else -> {
                    when (Utils.getLastDigit(value)) {
                        0 -> "$value секунд"
                        1 -> "$value секунду"
                        in 2..4 -> "$value секунды"
                        in 5..9 -> "$value секунд"
                        else -> ""
                    }
                }
            }
        }

    },
    MINUTE {
        override fun plural(value: Int): String {
            return when (value) {
                in 5..20 -> "$value минут"
                else -> {
                    when (Utils.getLastDigit(value)) {
                        0 -> "$value минут"
                        1 -> "$value минуту"
                        in 2..4 -> "$value минуты"
                        in 5..9 -> "$value минут"
                        else -> ""
                    }
                }
            }
        }
    },
    HOUR {
        override fun plural(value: Int): String {
            return when (value) {
                in 5..20 -> "$value часов"
                else -> {
                    when (Utils.getLastDigit(value)) {
                        0 -> "$value часов"
                        1 -> "$value час"
                        in 2..4 -> "$value часа"
                        in 5..9 -> "$value часов"
                        else -> ""
                    }
                }
            }
        }

    },
    DAY {
        override fun plural(value: Int): String {
            return when (value) {
                in 5..20 -> "$value дней"
                else -> {
                    when (Utils.getLastDigit(value)) {
                        0 -> "$value дней"
                        1 -> "$value день"
                        in 2..4 -> "$value дня"
                        in 5..9 -> "$value дней"
                        else -> ""
                    }
                }
            }
        }
    };

    abstract fun plural(value: Int):String
}