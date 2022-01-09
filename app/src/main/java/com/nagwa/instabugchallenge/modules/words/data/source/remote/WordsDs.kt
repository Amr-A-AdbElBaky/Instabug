package com.nagwa.instabugchallenge.modules.words.data.source.remote


import com.nagwa.instabugchallenge.BuildConfig.BASE_URL
import okhttp3.ResponseBody
import retrofit2.Call
import javax.inject.Inject


class WordsDs @Inject constructor(
    private val api: WordsApi
) {

    fun getWords(): Call<ResponseBody> {
         return  api.getInstabugContent(BASE_URL)
    }

}