package ru.skillbranch.devintensive.extensions

fun String.truncate(length: Int = 16): String {
    val trimmed = this.trimEnd(' ')
    if (trimmed.length > length) {
        var result = trimmed.substring(0, length)
        result += "..."
        return result
    }
    return trimmed
}

fun String.stripHtml(): String {
    var result = this.replace("\\s+".toRegex(), " ")
    result = result.replace("<[^>]*>".toRegex(), "")
    return result
}