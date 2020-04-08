package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR
fun Date.format(pattern:String = "HH:mm:ss dd:MM:yy"):String{
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value:Int, units:TimeUnites = TimeUnites.SECOND): Date{
    var time = this.time

    time += when(units){
        TimeUnites.SECOND -> value * SECOND
        TimeUnites.MINUTE-> value * MINUTE
        TimeUnites.HOUR-> value * HOUR
        TimeUnites.DAY -> value * DAY

    }
    this.time = time
    return  this

}

fun Date.humanizeDiff(date: Date = Date()): String {
return ""
}

enum class TimeUnites{
    SECOND,
    MINUTE,
    HOUR,
    DAY
}