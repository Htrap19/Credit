package com.example.myapplication.databasehelpers

import android.content.ContentValues
import android.database.Cursor

class User {
    var id: Int = 0
    var username: String = ""
    var password: String = ""

    constructor(username: String, password: String) {
        this.username = username
        this.password = password
    }

    constructor() {
    }

    companion object Columns {
        const val COL_ID = "id"
        const val COL_USERNAME = "username"
        const val COL_PASSWORD = "password"
    }
}

class UserTbl : Table<User> {
    override val tableName = "user"
    override fun creationQuery(): String {
        return "CREATE TABLE $tableName (" +
                "${User.COL_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${User.COL_USERNAME} VARCHAR(256)," +
                "${User.COL_PASSWORD} VARCHAR(256));"
    }

    override fun contentValues(data: User): ContentValues {
        val cv = ContentValues()
        cv.put(User.COL_USERNAME, data.username)
        cv.put(User.COL_PASSWORD, data.password)
        return cv
    }

    override fun mutableList(cursor: Cursor?): MutableList<User> {
        val list: MutableList<User> = ArrayList()
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val user = User()
                    user.id = cursor.getInt(cursor.getColumnIndex(User.COL_ID))
                    user.username = cursor.getString(cursor.getColumnIndex(User.COL_USERNAME))
                    user.password = cursor.getString(cursor.getColumnIndex(User.COL_PASSWORD))
                    list.add(user)
                } while (cursor.moveToNext())
            }
        }
        return list
    }
}