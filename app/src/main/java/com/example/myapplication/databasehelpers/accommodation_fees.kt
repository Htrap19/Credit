package com.example.myapplication.databasehelpers

import android.content.ContentValues
import android.database.Cursor
import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AccommodationFees : Serializable {
    var id: Int = 0
    var caseStudyId: Int = -1
    var year: String = ""
    var amount: Double = 0.0
    var receiptNumber: String = ""
    var date: String = ""

    constructor() {}

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(year: String,
                amount: Double,
                receiptNumber: String) {
        this.year = year
        this.amount = amount
        this.receiptNumber = receiptNumber

        val date = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        this.date = date.format(formatter)
    }

    companion object Columns {
        const val COL_ID = "id"
        const val COL_CASE_STUDY_ID = "case_study_id"
        const val COL_YEAR = "year"
        const val COL_AMOUNT = "amount"
        const val COL_DATE = "date"
        const val COL_RECEIPT_NUMBER = "receipt_number"
    }
}

class AccommodationFeesTbl : Table<AccommodationFees> {
    override val tableName: String = "accommodation_fees"
    override fun creationQuery(): String {
        return "CREATE TABLE IF NOT EXISTS $tableName (" +
                "${AccommodationFees.COL_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${AccommodationFees.COL_CASE_STUDY_ID} INTEGER," +
                "${AccommodationFees.COL_YEAR} VARCHAR(256)," +
                "${AccommodationFees.COL_AMOUNT} REAL," +
                "${AccommodationFees.COL_DATE} TEXT," +
                "${AccommodationFees.COL_RECEIPT_NUMBER} VARCHAR(256));"
    }

    override fun deletionQuery(): String {
        return "DROP TABLE IF EXISTS $tableName"
    }

    override fun contentValues(data: AccommodationFees): ContentValues {
        val cv = ContentValues()
        cv.put(AccommodationFees.COL_CASE_STUDY_ID,  data.caseStudyId)
        cv.put(AccommodationFees.COL_YEAR,           data.year)
        cv.put(AccommodationFees.COL_AMOUNT,         data.amount)
        cv.put(AccommodationFees.COL_DATE,           data.date)
        cv.put(AccommodationFees.COL_RECEIPT_NUMBER, data.receiptNumber)
        return cv
    }

    override fun mutableList(cursor: Cursor?): MutableList<AccommodationFees> {
        val list: MutableList<AccommodationFees> = ArrayList()
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val accommodationFees = AccommodationFees()
                    accommodationFees.id = cursor.getInt(cursor.getColumnIndex                (AccommodationFees.COL_ID))
                    accommodationFees.caseStudyId = cursor.getInt(cursor.getColumnIndex       (AccommodationFees.COL_CASE_STUDY_ID))
                    accommodationFees.amount = cursor.getDouble(cursor.getColumnIndex         (AccommodationFees.COL_AMOUNT))
                    accommodationFees.year = cursor.getString(cursor.getColumnIndex           (AccommodationFees.COL_YEAR))
                    accommodationFees.receiptNumber = cursor.getString(cursor.getColumnIndex  (AccommodationFees.COL_RECEIPT_NUMBER))
                    accommodationFees.date = cursor.getString(cursor.getColumnIndex           (AccommodationFees.COL_DATE))
                    list.add(accommodationFees)
                } while (cursor.moveToNext())
            }
        }
        return list
    }

}