package com.nagwa.instabugchallenge.modules.words.domain.repository

import com.nagwa.instabugchallenge.modules.words.domain.entity.WordEntity


interface WordsRepository {


    interface LoadPostsCallback {
        fun onWordsLoaded(posts: List<WordEntity>)
        fun onError(t: Throwable)
    }

    fun getInstabugWords( callback: LoadPostsCallback)
    fun addWords(words :List<WordEntity>)


}