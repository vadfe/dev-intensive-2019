package ru.skillbranch.devintensive.extensions

fun String.truncate(tr:Int = 16):String{
    var res = this.substring(0,if(tr<this.length)tr else this.length)
    if(res.substring(res.length-1) == " ")
        res = res.substring(0,res.length-1)
    return  res+if(this.length > tr) "..." else ""
}

fun String.stripHtml():String{
    val pattern = "\\s+".toRegex()
    val pattern2 = "\\<[^>]*>".toRegex()
    return  this.replace(pattern," ").replace(pattern2,"")
}