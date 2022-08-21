package com.example.myapplication.databasehelpers

import android.content.ContentValues
import android.database.Cursor
import java.io.Serializable

class Student : Serializable {
    var id: Int = 0
    var name: String = ""
    var gender: Int = -1
    var dateOfBirth: String = ""
    var phoneNumber: Int = 0
    var address: String = ""
    var idNumber: String = ""
    var idType: String = ""

    constructor(name: String, gender: Int,
                dateOfBirth: String,
                phoneNumber: Int,
                address: String,
                idNumber: String,
                idType: String) {
        this.name = name
        this.gender = gender
        this.dateOfBirth = dateOfBirth
        this.phoneNumber = phoneNumber
        this.address = address
        this.idNumber = idNumber
        this.idType = idType
    }

    constructor() {
    }

    companion object Columns {
        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_GENDER = "gender"
        const val COL_DATE_OF_BIRTH = "date_of_birth"
        const val COL_PHONE_NUMBER = "phone_number"
        const val COL_ADDRESS = "address"
        const val COL_ID_NUMBER = "id_number"
        const val COL_ID_TYPE = "id_type"
    }
}

class StudentTbl : Table<Student> {
    override val tableName: String = "students"

    override fun creationQuery(): String {
        return "CREATE TABLE IF NOT EXISTS $tableName (" +
                "${Student.COL_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${Student.COL_NAME} TEXT," +
                "${Student.COL_GENDER} INTEGER," +
                "${Student.COL_DATE_OF_BIRTH} TEXT," +
                "${Student.COL_PHONE_NUMBER} INTEGER," +
                "${Student.COL_ADDRESS} TEXT," +
                "${Student.COL_ID_NUMBER} TEXT," +
                "${Student.COL_ID_TYPE} TEXT);"
    }

    override fun contentValues(data: Student): ContentValues {
        val cv = ContentValues()
        cv.put(Student.COL_NAME, data.name)
        cv.put(Student.COL_GENDER, data.gender)
        cv.put(Student.COL_DATE_OF_BIRTH, data.dateOfBirth)
        cv.put(Student.COL_PHONE_NUMBER, data.phoneNumber)
        cv.put(Student.COL_ADDRESS, data.address)
        cv.put(Student.COL_ID_NUMBER, data.idNumber)
        cv.put(Student.COL_ID_TYPE, data.idType)
        return cv
    }

    override fun mutableList(cursor: Cursor?): MutableList<Student> {
        val list: MutableList<Student> = ArrayList()
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val student = Student()
                    student.id = cursor.getInt(cursor.getColumnIndex(Student.COL_ID))
                    student.name = cursor.getString(cursor.getColumnIndex(Student.COL_NAME))
                    student.gender = cursor.getInt(cursor.getColumnIndex(Student.COL_GENDER))
                    student.dateOfBirth = cursor.getString(cursor.getColumnIndex(Student.COL_DATE_OF_BIRTH))
                    student.phoneNumber = cursor.getInt(cursor.getColumnIndex(Student.COL_PHONE_NUMBER))
                    student.address = cursor.getString(cursor.getColumnIndex(Student.COL_ADDRESS))
                    student.idNumber = cursor.getString(cursor.getColumnIndex(Student.COL_ID_NUMBER))
                    student.idType = cursor.getString(cursor.getColumnIndex(Student.COL_ID_TYPE))
                    list.add(student)
                } while (cursor.moveToNext())
            }
        }
        return list
    }

    override fun deletionQuery(): String {
        return "DROP TABLE IF EXISTS $tableName"
    }

}