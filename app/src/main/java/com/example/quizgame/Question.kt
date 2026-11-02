package com.example.quizgame

import androidx.annotation.DrawableRes

/**
 * Data class representing a quiz question.
 *
 * @param questionText The prompt/question displayed to the user.
 * @param imageResId Resource ID of the associated image in res/drawable.
 * @param options List of 3 answer choices (String).
 * @param correctAnswerIndex Index (0, 1, or 2) of the correct answer in options.
 * @param contentDescription Accessibility description for the image.
 */
data class Question(
    val questionText: String,
    @param:DrawableRes val imageResId: Int,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val contentDescription: String,
)