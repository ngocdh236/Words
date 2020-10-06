/*
Name: Doan Ngoc
Student ID: 1605449
*/

package com.example.words.ui.words

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.words.Word
import com.example.words.repository.Resource
import com.example.words.repository.WordsRepositoryImpl

class WordsListViewModel() : ViewModel() {

    private val repository = WordsRepositoryImpl()

    val words: LiveData<Resource<List<Word>>>

    init {
        words = repository.loadWords()
    }
}