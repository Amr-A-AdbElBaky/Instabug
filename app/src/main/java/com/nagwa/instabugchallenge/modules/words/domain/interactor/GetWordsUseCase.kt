package com.nagwa.instabugchallenge.modules.words.domain.interactor

import com.nagwa.instabugchallenge.base.domain.interactor.UseCase
import com.nagwa.instabugchallenge.modules.words.domain.repository.WordsRepository
import javax.inject.Inject

class GetWordsUseCase @Inject constructor(
    private val wordsRepository: WordsRepository
) : UseCase<WordsRepository.LoadPostsCallback>() {
    override fun build(params: WordsRepository.LoadPostsCallback) {
        return wordsRepository.getInstabugWords(params)
    }

}