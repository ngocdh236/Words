package com.example.words.ui.words

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.words.network.Word
import com.example.words.network.WordsApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WordsViewModel : ViewModel() {

    private val _response = MutableLiveData<List<Word>>()


    val response: LiveData<List<Word>>
        get() = _response

    init {
        getWords()
    }

    private fun getWords() {
        WordsApi.retrofitService.getWords().enqueue(object: Callback<List<Word>> {
            override fun onResponse(call: Call<List<Word>>, response: Response<List<Word>>) {
                _response.value = response.body()
            }

            override fun onFailure(call: Call<List<Word>>, t: Throwable) {
            }
        })

    }
}