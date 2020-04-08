package ru.skillbranch.devintensive.extensions

import java.sql.Time
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

fun Date.humanizeDiff(date: Date= Date()): String {
    val interval = -(this.time- date.time)/ SECOND

    val message:String = when(interval){
        in 0..1 -> "только что"
        in 2..45 -> "несколько секунд назад"
        in 46..75 -> "минуту назад"
        in 76..45*60 ->"N минут назад"
        in (45*60+1)..75*60 ->"час назад"
        in (75*60+1)..22*3600 ->"N часов назад"
        in (22*3600+1)..26*3600 -> "день назад"
        in (26*3600+1)..360*3600*24 ->"N дней назад"
        else -> "более года назад"
    }
return "$message"
}





enum class TimeUnites{
    SECOND,
    MINUTE,
    HOUR,
    DAY
}