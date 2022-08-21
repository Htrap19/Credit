package com.example.myapplication.databasehelpers

import android.content.ContentValues
import android.database.Cursor
import java.io.Serializable

class Permissions : Serializable {
    var id: Int = 0
    var accommodationId: Int = -1
    var yearHistory: String = ""
    var returnDate: String = ""
    var numberOfDays: Int = 0
    var visitingToName: String = ""
    var visitingToAddress: String = ""
    var visitingToPhoneNumber: String = ""

    constructor() {}

    constructor(yearHistory: String,
                returnDate: String,
                numberOfDays: Int,
                visitingToName: String,
                visitingToAddress: String,
                visitingToPhoneNumber: String) {
        this.yearHistory = yearHistory
        this.returnDate = returnDate
        this.numberOfDays = numberOfDays
        this.visitingToName = visitingToName
        this.visitingToAddress = visitingToAddress
        this.visitingToPhoneNumber = visitingToPhoneNumber
    }

    companion object {
        const val COL_ID = "id"
        const val COL_ACCOMMODATION_ID = "accommodation_id"
        const val COL_YEAR_HISTORY = "year_history"
        const val COL_RETURN_DATE = "return_date"
        const val COL_NUMBER_OF_DAYS = "number_of_days"
        const val COL_VISITING_TO_NAME = "visiting_to_name"
        const val COL_VISITING_TO_ADDRESS = "visiting_to_address"
        const val COL_VISITING_TO_PHONE_NUMBER = "visiting_to_phone_number"
    }
}

class PermissionsTbl : Table<Permissions> {
    override val tableName = "permissions"
    override fun creationQuery(): String {
        return "CREATE TABLE IF NOT EXISTS $tableName (" +
                "${Permissions.COL_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${Permissions.COL_ACCOMMODATION_ID} INTEGER," +
                "${Permissions.COL_YEAR_HISTORY} VARCHAR(256)," +
                "${Permissions.COL_RETURN_DATE} TEXT," +
                "${Permissions.COL_NUMBER_OF_DAYS} INTEGER," +
                "${Permissions.COL_VISITING_TO_NAME} VARCHAR(256)," +
                "${Permissions.COL_VISITING_TO_ADDRESS} VARCHAR(256)," +
                "${Permissions.COL_VISITING_TO_PHONE_NUMBER} VARCHAR(10));"
    }

    override fun deletionQuery(): String {
        return "DROP TABLE IF EXISTS $tableName"
    }

    override fun contentValues(data: Permissions): ContentValues {
        val cv = ContentValues()
        cv.put(Permissions.COL_ACCOMMODATION_ID,         data.accommodationId)
        cv.put(Permissions.COL_YEAR_HISTORY,             data.yearHistory)
        cv.put(Permissions.COL_RETURN_DATE,              data.returnDate)
        cv.put(Permissions.COL_NUMBER_OF_DAYS,           data.numberOfDays)
        cv.put(Permissions.COL_VISITING_TO_NAME,         data.visitingToName)
        cv.put(Permissions.COL_VISITING_TO_ADDRESS,      data.visitingToAddress)
        cv.put(Permissions.COL_VISITING_TO_PHONE_NUMBER, data.visitingToPhoneNumber)
        return cv
    }

    override fun mutableList(cursor: Cursor?): MutableList<Permissions> {
        val list: MutableList<Permissions> = ArrayList()
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val permissions = Permissions()
                    permissions.id = cursor.getInt(cursor.getColumnIndex                        (Permissions.COL_ID))
                    permissions.accommodationId = cursor.getInt(cursor.getColumnIndex           (Permissions.COL_ACCOMMODATION_ID))
                    permissions.yearHistory = cursor.getString(cursor.getColumnIndex            (Permissions.COL_YEAR_HISTORY))
                    permissions.returnDate = cursor.getString(cursor.getColumnIndex             (Permissions.COL_RETURN_DATE))
                    permissions.numberOfDays = cursor.getInt(cursor.getColumnIndex              (Permissions.COL_NUMBER_OF_DAYS))
                    permissions.visitingToName = cursor.getString(cursor.getColumnIndex         (Permissions.COL_VISITING_TO_NAME))
                    permissions.visitingToAddress = cursor.getString(cursor.getColumnIndex      (Permissions.COL_VISITING_TO_ADDRESS))
                    permissions.visitingToPhoneNumber = cursor.getString(cursor.getColumnIndex  (Permissions.COL_VISITING_TO_PHONE_NUMBER))
                    list.add(permissions)
                } while (cursor.moveToNext())
            }
        }
        return list
    }
}