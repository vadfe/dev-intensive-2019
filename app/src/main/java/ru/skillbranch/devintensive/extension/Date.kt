package ru.skillbranch.devintensive.extension

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern
import kotlin.math.abs
import kotlin.math.absoluteValue

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"):String{
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value:Int, units:TimeUnits = TimeUnits.SECOND): Date{
    var time = this.time
    time += when(units){
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun convert_str_day(dif:Int):String?{
    val  min = listOf("день","дня","дней")
    val z = dif % 10
    var sec_txt:String? = when(dif){
        in 10..20 -> min.get(2)
        in 110..120 -> min.get(2)
        in 210..220 -> min.get(2)
        in 310..320 -> min.get(2)
        else  -> null
    }

    if(sec_txt == null)
        sec_txt= when(z){
            0 -> min.get(2)
            in 2..4 -> min.get(1)
            in 5..9 -> min.get(2)
            1 -> min.get(0)
            else  -> min.get(2)
        }
    return sec_txt
}

fun convert_str_hor(dif:Int):String{
    val  min = listOf("час","часа","часов")
    var sec_txt:String = when(dif){
        0 -> min.get(2)
        1,21 -> min.get(0)
        in 2..4 -> min.get(1)
        in 22..24 -> min.get(1)
        in 5..20 -> min.get(2)
        else  -> min.get(2)
    }
    return sec_txt
}

fun convert_str_min(dif:Int):String{
    val  min = listOf("минуту","минуты","минут")
    var sec_txt:String = when(dif){
        0 -> min.get(2)
        1,21,31,41,51 -> min.get(0)
        in 2..4 -> min.get(1)
        in 22..24 -> min.get(1)
        in 32..34 -> min.get(1)
        in 42..44 -> min.get(1)
        in 52..54 -> min.get(1)
        in 5..20 -> min.get(2)
        in 25..30 -> min.get(2)
        in 35..40 -> min.get(2)
        in 45..50 -> min.get(2)
        in 55..60 -> min.get(2)
        else  -> min.get(2)
    }
    return sec_txt
}

fun Date.humanizeDiff(date:Date? = this):String{
    var dt:Date = if(date != null) date else this

   // 0с - 1с "только что"
   // 1с - 45с "несколько секунд назад"
   // 45с - 75с "минуту назад"
   // 75с - 45мин "N минут назад"
   // 45мин - 75мин "час назад"
   // 75мин 22ч "N часов назад"
   // 22ч - 26ч "день назад"
   // 26ч - 360д "N дней назад"
   // >360д "более года назад"
    var dif:Int = ((Date().time - dt.time)/ SECOND.toInt()).toInt()
    var dif_min:Int = abs(((Date().time - dt.time)/ MINUTE.toInt()).toInt())
    var dif_hor:Int = abs(((Date().time - dt.time)/ HOUR.toInt()).toInt())
    var dif_day:Int = abs(((Date().time - dt.time)/ DAY.toInt()).toInt())
    var strdif:String
    if(dif >= 0)
        strdif = when(dif){
            in 0..1 -> "только что"
            in 1..45 -> "несколько секунд назад"
            in 45..75 -> "минуту назад"
            in 75..2700 -> "$dif_min ${convert_str_min(dif_min)} назад"
            in 2700..4500 -> "час назад"
            in 4500..79200 -> "$dif_hor ${convert_str_hor(dif_hor)} назад"
            in 79200..93600 -> "день назад"
            in 93600..31104000 -> "$dif_day ${convert_str_day(dif_day)} назад"
            else -> "более года назад"
        }
    else{
        strdif = when(abs(dif)){
            in 0..1 -> "только что"
            in 1..45 -> "через несколько секунд"
            in 45..75 -> "через минуту"
            in 75..2700 -> "через $dif_min ${convert_str_min(dif_min)}"
            in 2700..4500 -> "через час"
            in 4500..79200 -> "через $dif_hor ${convert_str_hor(dif_hor)}"
            in 79200..93600 -> "через день"
            in 93600..31104000 -> "через $dif_day ${convert_str_day(dif_day)}"
            else -> "более чем через год"
        }
    }

    return "$strdif"
}

enum class TimeUnits{
    SECOND,
    MINUTE,
    HOUR,
    DAY
}