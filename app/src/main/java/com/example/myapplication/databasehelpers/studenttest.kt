package com.example.myapplication.databasehelpers

import android.content.ContentValues
import android.database.Cursor

class StudentTest {
    var id: Int = 0
    var name: String = ""
    var phone: Int = 0

    constructor() {
    }

    constructor(name: String, phone: Int) {
        this.name = name
        this.phone = phone
    }

    companion object Columns {
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PHONE = "phone"
    }
}

class StudentTestTbl : Table<StudentTest> {
    override val tableName: String = "StudentTest2"
    override fun creationQuery(): String {
        return "CREATE TABLE $tableName (" +
                "${StudentTest.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${StudentTest.COLUMN_NAME} VARCHAR(256)," +
                "${StudentTest.COLUMN_PHONE} INTEGER);"
    }

    override fun contentValues(data: StudentTest): ContentValues {
        val cv = ContentValues()
        cv.put(StudentTest.COLUMN_NAME, data.name)
        cv.put(StudentTest.COLUMN_PHONE, data.phone)
        return cv
    }

    override fun mutableList(cursor: Cursor?): MutableList<StudentTest> {
        val list: MutableList<StudentTest> = ArrayList()
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val student = StudentTest()
                    student.id = cursor.getInt(cursor.getColumnIndex(StudentTest.COLUMN_ID))
                    student.name = cursor.getString(cursor.getColumnIndex(StudentTest.COLUMN_NAME))
                    student.phone = cursor.getInt(cursor.getColumnIndex(StudentTest.COLUMN_PHONE))
                    list.add(student)
                } while (cursor.moveToNext())
            }
        }
        return list
    }
}