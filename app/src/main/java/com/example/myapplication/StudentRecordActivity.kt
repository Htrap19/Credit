package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_student_record.*

class StudentRecordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_record)

        title = getString(R.string.student_record)

        val studentsRecords = ArrayList<StudentRecordModel>()
        studentsRecords.add(StudentRecordModel("S", "Somewhere", "Unknown", "100,000"))
        studentsRecords.add(StudentRecordModel("Someone", "Somewhere", "Unknown", "100,000"))
        studentsRecords.add(StudentRecordModel("Some one more", "Somewhere here", "Unknown", "100,000"))
        studentsRecords.add(StudentRecordModel("And something", "Somewhere", "Unknown", "100,000"))
        studentsRecords.add(StudentRecordModel("And something", "Somewhere Maybe", "Unknown More", "100,000"))

        val studentAdapter = StudentRecordAdapter(studentsRecords, this)
        rcvStudentRecords.layoutManager = LinearLayoutManager(this)
        rcvStudentRecords.adapter = studentAdapter
    }
}