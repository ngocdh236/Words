package com.example.words.network

import androidx.lifecycle.LiveData
import com.example.words.Word
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://users.metropolia.fi/~ngocd/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

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