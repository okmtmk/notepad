package jp.okmt.notepad.store.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MemoDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "memo.db", null, 1) {

    companion object {
        const val TABLE_NAME = "memos"
        const val ID = "_id"
        const val TITLE = "title"
        const val FILEPATH = "filepath"
        const val CREATED_AT = "created_at"
        const val UPDATED_AT = "updated_at"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table $TABLE_NAME ($ID integer primary key, $TITLE text not null, $FILEPATH text not null unique, $CREATED_AT text not null, $UPDATED_AT text not null);")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //To change body of created functions use File | Settings | File Templates.
    }
}