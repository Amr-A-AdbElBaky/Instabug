package com.nagwa.instabugchallenge.modules.words.data.source.remote

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface WordsApi {

    @GET()
    fun getInstabugContent(
        @Url url :String
    ):Call<ResponseBody>
}