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
    return ""
    }
}