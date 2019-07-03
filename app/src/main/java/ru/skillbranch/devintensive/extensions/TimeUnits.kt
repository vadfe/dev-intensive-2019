package ru.skillbranch.devintensive.extensions

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
    return "$value ${li?.getOrNull(pos)}"
}