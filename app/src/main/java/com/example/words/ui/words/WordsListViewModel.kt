package com.example.words.ui.words

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import Word
import com.example.words.network.WordsApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WordsListViewModel : ViewModel() {
    val words: LiveData<List<Word>>
        get() = this._words

    private val _words = MutableLiveData<List<Word>>()

    fun getWords() {
        WordsApi.retrofitService.getWords().enqueue(object: Callback<List<Word>> {
            override fun onResponse(call: Call<List<Word>>, response: Response<List<Word>>) {
                _words.value = response.body()
            }

            override fun onFailure(call: Call<List<Word>>, t: Throwable) {
            }
        })
    }
}