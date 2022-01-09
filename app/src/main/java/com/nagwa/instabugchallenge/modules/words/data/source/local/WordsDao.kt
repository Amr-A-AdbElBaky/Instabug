package com.nagwa.instabugchallenge.modules.words.data.source.local

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.nagwa.instabugchallenge.core.data.InstaBugDatabase
import com.nagwa.instabugchallenge.modules.words.data.source.local.model.WordDto
import javax.inject.Inject

class WordsDao @Inject constructor(private val db: InstaBugDatabase) {

    fun addWords(words: List<WordDto>): Boolean {

        words.forEach {
            val values = ContentValues().apply {
                put(NAME_COL, it.name)
                put(COUNT_COL, it.count)
            }

            with(db.writableDatabase) {
                insertWithOnConflict(
                    TABLE_WORDS, null, values,
                    SQLiteDatabase.CONFLICT_REPLACE
                )

                    close()
            }
        }
        return true
    }

    fun getAllWords(): List<WordDto> {
        val wordsList: MutableList<WordDto> = mutableListOf()

        var selectQuery = "SELECT  * FROM $TABLE_WORDS"

        val cursor: Cursor = db.writableDatabase.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val wordDto = WordDto(
                    name = cursor.getString(0),
                    count = cursor.getInt(1),
                )
                wordsList.add(wordDto)
            } while (cursor.moveToNext())
        }

        return wordsList
    }


    companion object {
        private const val TABLE_WORDS = "words"
        private const val NAME_COL = "name"
        private const val COUNT_COL = "count"
    }

}