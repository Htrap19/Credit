package com.example.myapplication.databasehelpers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DATABASE_NAME = "CreditDB"

interface Table<T> {
    val tableName: String
    fun creationQuery(): String
    fun contentValues(data: T): ContentValues
    fun mutableList(cursor: Cursor?): MutableList<T>
}

class DatabaseHelper<T>(val context: Context, val table: Table<T>) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(table.creationQuery())
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun selectAllQuery(): String {
        return "SELECT * FROM " + table.tableName
    }

    fun insertData(data: T): Boolean {
        val db = this.writableDatabase
        val result = db.insert(table.tableName, null, table.contentValues(data))
        db.close()
        return (result != (-1).toLong())
    }

    fun readData(): MutableList<T> {
        val db = this.readableDatabase
        val result = db.rawQuery(this.selectAllQuery(), null)
        val list: MutableList<T> = table.mutableList(result)
        result.close()
        db.close()
        return list
    }

    fun deleteData(whereClause: String?, whereArray: Array<String>?) : Int {
        val db = this.readableDatabase
        val rowsAffected = db.delete(table.tableName, whereClause, whereArray)
        db.close()
        return rowsAffected
    }

    fun deleteAll(): Boolean {
        val affected = deleteData(null, null)
        return (affected == readData().size)
    }

}