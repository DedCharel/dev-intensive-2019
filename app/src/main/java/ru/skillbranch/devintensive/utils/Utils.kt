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
    return  ""
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