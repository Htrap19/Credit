package com.example.myapplication.databasehelpers

import android.content.ContentValues
import android.database.Cursor
import java.io.Serializable

class Repayments : Serializable {
    var id: Int = 0
    var universityId: Int = -1
    var paidAmount: Float = -1.0f
    var paidDate: String = ""

    constructor(universityId: Int, paidAmount: Float, paidDate: String) {
        this.universityId = universityId
        this.paidAmount = paidAmount
        this.paidDate = paidDate
    }

    constructor() {
    }

    companion object {
        const val COL_ID = "id"
        const val COL_UNIVERSITY_ID = "university_id"
        const val COL_PAID_AMOUNT = "paid_amount"
        const val COL_PAID_DATE = "paid_date"
    }
}

class RepaymentsTbl : Table<Repayments> {
    override val tableName: String = "repayments"

    override fun creationQuery(): String {
        return "CREATE TABLE IF NOT EXISTS $tableName (" +
                "${Repayments.COL_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${Repayments.COL_UNIVERSITY_ID} INTEGER," +
                "${Repayments.COL_PAID_AMOUNT} REAL," +
                "${Repayments.COL_PAID_DATE} TEXT)"
    }

    override fun deletionQuery(): String {
        return "DROP TABLE IF EXISTS $tableName"
    }

    override fun contentValues(data: Repayments): ContentValues {
        val cv = ContentValues()
        cv.put(Repayments.COL_UNIVERSITY_ID, data.universityId)
        cv.put(Repayments.COL_PAID_AMOUNT,   data.paidAmount)
        cv.put(Repayments.COL_PAID_DATE,     data.paidDate)
        return cv
    }

    override fun mutableList(cursor: Cursor?): MutableList<Repayments> {
        val list: MutableList<Repayments> = ArrayList()
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val repayments = Repayments()
                    repayments.id = cursor.getInt(cursor.getColumnIndex               (Repayments.COL_ID))
                    repayments.universityId = cursor.getInt(cursor.getColumnIndex     (Repayments.COL_UNIVERSITY_ID))
                    repayments.paidAmount = cursor.getFloat(cursor.getColumnIndex     (Repayments.COL_PAID_AMOUNT))
                    repayments.paidDate = cursor.getString(cursor.getColumnIndex      (Repayments.COL_PAID_DATE))
                    list.add(repayments)
                } while (cursor.moveToNext())
            }
        }
        return list
    }

}