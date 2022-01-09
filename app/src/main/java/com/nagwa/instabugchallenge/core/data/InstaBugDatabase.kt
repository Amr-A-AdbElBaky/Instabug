package com.nagwa.instabugchallenge.core.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class InstaBugDatabase  // creating a constructor for our database handler.
    (context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    // below method is for creating a database by running a sqlite query
    override fun onCreate(db: SQLiteDatabase) {

        val query = ("CREATE TABLE " + TABLE_WORDS + " ("
                + NAME_COL + " TEXT PRIMARY KEY,"
                + COUNT_COL + " INTEGER )")

        db.execSQL(query)
    }

    // this method is use to add new course to our sqlite database.

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS $TABLE_WORDS")
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "instabugdb"
        private const val DB_VERSION = 1
        private const val TABLE_WORDS = "words"
        private const val NAME_COL = "name"
        private const val COUNT_COL = "count"
    }
}
