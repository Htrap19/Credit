package com.example.myapplication.databasehelpers

import android.content.ContentValues
import android.database.Cursor
import java.io.Serializable

class BankProcedure : Serializable {
    var id: Int = 0
    var studentId: Int = -1
    var bank: String = ""
    var bankType: String = ""
    var accountNumber: Int = -1
    var totalTuitionFees: Float = 0.0f
    var paymentPeriod: Int = 0
    var periodType: Int = -1
    var amountOfRepayment: Float = 0.0f

    constructor(bank: String, bankType: String, accountNumber: Int, totalTuitionFees: Float, paymentPeriod: Int, periodType: Int, periodPaymentAmount: Float) {
        this.bank = bank
        this.bankType = bankType
        this.accountNumber = accountNumber
        this.totalTuitionFees = totalTuitionFees
        this.paymentPeriod = paymentPeriod
        this.periodType = periodType
        this.amountOfRepayment = periodPaymentAmount
    }

    constructor() {
    }

    companion object Columns {
        const val COL_ID = "id"
        const val COL_STUDENT_ID = "student_id"
        const val COL_BANK = "bank"
        const val COL_BANK_TYPE = "bank_type"
        const val COL_ACCOUNT_NUMBER = "account_number"
        const val COL_TOTAL_TUITION_FEES = "total_tuition_fees"
        const val COL_PAYMENT_PERIOD = "payment_period"
        const val COL_PERIOD_TYPE = "period_type"
        const val COL_AMOUNT_OF_REPAYMENT = "amount_of_repayment"
    }
}

class BankProcedureTbl : Table<BankProcedure> {
    override val tableName: String = "bank_procedure"

    override fun creationQuery(): String {
        return "CREATE TABLE IF NOT EXISTS $tableName (" +
                "${BankProcedure.COL_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${BankProcedure.COL_STUDENT_ID} INTEGER," +
                "${BankProcedure.COL_BANK} TEXT," +
                "${BankProcedure.COL_BANK_TYPE} TEXT," +
                "${BankProcedure.COL_ACCOUNT_NUMBER} INTEGER," +
                "${BankProcedure.COL_TOTAL_TUITION_FEES} REAL," +
                "${BankProcedure.COL_PAYMENT_PERIOD} INTEGER," +
                "${BankProcedure.COL_PERIOD_TYPE} INTEGER," +
                "${BankProcedure.COL_AMOUNT_OF_REPAYMENT} REAL);"
    }

    override fun deletionQuery(): String {
        return "DROP TABLE IF EXISTS $tableName"
    }

    override fun contentValues(data: BankProcedure): ContentValues {
        val cv = ContentValues()
        cv.put(BankProcedure.COL_STUDENT_ID, data.studentId)
        cv.put(BankProcedure.COL_BANK, data.bank)
        cv.put(BankProcedure.COL_BANK_TYPE, data.bankType)
        cv.put(BankProcedure.COL_ACCOUNT_NUMBER, data.accountNumber)
        cv.put(BankProcedure.COL_TOTAL_TUITION_FEES, data.totalTuitionFees)
        cv.put(BankProcedure.COL_PAYMENT_PERIOD, data.paymentPeriod)
        cv.put(BankProcedure.COL_PERIOD_TYPE, data.periodType)
        cv.put(BankProcedure.COL_AMOUNT_OF_REPAYMENT, data.amountOfRepayment)
        return cv
    }

    override fun mutableList(cursor: Cursor?): MutableList<BankProcedure> {
        val list: MutableList<BankProcedure> = ArrayList()
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val bankProcedure = BankProcedure()
                    bankProcedure.id = cursor.getInt(cursor.getColumnIndex                      (BankProcedure.COL_ID))
                    bankProcedure.studentId = cursor.getInt(cursor.getColumnIndex               (BankProcedure.COL_STUDENT_ID))
                    bankProcedure.bank = cursor.getString(cursor.getColumnIndex                 (BankProcedure.COL_BANK))
                    bankProcedure.bankType = cursor.getString(cursor.getColumnIndex             (BankProcedure.COL_BANK_TYPE))
                    bankProcedure.accountNumber = cursor.getInt(cursor.getColumnIndex           (BankProcedure.COL_ACCOUNT_NUMBER))
                    bankProcedure.totalTuitionFees = cursor.getFloat(cursor.getColumnIndex           (BankProcedure.COL_TOTAL_TUITION_FEES))
                    bankProcedure.paymentPeriod = cursor.getInt(cursor.getColumnIndex           (BankProcedure.COL_PAYMENT_PERIOD))
                    bankProcedure.periodType = cursor.getInt(cursor.getColumnIndex              (BankProcedure.COL_PERIOD_TYPE))
                    bankProcedure.amountOfRepayment = cursor.getFloat(cursor.getColumnIndex   (BankProcedure.COL_AMOUNT_OF_REPAYMENT))
                    list.add(bankProcedure)
                } while (cursor.moveToNext())
            }
        }
        return list
    }

}