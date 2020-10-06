/*
Name: Doan Ngoc
Student ID: 1605449
*/

package com.example.words.ui.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Welcome to Word Quiz"
    }

    val text: LiveData<String> = _text

    val buttonTitle: String = "Start"
}