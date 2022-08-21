package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.myapplication.databasehelpers.*
import kotlinx.android.synthetic.main.activity_research.*
import kotlin.math.log

class ResearchActivity : AppCompatActivity() {
    private val studentTbl = StudentTbl()
    private val caseStudyTbl = CaseStudyTbl()
    private val guardianTbl = GuardianTbl()
    private val bankProcedureTbl = BankProcedureTbl()
    private val names = ArrayList<String>()
    private val studentList = ArrayList<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_research)

        title = getString(R.string.find)
        loadNamesForAutoComplete()

        val adapter = ArrayAdapter(this, R.layout.auto_complete_dropdown_layout, R.id.txtResultName, names)
        edtStudentName.setAdapter(adapter)

        edtStudentName.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position).toString()

            for (student in studentList) {
                if (student.name == selectedItem) {
                    fetchStudentInfo(student)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        loadNamesForAutoComplete()
    }

    override fun onResume() {
        super.onResume()
        loadNamesForAutoComplete()
    }

    private fun fetchStudentInfo(student: Student) {
        val db = DatabaseHelper(this, caseStudyTbl)
        val results = db.readData()
        db.close()

        val db2 = DatabaseHelper(this, guardianTbl)
        val gResults = db2.readData()
        db2.close()

        val db3 = DatabaseHelper(this, bankProcedureTbl)
        val bResults = db3.readData()
        db3.close()

        for (cs in results) {
            if (student.id == cs.studentId) {
                for (g in gResults) {
                    if (student.id == g.studentId) {
                        for (b in bResults) {
                            if (student.id == b.id) {
                                setTexts(student.name, student.phoneNumber.toString(), g.name, student.dateOfBirth, student.idNumber, student.idType, cs.university, cs.college, cs.fees, cs.semester, cs.universityId.toString(), b.bank, b.accountNumber.toString())
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setTexts(studentName: String, phoneNumber: String, guardianName: String, dateOfBirth: String, idNumber: String, idType: String, university: String, college: String, fees: Float, semester: String, universityId: String, bank: String, accountNumber: String) {
        txtStudentNameValue.text = studentName
        txtPhoneNumberValue.text = phoneNumber
        txtGuardianNameValue.text = guardianName
        txtDateOfBirthValue.text = dateOfBirth
        txtIdNumberValue.text = idNumber
        txtIdType.text = idType
        txtUniversityValue.text = university
        txtCollegeValue.text = college
        txtFeesValue.text = fees.toString()
        txtSemesterValue.text = semester
        txtUniversityIdValue.text = universityId
        txtBankValue.text = bank
        txtAccountNumberValue.text = accountNumber
    }

    private fun loadNamesForAutoComplete() {
        val db = DatabaseHelper(this, studentTbl)
        val results = db.readData()
        db.close()
        if (results.size != names.size) {
            names.clear()
            for (student in results) {
                names.add(student.name)
                studentList.add(student)
            }
        }
    }
}