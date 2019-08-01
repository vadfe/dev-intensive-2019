package ru.skillbranch.devintensive.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import ru.skillbranch.devintensive.R
import java.util.regex.Pattern
import android.R.attr.y
import android.R.attr.x
import android.graphics.Color
import android.graphics.Paint


val rus  = listOf("а","б","в","г","д","е","ё","ж","з","и","й","к","л","м","н","о","п","р","с","т","у","ф","х","ц","ч","ш","щ","ъ","ы","ь","э","ю","я", " ")
val rus_up  = listOf("А","Б","В","Г","Д","Е","Ё","Ж","З","И","Й","К","Л","М","Н","О","П","Р","С","Т","У","Ф","Х","Ц","Ч","Ш","Щ","Ъ","Ы","Ь","Э","Ю","Я"," ")
val en  = listOf("a","b","v","g","d","e","e","zh","z","i","i","k","l","m","n","o","p","r","s","t","u","f","h","c","ch","sh","sh'","","i","","e","yu","ya"," ")
val en_up  = listOf("A","B","V","G","D","E","E","Zh","Z","I","I","K","L","M","N","O","P","R","S","T","U","F","H","C","Ch","Sh","Sh'","","I","","E","Yu","Ya"," ")
val ignored = setOf("enterprise", "features", "topics",
        "collections", "trending", "events", "marketplace", "pricing", "nonprofit",
        "customer-stories", "security", "login", "join")
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
        var res:String? = null
        if (fn != null)
            res = fn.substring(0,1).toUpperCase()
        if (ln != null)
            if(res!= null)
                res += ln.substring(0,1).toUpperCase()
            else
                res = ln.substring(0,1).toUpperCase()

        return res
    }

    fun toNickName(firstName: String?, lastName:String?):String{
        var nickName:String =  transliteration(firstName+" "+lastName,"_")
        if(firstName.isNullOrEmpty())
            nickName = nickName.replace("_","")
        if(lastName.isNullOrEmpty())
            nickName = nickName.replace("_","")

        return nickName
    }

    fun transliteration(payload:String, divider:String = " "):String{
        var res = ""
        payload.forEach{
            val r:String = it.toString()
            if(rus.indexOf(r) > -1)
                res += en.get(rus.indexOf(r))
            else
                if(rus_up.indexOf(r) > -1)
                    res += en_up.get(rus_up.indexOf(r))
                else
                    res += r

        }
        return res.replace(" ", divider)
    }

    fun url_validator(_url:String?):Boolean{
        if(_url.isNullOrEmpty()) return true
        val  exept_word = setOf(
                "enterprise",
                "features",
                "topics",
                "collections",
                "trending",
                "events",
                "marketplace",
                "pricing",
                "nonprofit",
                "customer-stories",
                "security",
                "login",
                "join"
        )
        with(exept_word){
            for(s in this)
                if(_url.contains(s, true))
                    return false
        }
        val lRegex = with(StringBuilder()){
            append("^((https://github.com)?|(https://www.github.com)|(github.com)|(www.github.com))/[a-zA-Z0-9_]{1,}$")
        }.toString()
        val p = Pattern.compile(lRegex)
        val m = p.matcher(_url)
        val res = m.find()
        return res
    }
    fun validateUrl(url: String): Boolean = url.isEmpty() || (
            url.matches("^(https://)?(www.)?github.com/(?=.{1,39}\$)(?![-_])(?!.*[-_]{2})[a-zA-Z0-9-_]+(?<![-])$".toRegex())) &&
            !url.matches( Regex("^.*(/enterprise|/features|/topics|/collections|/trending|/events|/marketplace|/pricing|/nonprofit|/customer-stories|/security|/login|/join\$)",
                    RegexOption.IGNORE_CASE)
            )

    fun isRepositoryValid(repository: String): Boolean {
        val regex = Regex("^(?:https://)?(?:www.)?(?:github.com/)(?!${ignored.joinToString("|")})\\w+$")
        return repository.isEmpty() || regex.matches(repository)
    }
}