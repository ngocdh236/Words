/*
Name: Doan Ngoc
Student ID: 1605449
*/

package com.example.words

import com.example.words.models.QuizResult


import com.example.words.other.QuizResultFeedbackHelper
import org.junit.Assert.*
import org.junit.Test

class QuizResultFeedbackHelperTest {
    @Test
    fun feedbackLevel1() {
        val result = QuizResult(1, 5, 5)
        assertEquals("You are amazing!", QuizResultFeedbackHelper.feedback(result))
    }

    @Test
    fun feedbackLevel2() {
        val result = QuizResult(1, 5, 4)
        assertEquals("You are good!", QuizResultFeedbackHelper.feedback(result))
    }

    @Test
    fun feedbackLevel3() {
        val result = QuizResult(1, 5, 2)
        assertEquals("You need more practices!", QuizResultFeedbackHelper.feedback(result))
    }

    @Test
    fun feedbackLevel4() {
        val result = QuizResult(1, 5, 1)
        assertEquals(
            "Stop playing video games! Focus and study more!",
            QuizResultFeedbackHelper.feedback(result)
        )
    }
}