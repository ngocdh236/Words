/*
Name: Doan Ngoc
Student ID: 1605449
*/

package com.example.words.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.words.models.QuizResult
import androidx.room.Query

@Dao
interface QuizResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(result: QuizResult)

    @Query("SELECT * from quiz_result")
    fun getAllQuizResults(): LiveData<List<QuizResult>>
}