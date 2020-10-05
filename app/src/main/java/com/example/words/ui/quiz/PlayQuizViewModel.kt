package com.example.words.ui.quiz

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.lang.Integer.min

class QuizViewViewModel : ViewModel() {

    private val numberOfQuizzes = 5

    private val _currentQuizIndex = MutableLiveData<Int>().apply { value = 1 }

    val currentQuizIndex: LiveData<Int> = _currentQuizIndex

    val currentQuizTitle: LiveData<String> = Transformations.map(_currentQuizIndex) { currentIndex ->
        "${currentIndex}/${numberOfQuizzes}"
    }

    val isLastQuestion: Boolean
        get() = _currentQuizIndex.value!! == numberOfQuizzes

    fun goNext() {

        _currentQuizIndex.value = _currentQuizIndex.value!! + 1
    }

}