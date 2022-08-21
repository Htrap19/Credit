package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databasehelpers.*
import kotlinx.android.synthetic.main.activity_case_study.*

class CaseStudyActivity : AppCompatActivity() {
    private val studentsTbl = StudentTbl()
    private val guardianTbl = GuardianTbl()
    private val caseStudyTbl = CaseStudyTbl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_case_study)

        title = getString(R.string.case_study)
        val studentObj = intent.getSerializableExtra("studentObj") as Student
        val guardianObj = intent.getSerializableExtra("guardianObj") as Guardian

        btnNewAdditionSave.setOnClickListener {
            try {
                if (edtUniversity.text.isNotEmpty() && edtCollege.text.isNotEmpty() && edtClassroom.text.isNotEmpty() && edtSemester.text.isNotEmpty() && edtFees.text.isNotEmpty() && edtUniversityId.text.isNotEmpty()) {
                    val caseStudyObj = CaseStudy(edtUniversity.text.toString(), edtCollege.text.toString(), edtClassroom.text.toString(), edtSemester.text.toString(), edtFees.text.toString().toFloat(), edtUniversityId.text.toString().toInt())

                    val sDB = DatabaseHelper(this, studentsTbl)
                    if (sDB.insertData(studentObj)) {
                        val result = sDB.readData("${studentsTbl.tableName}.${Student.COL_NAME} = '${studentObj.name}'")
                        sDB.close()

                        if (result.size == 1) {
                            guardianObj.studentId = result[0].id

                            val gDB = DatabaseHelper(this, guardianTbl)
                            if (gDB.insertData(guardianObj)) {
                                gDB.close()

                                val cDB = DatabaseHelper(this, caseStudyTbl)
                                caseStudyObj.studentId = result[0].id
                                if (cDB.insertData(caseStudyObj)) {
                                    cDB.close()
                                    val intent = Intent(this, DashBoardActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    cDB.close()
                                    showToastShort(getString(R.string.unable_to_save))
                                }
                            } else {
                                gDB.close()
                                showToastShort(getString(R.string.unable_to_save))
                            }
                        }
                    } else {
                        sDB.close()
                        showToastShort(getString(R.string.unable_to_save))
                    }
                } else {
                    showToastShort(getString(R.string.please_complete_the_form))
                }
            } catch (e: Exception) {
                showToastShort(e.message.toString())
            }
        }
    }
}