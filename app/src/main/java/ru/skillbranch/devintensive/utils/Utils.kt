package ru.skillbranch.devintensive.utils

val rus  = listOf("а","б","в","г","д","е","ё","ж","з","и","й","к","л","м","н","о","п","р","с","т","у","ф","х","ц","ч","ш","щ","ъ","ы","ь","э","ю","я", " ")
val rus_up  = listOf("А","Б","В","Г","Д","Е","Ё","Ж","З","И","Й","К","Л","М","Н","О","П","Р","С","Т","У","Ф","Х","Ц","Ч","Ш","Щ","Ъ","Ы","Ь","Э","Ю","Я"," ")
val en  = listOf("a","b","v","g","d","e","e","zh","z","i","i","k","l","m","n","o","p","r","s","t","u","f","h","c","ch","sh","sh'","","i","","e","yu","ya"," ")


object Utils {
    fun parseFullName(fullName:String?):Pair<String?, String?>{
        val parts : List<String>? = fullName?.split(" ")
        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)
        return Pair(if((firstName == null) || (firstName.length == 0))null else firstName ,
            if((lastName == null) || (lastName.length == 0))null else lastName)
    }

    fun toInitials(firstName: String?, lastName:String?):String?{
        var fn:String? = null
        if(firstName != null)
            if(firstName.replace(" ","").length >0)
                fn  = firstName
        var ln:String? = null
        if(lastName != null)
            if(lastName.replace(" ","").length >0)
                ln  = lastName
        var res:String = ""
        if (fn != null)
            res += fn.substring(0,1).toUpperCase()
        if (ln != null)
            res += ln.substring(0,1).toUpperCase()

        return res
    }

    fun transliteration(payload:String, divider:String = " "):String{
        var res:String = ""
        payload.forEach{
            var r:String = it.toString()
            if(rus.indexOf(r) > -1)
                res += en.get(rus.indexOf(r))
            else
            if(rus_up.indexOf(r.toUpperCase()) > -1)
                res += en.get(rus_up.indexOf(r.toUpperCase())).toUpperCase()
            else
                res += r

        }
        return res.replace(" ", divider)
    }
}