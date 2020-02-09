package ru.skillbranch.devintensive.utils

import kotlin.math.abs

object Utils {
    private val lettersDictionary = mapOf(
            'а' to "a",
            'б' to "b",
            'в' to "v",
            'г' to "g",
            'д' to "d",
            'е' to "e",
            'ё' to "e",
            'ж' to "zh",
            'з' to "z",
            'и' to "i",
            'й' to "i",
            'к' to "k",
            'л' to "l",
            'м' to "m",
            'н' to "n",
            'о' to "o",
            'п' to "p",
            'р' to "r",
            'с' to "s",
            'т' to "t",
            'у' to "u",
            'ф' to "f",
            'х' to "h",
            'ц' to "c",
            'ч' to "ch",
            'ш' to "sh",
            'щ' to "sh'",
            'ъ' to "",
            'ы' to "i",
            'ь' to "",
            'э' to "e",
            'ю' to "yu",
            'я' to "ya")

    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>? = fullName?.split(" ")
        var firstName = parts?.getOrNull(0)
        var lastName = parts?.getOrNull(1)
        if (firstName.isNullOrBlank()) firstName = null
        if (lastName.isNullOrBlank()) lastName = null
        return firstName to lastName
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val first = if (firstName.isNullOrBlank()) null else firstName[0].toUpperCase()
        val last = if (lastName.isNullOrBlank()) null else lastName[0].toUpperCase()

        var result: String? = (first?.toString() ?: "") + (last?.toString() ?: "")
        return if (result.isNullOrBlank()) null else result
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val parts = payload.split(' ')
        val translatedParts = mutableListOf<String>()
        parts.forEach {
            var part = ""
            for (i in it) {
                val isUpper = i.isUpperCase()
                val letter = lettersDictionary[i.toLowerCase()] ?: i.toString()
                part += if (isUpper) letter.toUpperCase() else letter
            }
            translatedParts.add(part)
        }
        return translatedParts.joinToString(divider)
    }

    fun getLastDigit(value: Int): Int {
        return abs(value) % 10
    }


}