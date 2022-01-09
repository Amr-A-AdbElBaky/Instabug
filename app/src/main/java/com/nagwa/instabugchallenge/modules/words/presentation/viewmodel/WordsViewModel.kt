package com.nagwa.instabugchallenge.modules.words.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nagwa.instabugchallenge.modules.words.domain.entity.WordEntity
import com.nagwa.instabugchallenge.modules.words.domain.interactor.AddWordsUseCase
import com.nagwa.instabugchallenge.modules.words.domain.interactor.GetWordsUseCase
import com.nagwa.instabugchallenge.modules.words.domain.repository.WordsRepository
import javax.inject.Inject

class WordsViewModel @Inject constructor(
    private val getWordsUseCase: GetWordsUseCase,
    private val addWordsUseCase: AddWordsUseCase
) : ViewModel() {
    val wordsSuccessEvent = MutableLiveData<List<WordEntity>>()
    val wordsSearchEvent by lazy { MutableLiveData<List<WordEntity>>() }
    val wordsLoadingEvent = MutableLiveData<Boolean>()
    val sortClickEvent = MutableLiveData<Boolean>()
    val wordsErrorEvent = MutableLiveData<Throwable>()
    fun requestWords() {
        wordsLoadingEvent.value = true
        getWordsUseCase.build(object : WordsRepository.LoadPostsCallback {
            override fun onWordsLoaded(words: List<WordEntity>) {
                wordsSuccessEvent.value = words
                wordsLoadingEvent.value = false
                addWordsUseCase.build(words)
            }

            override fun onError(t: Throwable) {
                wordsLoadingEvent.value = false
                wordsErrorEvent.value = t
            }

        })
    }

    fun requestWords(isSorted: Boolean) {
        if (isSorted)
            wordsSuccessEvent.value = wordsSuccessEvent.value?.sortedByDescending { it.count }
        else
            wordsSuccessEvent.value = wordsSuccessEvent.value?.sortedBy { it.count }
    }

    fun searchStories(query: String) {
        val filtered = wordsSuccessEvent.value?.filter { it.name.contains(query, true) }
        wordsSearchEvent.value = filtered
    }
}