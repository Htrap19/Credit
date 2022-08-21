package com.example.myapplication.databasehelpers

import android.content.ContentValues
import android.database.Cursor
import java.io.Serializable

class Accommodation : Serializable {
    var id: Int = 0
    var caseStudyId: Int = -1
    var interior: String = ""
    var wing: String = ""
    var roomNumber: Int = 0
    var bedNumber: Int = 0
    var cupBoardNumber: Int = 0

    constructor(interior: String,
                wing: String,
                roomNumber: Int,
                bedNumber: Int,
                cupBoardNumber: Int) {
        this.interior = interior
        this.wing = wing
        this.roomNumber = roomNumber
        this.bedNumber = bedNumber
        this.cupBoardNumber = cupBoardNumber
    }

    constructor() {}

    companion object Columns {
        const val COL_ID = "id"
        const val COL_CASE_STUDY_ID = "case_study_id"
        const val COL_INTERIOR = "interior"
        const val COL_WING = "wing"
        const val COL_ROOM_NUMBER = "room_number"
        const val COL_BED_NUMBER = "bed_number"
        const val COL_CUPBOARD_NUMBER = "cup_board"
    }
}

class AccommodationTbl : Table<Accommodation> {
    override val tableName: String = "accommodation"
    override fun creationQuery(): String {
        return "CREATE TABLE IF NOT EXISTS $tableName (" +
                "${Accommodation.COL_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${Accommodation.COL_CASE_STUDY_ID} INTEGER," +
                "${Accommodation.COL_INTERIOR} VARCHAR(256)," +
                "${Accommodation.COL_WING} VARCHAR(256)," +
                "${Accommodation.COL_ROOM_NUMBER} INTEGER," +
                "${Accommodation.COL_BED_NUMBER} INTEGER," +
                "${Accommodation.COL_CUPBOARD_NUMBER} INTEGER);"
    }

    override fun deletionQuery(): String {
        return "DROP TABLE IF EXISTS $tableName"
    }

    override fun contentValues(data: Accommodation): ContentValues {
        val cv = ContentValues()
        cv.put(Accommodation.COL_CASE_STUDY_ID,   data.caseStudyId)
        cv.put(Accommodation.COL_INTERIOR,        data.interior)
        cv.put(Accommodation.COL_WING,            data.wing)
        cv.put(Accommodation.COL_ROOM_NUMBER,     data.roomNumber)
        cv.put(Accommodation.COL_BED_NUMBER,      data.bedNumber)
        cv.put(Accommodation.COL_CUPBOARD_NUMBER, data.cupBoardNumber)
        return cv
    }

    override fun mutableList(cursor: Cursor?): MutableList<Accommodation> {
        val list: MutableList<Accommodation> = ArrayList()
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val accommodation = Accommodation()
                    accommodation.id = cursor.getInt(cursor.getColumnIndex                (Accommodation.COL_ID))
                    accommodation.caseStudyId = cursor.getInt(cursor.getColumnIndex       (Accommodation.COL_CASE_STUDY_ID))
                    accommodation.interior = cursor.getString(cursor.getColumnIndex       (Accommodation.COL_INTERIOR))
                    accommodation.wing = cursor.getString(cursor.getColumnIndex           (Accommodation.COL_WING))
                    accommodation.roomNumber = cursor.getInt(cursor.getColumnIndex        (Accommodation.COL_ROOM_NUMBER))
                    accommodation.bedNumber = cursor.getInt(cursor.getColumnIndex         (Accommodation.COL_BED_NUMBER))
                    accommodation.cupBoardNumber = cursor.getInt(cursor.getColumnIndex    (Accommodation.COL_CUPBOARD_NUMBER))
                    list.add(accommodation)
                } while (cursor.moveToNext())
            }
        }
        return list
    }
}