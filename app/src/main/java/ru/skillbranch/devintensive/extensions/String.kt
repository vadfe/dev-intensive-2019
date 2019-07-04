package ru.skillbranch.devintensive.extensions

fun String.truncate(tr:Int = 15):String{
    var res = this
    val le = tr
    while(res.substring(res.length-1) == " ")
        res = res.substring(0,res.length-1)

    val nad:Boolean = if(res.length > le) true else false
    res = this.substring(0,if(le<this.length)le else this.length)
    while(res.substring(res.length-1) == " ")
        res = res.substring(0,res.length-1)

    return  res+if(nad) "..." else ""
}

fun String.stripHtml():String{
    val pattern = "\\s+".toRegex()
    val pattern2 = "\\<[^>]*>".toRegex()
    return  this.replace(pattern," ").replace(pattern2,"")
}