package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

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

fun Date.humanizeDiff(date:Date? = this):String{
    val dt:Date = if(date != null) date else this
    val dif:Int = ((Date().time - dt.time)/ SECOND.toInt()).toInt()
    val dif_min:Int = abs(((Date().time - dt.time)/ MINUTE.toInt()).toInt())
    val dif_hor:Int = abs(((Date().time - dt.time)/ HOUR.toInt()).toInt())
    val dif_day:Int = abs(((Date().time - dt.time)/ DAY.toInt()).toInt())
    val strdif:String
    if(dif >= 0)
        strdif = when(dif){
            in 0..1 -> "только что"
            in 1..45 -> "несколько секунд назад"
            in 45..75 -> "минуту назад"
            in 75..2700 -> "$dif_min ${TimeUnits.MINUTE.plural(dif_min)} назад"
            in 2700..4500 -> "час назад"
            in 4500..79200 -> "$dif_hor ${TimeUnits.HOUR.plural(dif_hor)} назад"
            in 79200..93600 -> "день назад"
            in 93600..31104000 -> "$dif_day ${TimeUnits.DAY.plural(dif_day)} назад"
            else -> "более года назад"
        }
    else{
        strdif = when(abs(dif)){
            in 0..1 -> "только что"
            in 1..45 -> "через несколько секунд"
            in 45..75 -> "через минуту"
            in 75..2700 -> "через $dif_min ${TimeUnits.MINUTE.plural(dif_min)}"
            in 2700..4500 -> "через час"
            in 4500..79200 -> "через $dif_hor ${TimeUnits.HOUR.plural(dif_hor)}"
            in 79200..93600 -> "через день"
            in 93600..31104000 -> "через $dif_day ${TimeUnits.DAY.plural(dif_day)}"
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

fun TimeUnits.plural(value:Int): String? {
    val mapa  = mapOf(TimeUnits.SECOND to listOf("секунду","секунды","секунд"),
        TimeUnits.MINUTE to listOf("минуту","минуты","минут"),
        TimeUnits.HOUR to listOf("час","часа","часов"),
        TimeUnits.DAY to listOf("день","дня","дней"))
    val li = mapa.get(this)
    val z = value % 10
    var pos:Int? = when(value){
        in 10..20 -> 2
        in 110..120 -> 2
        in 210..220 -> 2
        in 310..320 -> 2
        else  -> null
    }
    if(pos == null)
        pos= when(z){
            0 -> 2
            in 2..4 -> 1
            in 5..9 -> 2
            1 -> 0
            else  -> 0
        }
    return li?.getOrNull(pos)
}