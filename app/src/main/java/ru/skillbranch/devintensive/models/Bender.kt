package ru.skillbranch.devintensive.models

import android.util.Log

class Bender(var status:Status = Status.NORMAL, var question: Question = Question.NAME) {
    var countAnswer:Int = 0
    fun ascQuestion():String = when(question){
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer:String):Pair<String, Triple<Int, Int, Int>>{
         return if (question.answers.contains(answer.toLowerCase())){
             var iserrorformat:Boolean = false
              Log.d("M_Bender", answer.get(0)+" "+answer.get(0).toUpperCase()+" "+answer)
             when(question){
                 Question.NAME ->
                     if(answer.get(0) != answer.get(0).toUpperCase())
                         iserrorformat = true

                 Question.PROFESSION  ->
                     if(answer[0] != answer[0].toLowerCase())
                         iserrorformat = true

                 Question.MATERIAL  ->
                     if(answer[0] != answer[0].toUpperCase())
                         iserrorformat = true

                 Question.BDAY  ->
                     if(answer.contains(Regex("\\D+")))
                         iserrorformat = true

                 Question.SERIAL  ->
                     if(answer.contains(Regex("\\D+")) || answer.length != 7)
                         iserrorformat = true

             }
             if(iserrorformat){
                 "${question.errorFomat()}\n${question.question}" to status.color
             }else{
                 question = question.nextQuestion()
                 "Отлично - ты справился\n${question.question}" to status.color
             }

        }else{
             countAnswer +=1
             if(countAnswer<=3) {
                 status = status.nextStatus()
                 "Это неправильный ответ\n${question.question}" to status.color
             }else{
                 countAnswer = 0
                 status = Status.NORMAL
                 question = Question.NAME
                 "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
             }
        }
    }

    enum class Status(val color:Triple<Int, Int, Int>){
        NORMAL(Triple(255,255,255)),
        WARNING(Triple(255,120,0)),
        DANGER(Triple(255,60,60)),
        CRITICAL(Triple(255,255,0));

        fun nextStatus():Status{
            return if(this.ordinal < values().lastIndex){
                values()[this.ordinal +1]
            }else{
                 values()[0]
            }
        }
    }

    enum class Question(val question:String, val answers:List<String>){
        NAME("Как меня зовут?", listOf("бендер","bender")) {
            override fun nextQuestion(): Question = PROFESSION
            override fun errorFomat():String = "Имя должно начинаться с заглавной буквы"
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик","bender")){
            override fun nextQuestion(): Question = MATERIAL
            override fun errorFomat():String = "Профессия должна начинаться со строчной буквы"
        },
        MATERIAL("Из чего я сделан?", listOf("металл","дерево","metall","iron","wood")){
            override fun nextQuestion(): Question = BDAY
            override fun errorFomat():String = "Материал не должен содержать цифр"
        },
        BDAY("Когда меня создали?", listOf("2993")){
            override fun nextQuestion(): Question = SERIAL
            override fun errorFomat():String = "Год моего рождения должен содержать только цифры"
        },
        SERIAL("Мой серийный номер?", listOf("2716057")){
            override fun nextQuestion(): Question = IDLE
            override fun errorFomat():String = "Серийный номер содержит только цифры, и их 7"
        },
        IDLE("На этом все, вопросов больше нет", listOf()){
            override fun nextQuestion(): Question = IDLE
            override fun errorFomat():String = ""
        };

        abstract fun nextQuestion():Question
        abstract fun errorFomat():String
    }
}