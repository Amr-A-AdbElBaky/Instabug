package com.nagwa.instabugchallenge.modules.words.data.repository


import com.nagwa.instabugchallenge.modules.words.data.source.local.WordsDao
import com.nagwa.instabugchallenge.modules.words.data.source.local.model.mapper.toDto
import com.nagwa.instabugchallenge.modules.words.data.source.local.model.mapper.toEntity
import com.nagwa.instabugchallenge.modules.words.data.source.remote.WordsDs
import com.nagwa.instabugchallenge.modules.words.domain.entity.WordEntity
import com.nagwa.instabugchallenge.modules.words.domain.exceptions.NoInternetError
import com.nagwa.instabugchallenge.modules.words.domain.exceptions.SomethingWrongError
import com.nagwa.instabugchallenge.modules.words.domain.repository.WordsRepository
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class WordsRepositoryImpl @Inject constructor(
    private val wordsDs: WordsDs,
    private val wordsDao: WordsDao

) : WordsRepository {
    override fun getInstabugWords(callback: WordsRepository.LoadPostsCallback) {
        wordsDs.getWords().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful)
                    callback.onWordsLoaded(getCurrentContent(response))
                else
                    callback.onError(SomethingWrongError)

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                if (t is IOException) {
                    val localWords = getWords()
                    if (localWords.isEmpty())
                        callback.onError(NoInternetError)
                    else
                        callback.onWordsLoaded(getWords())
                } else
                    callback.onError(SomethingWrongError)
            }

        })
    }


    override fun addWords(words: List<WordEntity>) {
        wordsDao.addWords(words.map { it.toDto() })
    }

    private fun getWords(): List<WordEntity> {
        return wordsDao.getAllWords().map { it.toEntity() }
    }

    private fun getCurrentContent(response: Response<ResponseBody>): List<WordEntity> {
        val currentMap = mutableMapOf<String, Int>()

        val myString = Jsoup.parse(response.body()?.string()).body().text()
        myString.split(" ").forEach {
            if (it.isNotBlank())
                currentMap[it.replace(Regex("[^a-zA-Z]"), "")] = currentMap[it]?.plus(1) ?: 1
        }
        return currentMap.toSortedMap().map {
            WordEntity(name = it.key, count = it.value)
        }
    }

}