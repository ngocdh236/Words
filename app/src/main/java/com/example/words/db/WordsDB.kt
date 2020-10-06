/*
Name: Doan Ngoc
Student ID: 1605449
*/

package com.example.words.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.words.models.QuizResult

@Database(
    entities = [QuizResult::class],
    version = 3,
    exportSchema = false
)

abstract class WordsDB : RoomDatabase() {
    abstract fun quizResultDao(): QuizResultDao

    companion object {

        @Volatile
        private var INSTANCE: WordsDB? = null

        fun getInstance(context: Context): WordsDB {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WordsDB::class.java,
                        "words_db"
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }

        }
    }
}
