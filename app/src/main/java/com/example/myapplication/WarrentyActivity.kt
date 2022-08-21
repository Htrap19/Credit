package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.myapplication.databasehelpers.*
import kotlinx.android.synthetic.main.activity_warrenty.*

class WarrentyActivity : AppCompatActivity() {
    private val caseStudyTbl = CaseStudyTbl()
    private val studentTbl = StudentTbl()
    private val guardianTbl = GuardianTbl()
    private val warrentyTbl = WarrentyTbl()
    private val universityIds = ArrayList<String>()
    private val caseStudyList = ArrayList<CaseStudy>()
    private lateinit var caseStudyObj: CaseStudy
    private lateinit var guardianObj: Guardian

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_warrenty)
        title = getString(R.string.warranty)

        val unids = ArrayAdapter(this, R.layout.auto_complete_dropdown_layout, R.id.txtResultName, universityIds)
        edtUniversityId.setAdapter(unids)

        edtUniversityId.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
            val selectedUniversityId = parent.getItemAtPosition(position).toString()

            for (data in caseStudyList) {
                if (selectedUniversityId == data.universityId.toString()) {
                    val sDB = DatabaseHelper(this, studentTbl)
                    val students = sDB.readData()
                    sDB.close()

                    val gDB = DatabaseHelper(this, guardianTbl)
                    val guardians = gDB.readData()
                    gDB.close()

                    for (student in students) {
                        val guardianInfo = guardians.find { it.studentId == student.id }
                        if (student.id == data.studentId && guardianInfo != null) {
                            setTexts(data, student)
                            caseStudyObj = data
                            guardianObj = guardianInfo
                        }
                    }
                }
            }
        }

        btnSaveWarrenty.setOnClickListener {
            if (!this::caseStudyObj.isInitialized || !caseStudyList.contains(caseStudyObj)) {
                showToastShort(getString(R.string.please_select_a_valid_university_id))
                return@setOnClickListener
            }

            val numberOfFamilyEscapes = edtNumberOfFamilyEscapes.text
            val numberOfBrotherInUniversity = edtNumberOfBrothers.text
            val isFatherAlive = rbYes.isChecked

            if (numberOfFamilyEscapes.isEmpty() ||
                    numberOfBrotherInUniversity.isEmpty()) {
                showToastShort(getString(R.string.please_complete_the_form))
                return@setOnClickListener
            }

            val warrenty = Warrenty(numberOfFamilyEscapes.toString().toInt(),
                                    numberOfBrotherInUniversity.toString().toInt(),
                                    isFatherAlive)
            warrenty.guardianId = guardianObj.id
            val wDB = DatabaseHelper(this, warrentyTbl)
            if (wDB.insertData(warrenty)) {
                val intent = Intent(this, HousingActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
            } else {
                showToastShort(getString(R.string.unable_to_save))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        loadUniversityIdForAutoComplete()
    }

    override fun onResume() {
        super.onResume()
        loadUniversityIdForAutoComplete()
    }

    private fun setTexts(caseStudy: CaseStudy, student: Student) {
        tvStudentName.text = student.name
        tvStudentPhoneNumber.text = student.phoneNumber.toString()
        tvUniversity.text = caseStudy.university
        tvCollege.text = caseStudy.college
    }

    private fun loadUniversityIdForAutoComplete() {
        val db = DatabaseHelper(this, caseStudyTbl)
        val results = db.readData()
        db.close()
        if (results.size != universityIds.size) {
            universityIds.clear()
            caseStudyList.clear()
            for (caseStudy in results) {
                universityIds.add(caseStudy.universityId.toString())
                caseStudyList.add(caseStudy)
            }
        }
    }
}