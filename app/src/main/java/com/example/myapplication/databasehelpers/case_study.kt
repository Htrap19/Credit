package com.example.myapplication.databasehelpers

import android.content.ContentValues
import android.database.Cursor
import java.io.Serializable

class CaseStudy : Serializable {
    var id: Int = 0
    var studentId: Int = -1
    var university: String = ""
    var college: String = ""
    var classroom: String = ""
    var semester: String = ""
    var fees: Float = -1.0f
    var universityId: Int = -1

    constructor(university: String,
                college: String,
                classroom: String,
                semester: String,
                fees: Float,
                universityId: Int) {
        this.university = university
        this.college = college
        this.classroom = classroom
        this.semester = semester
        this.fees = fees
        this.universityId = universityId
    }

    constructor() {
    }

    companion object Columns {
        const val COL_ID = "id"
        const val COL_STUDENT_ID = "student_id"
        const val COL_UNIVERSITY = "university"
        const val COL_COLLEGE = "college"
        const val COL_CLASSROOM = "classroom"
        const val COL_SEMESTER = "semester"
        const val COL_FEES = "fees"
        const val COL_UNIVERSITY_ID = "university_id"
    }
}

class CaseStudyTbl : Table<CaseStudy> {
    override val tableName: String = "case_study"

    override fun creationQuery(): String {
        return "CREATE TABLE IF NOT EXISTS $tableName (" +
                "${CaseStudy.COL_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${CaseStudy.COL_STUDENT_ID} INTEGER," +
                "${CaseStudy.COL_UNIVERSITY} VARCHAR(256)," +
                "${CaseStudy.COL_COLLEGE} VARCHAR(256)," +
                "${CaseStudy.COL_CLASSROOM} VARCHAR(256)," +
                "${CaseStudy.COL_SEMESTER} VARCHAR(256)," +
                "${CaseStudy.COL_FEES} REAL," +
                "${CaseStudy.COL_UNIVERSITY_ID} INTEGER)"
    }

    override fun contentValues(data: CaseStudy): ContentValues {
        val cv = ContentValues()
        cv.put(CaseStudy.COL_STUDENT_ID,    data.studentId)
        cv.put(CaseStudy.COL_UNIVERSITY,    data.university)
        cv.put(CaseStudy.COL_COLLEGE,       data.college)
        cv.put(CaseStudy.COL_CLASSROOM,     data.classroom)
        cv.put(CaseStudy.COL_SEMESTER,      data.semester)
        cv.put(CaseStudy.COL_FEES,          data.fees)
        cv.put(CaseStudy.COL_UNIVERSITY_ID, data.universityId)
        return cv
    }

    override fun mutableList(cursor: Cursor?): MutableList<CaseStudy> {
        val list: MutableList<CaseStudy> = ArrayList()
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val caseStudy = CaseStudy()
                    caseStudy.id = cursor.getInt(cursor.getColumnIndex               (CaseStudy.COL_ID))
                    caseStudy.studentId = cursor.getInt(cursor.getColumnIndex        (CaseStudy.COL_STUDENT_ID))
                    caseStudy.university = cursor.getString(cursor.getColumnIndex    (CaseStudy.COL_UNIVERSITY))
                    caseStudy.college = cursor.getString(cursor.getColumnIndex       (CaseStudy.COL_COLLEGE))
                    caseStudy.classroom = cursor.getString(cursor.getColumnIndex     (CaseStudy.COL_CLASSROOM))
                    caseStudy.semester = cursor.getString(cursor.getColumnIndex      (CaseStudy.COL_SEMESTER))
                    caseStudy.fees = cursor.getFloat(cursor.getColumnIndex           (CaseStudy.COL_FEES))
                    caseStudy.universityId = cursor.getInt(cursor.getColumnIndex     (CaseStudy.COL_UNIVERSITY_ID))
                    list.add(caseStudy)
                } while (cursor.moveToNext())
            }
        }
        return list
    }

    override fun deletionQuery(): String {
        return "DROP TABLE IF EXISTS $tableName"
    }

}