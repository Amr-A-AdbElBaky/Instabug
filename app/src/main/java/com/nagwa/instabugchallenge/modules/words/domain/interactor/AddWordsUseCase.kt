package com.nagwa.instabugchallenge.modules.words.domain.interactor

import com.nagwa.instabugchallenge.base.domain.interactor.UseCase
import com.nagwa.instabugchallenge.modules.words.domain.entity.WordEntity
import com.nagwa.instabugchallenge.modules.words.domain.repository.WordsRepository
import javax.inject.Inject

class AddWordsUseCase @Inject constructor(
    private val wordsRepository: WordsRepository
) : UseCase<List<WordEntity>>() {
    override fun build(params :List<WordEntity>) {
        return wordsRepository.addWords(params)
    }

}