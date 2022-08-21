package com.example.myapplication.databasehelpers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

const val DATABASE_NAME = "CreditDB"

interface Table<T> {
    val tableName: String
    fun creationQuery(): String
    fun deletionQuery(): String
    fun contentValues(data: T): ContentValues
    fun mutableList(cursor: Cursor?): MutableList<T>
}

class DatabaseHelper<T>(val context: Context, val table: Table<T>) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    companion object {
        private var initialized = false
    }

    override fun onCreate(db: SQLiteDatabase?) {
        if (!initialized) {
            db?.execSQL(UserTbl().creationQuery())
            db?.execSQL(StudentTbl().creationQuery())
            db?.execSQL(GuardianTbl().creationQuery())
            db?.execSQL(CaseStudyTbl().creationQuery())
            db?.execSQL(BankProcedureTbl().creationQuery())
            db?.execSQL(RepaymentsTbl().creationQuery())
            db?.execSQL(AccommodationTbl().creationQuery())
            db?.execSQL(AccommodationFeesTbl().creationQuery())
            db?.execSQL(PermissionsTbl().creationQuery())
            db?.execSQL(WarrentyTbl().creationQuery())
            initialized = true
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(table.deletionQuery())
        this.onCreate(db)
    }

    private fun selectAllQuery(): String {
        return "SELECT * FROM " + table.tableName
    }

    fun insertData(data: T): Boolean {
        val db = this.writableDatabase
        val result = db.insert(table.tableName, null, table.contentValues(data))
        db.close()
        return (result != (-1).toLong())
    }

    private fun readDataRow(query: String): Pair<SQLiteDatabase, Cursor> {
        val db = this.readableDatabase
        return Pair(db, db.rawQuery(query, null))
    }

    fun readData(): MutableList<T> {
        val result = readDataRow(this.selectAllQuery())
        val list: MutableList<T> = table.mutableList(result.second)
        result.second.close()
        result.first.close()
        return list
    }

    fun readData(whereClause: String): MutableList<T> {
        val result = readDataRow(this.selectAllQuery() + " WHERE " + whereClause)
        val list: MutableList<T> = table.mutableList(result.second)
        result.second.close()
        result.first.close()
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