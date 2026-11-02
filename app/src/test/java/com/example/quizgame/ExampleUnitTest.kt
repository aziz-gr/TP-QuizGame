package com.example.quizgame

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class QuizQuestionUnitTest {

    @Test
    fun question_structureIsCorrect() {
        val question = Question(
            questionText = "Quelle est cette forme ?",
            imageResId = R.drawable.ic_shape_triangle,
            options = listOf("Triangle", "Carré", "Cercle"),
            correctAnswerIndex = 0,
            contentDescription = "Un triangle"
        )

        assertEquals("Quelle est cette forme ?", question.questionText)
        assertEquals(3, question.options.size)
        assertEquals(0, question.correctAnswerIndex)
        assertEquals("Triangle", question.options[question.correctAnswerIndex])
        assertNotNull(question.contentDescription)
    }
}