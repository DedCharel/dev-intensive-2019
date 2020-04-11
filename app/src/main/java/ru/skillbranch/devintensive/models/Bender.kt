package ru.skillbranch.devintensive.models

import android.util.Log

class Bender (var status: Status = Status.NORMAL, var question: Question = Question.NAME){
    var answerCount = 0

    fun askQuestion() = when(question){
        Question.NAME ->Question.NAME.question
        Question.PROFESSION ->Question.PROFESSION.question
        Question.MATERIAL ->Question.MATERIAL.question
        Question.BDAY ->Question.BDAY.question
        Question.SERIAL ->Question.SERIAL.question
        Question.IDLE ->Question.IDLE.question
    }

    fun listenAnswer(answer:String):Pair<String, Triple<Int, Int, Int>>{
         when(question) {
            Question.IDLE -> return  question.question to status.color
            else ->{
              val  (isValid, resultValid) = checkValidation(question, answer)
              return if (isValid){
               if (question.answer.contains(resultValid)){
                   question = question.nextQuestion()
                   "Отлично - ты справился\n${question.question}" to status.color
               }else{
                   answerCount++
                   if (answerCount > 3){
                       answerCount = 0
                       status = Status.NORMAL
                       question = Question.NAME
                       "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color

                   } else {
                   status = status.nextStatus()
                   "Это неправильный ответ\n${question.question}" to status.color
                   }
               }
              } else {resultValid+"\n${question.question}" to status.color}
            }
         }
    }

    private fun checkValidation(question: Question, text: String):Pair<Boolean,String>{
        Log.d("M_Bender","$question")
        if (text.isNotEmpty()){
       return when(question){
            Question.NAME ->{
                 if ((text.isNotEmpty()) and (text[0].isUpperCase())) {
                    true to text.toLowerCase()
                }else {
                    false to "Имя должно начинаться с заглавной буквы"
                }
            }
            Question.PROFESSION -> {
                if ((text.isNotEmpty()) and (text[0].isLowerCase())) {
                    true to text.toLowerCase()
                } else {
                    false to "Профессия должна начинаться со строчной буквы"
                }
            }
           Question.MATERIAL ->{
               val regex = Regex("\\d+")
               if (regex.findAll(text).none()) {
                   true to text.toLowerCase()
               }else {
                   false to "Материал не должен содержать цифр"
               }
           }
           Question.BDAY ->{
               val regex = Regex("\\D+")
               if (regex.findAll(text).none()) {
                   true to text.toLowerCase()
               }else {
                   false to "Год моего рождения должен содержать только цифры"
               }
           }
           Question.SERIAL ->{
               val regex = Regex("\\D+")
                if ((regex.findAll(text).none()) and (text.length == 7)){
                   true to text.toLowerCase()
               }else {
                   false to "Серийный номер содержит только цифры, и их 7"
               }
           }
           Question.IDLE -> {
               true to text.toLowerCase()
           }
        }
        }else return true to text
    }



    enum class Status(val color: Triple<Int,Int,Int>){
        NORMAL(Triple(255,255,255)),
        WARNING(Triple(255,120,0)),
        DANGER(Triple(255,60,60)),
        CRITICAL(Triple(255,0,0));

        fun nextStatus():Status{
            return if(this.ordinal < values().lastIndex){
                values()[this.ordinal + 1]
            }else{
                values()[0]
            }

        }
    }

    enum class Question(val question: String, val answer:List<String>){
        NAME("Как меня зовут?", listOf("бендер","bender")) {
            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик","bender")){
            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл","дерево", "metal", "iron", "wood")){
            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")){
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")){
            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()){
            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion():Question
    }
}