package com.example.myapplication.databasehelpers

import android.content.ContentValues
import android.database.Cursor
import java.io.Serializable

class Warrenty : Serializable {
    var id: Int = 0
    var guardianId: Int = -1
    var numberOfFamilyEscapes: Int = 0
    var numberOfBrothersInUniversity: Int = 0
    var isFatherAlive: Boolean = true

    constructor() {}

    constructor(numberOfFamilyEscapes: Int,
                numberOfBrothersInUniversity: Int,
                isFatherAlive: Boolean) {
        this.numberOfFamilyEscapes = numberOfFamilyEscapes
        this.numberOfBrothersInUniversity = numberOfBrothersInUniversity
        this.isFatherAlive = isFatherAlive
    }

    companion object {
        const val COL_ID = "id"
        const val COL_GUARDIAN_ID = "guardian_id"
        const val COL_NUMBER_OF_FAMILY_ESCAPES = "number_of_family_escapes"
        const val COL_NUMBER_OF_BROTHERS_IN_UNIVERSITY = "number_of_brothers_in_university"
        const val COL_IS_FATHER_ALIVE = "is_father_alive"
    }
}

class WarrentyTbl : Table<Warrenty> {
    override val tableName = "warrenty"
    override fun creationQuery(): String {
        return "CREATE TABLE IF NOT EXISTS $tableName (" +
                "${Warrenty.COL_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${Warrenty.COL_GUARDIAN_ID} INTEGER," +
                "${Warrenty.COL_NUMBER_OF_FAMILY_ESCAPES} INTEGER," +
                "${Warrenty.COL_NUMBER_OF_BROTHERS_IN_UNIVERSITY} INTEGER," +
                "${Warrenty.COL_IS_FATHER_ALIVE} INTEGER);"
    }

    override fun deletionQuery(): String {
        return "DROP TABLE IF EXISTS $tableName"
    }

    override fun contentValues(data: Warrenty): ContentValues {
        val cv = ContentValues()
        cv.put(Warrenty.COL_GUARDIAN_ID,                      data.guardianId)
        cv.put(Warrenty.COL_NUMBER_OF_FAMILY_ESCAPES,         data.numberOfFamilyEscapes)
        cv.put(Warrenty.COL_NUMBER_OF_BROTHERS_IN_UNIVERSITY, data.numberOfBrothersInUniversity)
        cv.put(Warrenty.COL_IS_FATHER_ALIVE, if (data.isFatherAlive) 1 else 0)
        return cv
    }

    override fun mutableList(cursor: Cursor?): MutableList<Warrenty> {
        val list: MutableList<Warrenty> = ArrayList()
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val warrenty = Warrenty()
                    warrenty.id = cursor.getInt(cursor.getColumnIndex                               (Warrenty.COL_ID))
                    warrenty.guardianId = cursor.getInt(cursor.getColumnIndex                       (Warrenty.COL_GUARDIAN_ID))
                    warrenty.numberOfFamilyEscapes = cursor.getInt(cursor.getColumnIndex            (Warrenty.COL_NUMBER_OF_FAMILY_ESCAPES))
                    warrenty.numberOfBrothersInUniversity = cursor.getInt(cursor.getColumnIndex     (Warrenty.COL_NUMBER_OF_BROTHERS_IN_UNIVERSITY))
                    val alive = cursor.getInt(cursor.getColumnIndex                                 (Warrenty.COL_IS_FATHER_ALIVE))
                    warrenty.isFatherAlive = alive == 1
                    list.add(warrenty)
                } while (cursor.moveToNext())
            }
        }
        return list
    }

}