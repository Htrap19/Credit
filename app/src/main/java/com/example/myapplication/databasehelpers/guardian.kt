package com.example.myapplication.databasehelpers

import android.content.ContentValues
import android.database.Cursor
import java.io.Serializable

class Guardian : Serializable {
    var id: Int = 0
    var name: String = ""
    var studentId: Int = -1
    var adjective: Int = -1
    var phoneNumber: Int = 0
    var occupation: String = ""
    var address: String = ""
    var monthlyIncome: Float = 0.0f
    var idNumber: String = ""
    var idType: String = ""

    constructor(name: String,
                adjective: Int,
                phoneNumber: Int,
                occupation: String,
                monthlyIncome: Float,
                address: String,
                idNumber: String,
                idType: String) {
        this.name = name
        this.adjective = adjective
        this.phoneNumber = phoneNumber
        this.occupation = occupation
        this.monthlyIncome = monthlyIncome
        this.address = address
        this.idNumber = idNumber
        this.idType = idType
    }

    constructor() {
    }

    companion object Columns {
        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_STUDENT_ID = "student_id"
        const val COL_ADJECTIVE = "adjective"
        const val COL_PHONENUMBER = "phone_number"
        const val COL_ADDRESS = "address"
        const val COL_OCCUPATION = "occupation"
        const val COL_MONTHLYINCOME = "monthly_income"
        const val COL_IDNUMBER = "id_number"
        const val COL_ID_TYPE = "id_type"
    }
}

class GuardianTbl : Table<Guardian> {
    override val tableName: String = "guardians"

    override fun creationQuery(): String {
        return "CREATE TABLE $tableName (" +
                "${Guardian.COL_ID           } INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${Guardian.COL_STUDENT_ID   } INTEGER," +
                "${Guardian.COL_NAME         } VARCHAR(256)," +
                "${Guardian.COL_ADJECTIVE    } INTEGER," +
                "${Guardian.COL_PHONENUMBER  } INTEGER," +
                "${Guardian.COL_OCCUPATION   } VARCHAR(256)," +
                "${Guardian.COL_MONTHLYINCOME} REAL," +
                "${Guardian.COL_ADDRESS      } VARCHAR(256)," +
                "${Guardian.COL_IDNUMBER     } VARCHAR(256)," +
                "${Guardian.COL_ID_TYPE      } VARCHAR(256))"
    }

    override fun contentValues(data: Guardian): ContentValues {
        val cv = ContentValues()
        cv.put(Guardian.COL_STUDENT_ID, data.studentId)
        cv.put(Guardian.COL_NAME, data.name)
        cv.put(Guardian.COL_ADJECTIVE, data.adjective)
        cv.put(Guardian.COL_PHONENUMBER, data.phoneNumber)
        cv.put(Guardian.COL_OCCUPATION, data.occupation)
        cv.put(Guardian.COL_MONTHLYINCOME, data.monthlyIncome)
        cv.put(Guardian.COL_ADDRESS, data.address)
        cv.put(Guardian.COL_IDNUMBER, data.idNumber)
        cv.put(Guardian.COL_ID_TYPE, data.idType)
        return cv
    }

    override fun mutableList(cursor: Cursor?): MutableList<Guardian> {
        val list: MutableList<Guardian> = ArrayList()
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val guardian = Guardian()
                    guardian.id = cursor.getInt(cursor.getColumnIndex               (Guardian.COL_ID))
                    guardian.studentId = cursor.getInt(cursor.getColumnIndex        (Guardian.COL_STUDENT_ID))
                    guardian.name = cursor.getString(cursor.getColumnIndex          (Guardian.COL_NAME))
                    guardian.adjective = cursor.getInt(cursor.getColumnIndex        (Guardian.COL_ADJECTIVE))
                    guardian.phoneNumber = cursor.getInt(cursor.getColumnIndex      (Guardian.COL_PHONENUMBER))
                    guardian.occupation = cursor.getString(cursor.getColumnIndex    (Guardian.COL_OCCUPATION))
                    guardian.monthlyIncome = cursor.getFloat(cursor.getColumnIndex  (Guardian.COL_MONTHLYINCOME))
                    guardian.address = cursor.getString(cursor.getColumnIndex       (Guardian.COL_ADDRESS))
                    guardian.idNumber = cursor.getString(cursor.getColumnIndex      (Guardian.COL_IDNUMBER))
                    guardian.idType = cursor.getString(cursor.getColumnIndex        (Guardian.COL_ID_TYPE))
                    list.add(guardian)
                } while (cursor.moveToNext())
            }
        }
        return list
    }

    override fun deletionQuery(): String {
        return "DROP TABLE IF EXISTS $tableName"
    }

}