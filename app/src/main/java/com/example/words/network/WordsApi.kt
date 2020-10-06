/*
Name: Doan Ngoc
Student ID: 1605449
*/

package com.example.words.network

import androidx.lifecycle.LiveData
import com.example.words.Word
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://users.metropolia.fi/~ngocd/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(LiveDataCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface WordsApiService {
    @GET("words.json")
    fun getWords(): LiveData<ApiResponse<List<Word>>>
}

object WordsApi {
    val retrofitService: WordsApiService by lazy {
        retrofit.create(WordsApiService::class.java)
    }
}