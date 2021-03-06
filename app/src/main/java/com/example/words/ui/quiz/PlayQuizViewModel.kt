/*
Name: Doan Ngoc
Student ID: 1605449
*/

package com.example.words.ui.quiz

import android.os.CountDownTimer
import androidx.lifecycle.*
import com.example.words.Word
import com.example.words.combineWith
import com.example.words.db.QuizResultDao
import com.example.words.models.QuizResult
import com.example.words.repository.Resource
import com.example.words.repository.WordsRepository
import com.example.words.repository.WordsRepositoryImpl

// TODO: Rename to PlayQuizViewModel
class QuizViewViewModel(private val wordsRepository: WordsRepository = WordsRepositoryImpl()) :
    ViewModel() {

    private val ONE_SECOND = 1000L

    private val QUIZ_TIME_OUT = 10L * ONE_SECOND

    private val DEFAULT_NUMBER_OF_QUIZZES = 5

    private var numberOfQuizzes = MutableLiveData<Int>().apply { value = 0 }

    private val _currentQuizIndex = MutableLiveData<Int>().apply { value = 0 }

    private val _currentQuizAnswer = MutableLiveData<String>()

    private var words = MutableLiveData<List<Word>>()

    private var result: Array<Boolean> = arrayOf()

    private var timer: CountDownTimer? = null

    private val _isQuizzesDone = MutableLiveData<Boolean>().apply { value = false }

    var quizResultDao: QuizResultDao? = null

    var resource: LiveData<Resource<List<Word>>>

    val currentQuizIndex: LiveData<Int> = _currentQuizIndex

    val currentQuizIndexTitle: LiveData<String> =
        _currentQuizIndex.combineWith(numberOfQuizzes) { currentIndex, numberOfQuizzes ->
            "${currentIndex!! + 1}/${numberOfQuizzes}"
        }

    val isLastQuestion: Boolean
        get() = _currentQuizIndex.value!! >= numberOfQuizzes.value!! - 1

    val currentQuiz: LiveData<Word?> = _currentQuizIndex.combineWith(words) { currentIndex, words ->
        val index = currentIndex ?: 0
        val size = words?.size ?: 0
        if (index >= size) {
            return@combineWith null
        }

        return@combineWith words?.get(index)
    }

    val currentQuizTitle: LiveData<String?> = Transformations.map(currentQuiz) { quiz ->
        return@map quiz?.translationsSet?.first()?.let {
            "What is ${quiz.text.toUpperCase()} (${quiz.lang.capitalize()}) in ${it.lang.capitalize()}?"
        }
    }

    val currentQuizEditDistance: LiveData<Int?> =
        currentQuiz.combineWith(_currentQuizAnswer) { quiz, answer ->
            if (answer == null || quiz == null) {
                return@combineWith null
            }
            if (answer.length == 0) {
                return@combineWith null
            }

            // Always take first translation for the quiz to simplify things
            return@combineWith quiz.translationsSet.first()
                .editDistance(Word(lang = "", text = answer))
        }

    val currentEditDistanceText: LiveData<String?> =
        Transformations.map(currentQuizEditDistance) { editDistance ->
            editDistance?.let {
                if (editDistance == 0) {
                    return@let "Correct"
                }
                return@let "You have ${editDistance} incorrect letters"
            } ?: null
        }

    val currentCountDownTimer = MutableLiveData<Int>().apply { value = QUIZ_TIME_OUT.toInt() }

    val currentCountDownTimerText: LiveData<String> =
        Transformations.map(currentCountDownTimer) { "${it} seconds left" }

    val isQuizzesDone: LiveData<Boolean> = _isQuizzesDone

    init {
        resource = wordsRepository.loadWords()
        resource.observeForever(Observer {
            it.data?.let {
                val quizzesCount = kotlin.math.min(DEFAULT_NUMBER_OF_QUIZZES, it.size)
                numberOfQuizzes.value = quizzesCount
                _currentQuizIndex.value = kotlin.math.min(0, quizzesCount)
                words.value = it.shuffled().take(quizzesCount)
                result = Array(quizzesCount) { false }
                if (quizzesCount > 0) {
                    resetTimer()
                    _isQuizzesDone.value = false
                } else {
                    _isQuizzesDone.value = true
                }
            }
        })
    }

    fun goNext() {
        if (isQuizzesDone.value ?: false) {
            return
        }

        updateCurrentQuizResult()
        _currentQuizAnswer.value = null
        if (isLastQuestion) {
            _isQuizzesDone.value = true
            currentCountDownTimer.value = 0
            timer?.cancel()
        } else {
            resetTimer()
            _currentQuizIndex.value = _currentQuizIndex.value!! + 1
        }
    }

    fun setUserAnswer(answer: String?) {
        _currentQuizAnswer.value = answer
    }

    fun finishQuizzes(completion: (QuizResult) -> Unit) {

        val numberOfQuizzes = result.size
        val correctAnswers = result
            .filter { it }
            .size
        val result = QuizResult(numberOfQuizzes = numberOfQuizzes, correctAnswers = correctAnswers)
        quizResultDao?.insert(result)
        completion(result)
    }

    // Update result after each quiz
    private fun updateCurrentQuizResult() {
        val currentQUizdistance = currentQuizEditDistance.value
        val value = currentQuizEditDistance.value == 0
        result[currentQuizIndex.value!!] = (currentQuizEditDistance.value ?: 1) == 0
    }

    private fun resetTimer() {
        timer?.cancel()
        timer = object : CountDownTimer(QUIZ_TIME_OUT, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                currentCountDownTimer.value = (millisUntilFinished / ONE_SECOND).toInt()
            }

            override fun onFinish() {}
        }
        timer?.start()
    }

}