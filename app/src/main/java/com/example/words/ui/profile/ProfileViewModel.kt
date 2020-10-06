package com.example.words.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.words.db.QuizResultDao
import com.example.words.models.QuizResult

class ProfileViewModel : ViewModel() {

    // This database is messy... (context can't be passed into viewModel because of memory leak and this messes thing up)
    var quizResultDao: QuizResultDao? = null
        set(dao) {
            dao?.let {
                quizResults = dao.getAllQuizResults()
            }
        }

    private var quizResults: LiveData<List<QuizResult>>? = null

    // NOTE: Make sure observe this text after setting quizResultDao
    val text: LiveData<String>
        get() {
            return quizResults?.let {
                Transformations.map(it) { result ->
                    if (result.size > 0) {
                        val totalCorrectAnswers =
                            result.map { it.correctAnswers }.reduce { sum, element -> sum + element }
                        val totalQuizzes =
                            result.map { it.numberOfQuizzes }.reduce { sum, element -> sum + element }
                        val percentage = totalCorrectAnswers / totalQuizzes
                        return@map "Your correct answer percentage: ${percentage}"
                    } else {
                        return@map "You have not done any quiz!!!"
                    }
                }
            } ?: MutableLiveData<String>().apply {
                value = "You don't have any result yet!"
            }
        }
}