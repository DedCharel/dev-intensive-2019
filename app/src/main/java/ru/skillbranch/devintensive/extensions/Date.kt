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

fun Date.add(value:Int, units:TimeUnits = TimeUnits.SECOND): Date{
    var time = this.time

    time += when(units){
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE-> value * MINUTE
        TimeUnits.HOUR-> value * HOUR
        TimeUnits.DAY -> value * DAY

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
        in 76..45*60 ->"${TimeUnits.MINUTE.plural((interval/60).toInt())} назад"
        in (45*60+1)..75*60 ->"час назад"
        in (75*60+1)..22*3600 ->"${TimeUnits.HOUR.plural((interval/3600).toInt())} назад"
        in (22*3600+1)..26*3600 -> "день назад"
        in (26*3600+1)..360*3600*24 ->"${TimeUnits.DAY.plural((interval/3600/24).toInt())} назад"
        else -> "более года назад"
    }
return "$message"
}







enum class TimeUnits{
    SECOND,
    MINUTE,
    HOUR,
    DAY;

        fun plural(int:Int):String{
            var result:String =""
            val rem100: Int = int%100
            val rem: Int = int%10
            if ( rem100 in 10..19){
                result = when(this.name){
                    SECOND.name -> "$int секунд"
                    MINUTE.name -> "$int минут"
                    HOUR.name -> "$int часов"
                    DAY.name -> "$int дней"
                    else ->""
                }

            } else {
            when(rem){
                0 ->{
                    result = when(this.name){
                        SECOND.name -> "$int секунд"
                        MINUTE.name -> "$int минут"
                        HOUR.name -> "$int часов"
                        DAY.name -> "$int дней"
                        else ->""
                    }
                }
                1 ->{
                    result = when(this.name){
                        SECOND.name -> "$int секунда"
                        MINUTE.name -> "$int минута"
                        HOUR.name -> "$int час"
                        DAY.name -> "$int день"
                        else ->""
                    }
                }
                in 2..4 ->{
                    result = when(this.name){
                        SECOND.name -> "$int секунды"
                        MINUTE.name -> "$int минуты"
                        HOUR.name -> "$int часа"
                        DAY.name -> "$int дня"
                        else ->""
                    }
                }
                in 5..9 ->{
                    result = when(this.name){
                        SECOND.name -> "$int секунд"
                        MINUTE.name -> "$int минут"
                        HOUR.name -> "$int часов"
                        DAY.name -> "$int дней"
                        else ->""
                    }
                }
            }
            }


          return result
        }


}
