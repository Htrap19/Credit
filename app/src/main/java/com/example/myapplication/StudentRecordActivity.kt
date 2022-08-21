package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databasehelpers.CaseStudyTbl
import com.example.myapplication.databasehelpers.DatabaseHelper
import com.example.myapplication.databasehelpers.StudentTbl
import kotlinx.android.synthetic.main.activity_student_record.*

class StudentRecordActivity : AppCompatActivity() {
    private val studentTbl = StudentTbl()
    private val caseStudyTbl = CaseStudyTbl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_record)

        title = getString(R.string.student_record)

        val studentsRecords = ArrayList<StudentRecordModel>()
        val sDB = DatabaseHelper(this, studentTbl)
        val studentResults = sDB.readData()
        sDB.close()
        val cDB = DatabaseHelper(this, caseStudyTbl)
        val caseStudyResults = cDB.readData()
        cDB.close()
        for (student in studentResults) {
            for (caseStudy in caseStudyResults) {
                if (caseStudy.studentId == student.id) {
                    studentsRecords.add(StudentRecordModel(student.name, caseStudy.university, caseStudy.college, caseStudy.fees.toString()))
                }
            }
        }

        val studentAdapter = StudentRecordAdapter(studentsRecords, this)
        rcvStudentRecords.layoutManager = LinearLayoutManager(this)
        rcvStudentRecords.adapter = studentAdapter
    }
}