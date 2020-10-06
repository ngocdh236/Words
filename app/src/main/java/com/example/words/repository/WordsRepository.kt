/*
Name: Doan Ngoc
Student ID: 1605449
*/


package com.example.words.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.words.Word
import com.example.words.db.WordsDao
import com.example.words.network.*

interface WordsRepository {
    fun loadWords(): LiveData<Resource<List<Word>>>
}

class WordsRepositoryImpl(
    private val wordsApiService: WordsApiService = WordsApi.retrofitService,
    wordsDao: WordsDao? = null
) : WordsRepository {
    override fun loadWords(): LiveData<Resource<List<Word>>> {
        return object : NetworkBoundResource<List<Word>, List<Word>>() {
            override fun saveCallResult(item: List<Word>) {
                // Do nothing for now
            }

            override fun shouldFetch(data: List<Word>?): Boolean {
                // Fetch all for now
                return true
            }

            override fun loadFromDb(): LiveData<List<Word>> {
                return MutableLiveData<List<Word>>(listOf<Word>())
            }

            override fun createCall(): LiveData<ApiResponse<List<Word>>> {
                return wordsApiService.getWords()
            }
        }.asLiveData()
    }
}
