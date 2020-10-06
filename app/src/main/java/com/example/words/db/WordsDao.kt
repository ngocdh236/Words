/*
Name: Doan Ngoc
Student ID: 1605449
*/

package com.example.words.db


import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.words.Word


// @Dao
interface WordsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: Word)
}
