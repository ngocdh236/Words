/*
Name: Doan Ngoc
Student ID: 1605449
*/

package com.example.words.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_result")
data class QuizResult(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val numberOfQuizzes: Int,
    val correctAnswers: Int
)