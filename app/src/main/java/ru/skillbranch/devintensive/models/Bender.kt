package ru.skillbranch.devintensive.models

import androidx.core.text.isDigitsOnly

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME, var attempts: Int = 0) {

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listerAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        val validationResult = validateAnswer(answer)
        if (!validationResult.isNullOrEmpty()) {
            return "$validationResult" +
                    "${question.question}" to status.color
        }
        return when {
            question.answers.contains(answer?.toLowerCase()) -> {
                question = question.nextQuestion()
                attempts = 0
                "Отлично - ты справился\n${question.question}" to status.color
            }
            attempts < 3 -> {
                attempts++
                status = status.nextStatus()
                "Это неправильный ответ\n${question.question}" to status.color
            }
            else -> {
                attempts = 0
                status = Status.NORMAL
                question = Question.NAME
                "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            }
        }
    }

    private fun validateAnswer(answer: String): String {
        if (answer.isNullOrEmpty()) return ""
        return when (question) {
            Question.NAME -> if (!answer[0]?.isUpperCase()) "Имя должно начинаться с заглавной буквы\n" else ""
            Question.PROFESSION -> if (!answer[0]?.isLowerCase()) "Профессия должна начинаться со строчной буквы\n" else ""
            Question.MATERIAL -> if (answer.matches(".*\\d.*".toRegex())) "Материал не должен содержать цифр\n" else ""
            Question.BDAY -> if (!answer.isDigitsOnly()) "Год моего рождения должен содержать только цифры\n" else ""
            Question.SERIAL -> if (!answer.isDigitsOnly() || answer.length != 7) "Серийный номер содержит только цифры, и их 7\n" else ""
            Question.IDLE -> ""
        }
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]

            }
        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion(): Question
    }
}