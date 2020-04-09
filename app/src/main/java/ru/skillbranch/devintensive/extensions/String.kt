package ru.skillbranch.devintensive.extensions

fun String.truncate(countChar: Int = 16):String{
    var result = this
    if (this.trim().length >= countChar) {
        result = this.substring(0, countChar).trimEnd() +  "..."

    }
    return result

}

fun String.stripHtml(): String {
    return replace(Regex(pattern = "(<.+?>)|(&((\\w+)|(#\\w+));)"), "").replace(Regex(pattern = " {2,}"), " ")
}
