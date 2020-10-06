package com.example.words.other

import com.example.words.models.QuizResult

class QuizResultFeedbackHelper {
    companion object {
        fun feedback(result: QuizResult): String {
            val percentage = (result.correctAnswers.toDouble() / result.numberOfQuizzes.toDouble())

            if (percentage == 1.0) {
                return "You are amazing!"
            } else if (percentage >= 0.5) {
                return "You are good!"
            } else if (percentage >= 0.3) {
                return "You need more practices!"
            } else {
                return "Stop playing video games! Focus and study more!"
            }
        }
    }
}