package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName:String?):Pair<String?, String?>{
        return if (!fullName?.trim().isNullOrEmpty()){
            val parts: List<String>? =fullName?.trim()!!.split(" ")

            val firstName = parts?.getOrNull(0)
            val lastName = parts?.getOrNull(1)
            firstName to lastName
        } else {
            null to null
        }
    }

    fun transliteration(payload: String, divider:String = " "): String {
        var result:String =""
        if (!payload.trim().isNullOrEmpty()){
            val parts: List<String> = payload.trim().split(" ")
            var counterWords = 0
            parts.forEach {
                if (counterWords>0) {
                result+=divider
                }
                for (i in it.indices){
                    if (it[i].toUpperCase() == it[i]){
                        var str:String =translitChar(it[i].toLowerCase())

                        result +=str.replaceRange(0..0,str[0].toUpperCase().toString())
                    }else{

                    result +=translitChar(it[i])
                    }
                }
                counterWords++

            }
        }
    return  result
    }

    fun translitChar(char: Char):String{
        var result:String = ""
        result = when(char){
            'а' -> "a"
            'б' -> "b"
            'в' -> "v"
            'г' -> "g"
            'д' -> "d"
            'е' -> "e"
            'ё' -> "e"
            'ж' -> "zh"
            'з' -> "z"
            'и' -> "i"
            'й' -> "i"
            'к' -> "k"
            'л' -> "l"
            'м' -> "m"
            'н' -> "n"
            'о' -> "o"
            'п' -> "p"
            'р' -> "r"
            'с' -> "s"
            'т' -> "t"
            'у' -> "u"
            'ф' -> "f"
            'х' -> "h"
            'ц' -> "c"
            'ч' -> "ch"
            'ш' -> "sh"
            'щ' -> "sh'"
            'ъ' -> ""
            'ы' -> "i"
            'ь' -> ""
            'э' -> "e"
            'ю' -> "yu"
            'я' -> "ya"
            else ->char.toString()
        }
        return result
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        var result:String? = null
        if((!firstName?.trim().isNullOrEmpty()) or (!lastName?.trim().isNullOrEmpty())) {
           if  (!firstName?.trim().isNullOrEmpty()) {
               result = firstName?.trim()?.substring(0,1)?.toUpperCase()
           }
            if (!lastName?.trim().isNullOrEmpty()) {
                result += lastName?.trim()?.substring(0,1)?.toUpperCase()
            }
        }
        return result
    }

}