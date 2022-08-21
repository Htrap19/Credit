package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.myapplication.databasehelpers.*
import kotlinx.android.synthetic.main.activity_housing_registation.*

class HousingRegistrationActivity : AppCompatActivity() {
    private val caseStudyTbl = CaseStudyTbl()
    private val studentTbl = StudentTbl()
    private val accommodationTbl = AccommodationTbl()
    private val universityIds = ArrayList<String>()
    private val caseStudyList = ArrayList<CaseStudy>()
    private lateinit var caseStudyObj: CaseStudy

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_housing_registation)
        title = getString(R.string.new_registration)

        loadUniversityIdForAutoComplete()

        val unids = ArrayAdapter(this, R.layout.auto_complete_dropdown_layout, R.id.txtResultName, universityIds)
        edtUniversityId.setAdapter(unids)

        edtUniversityId.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
            val selectedUniversityId = parent.getItemAtPosition(position).toString()

            for (data in caseStudyList) {
                if (selectedUniversityId == data.universityId.toString()) {
                    val sDB = DatabaseHelper(this, studentTbl)
                    val students = sDB.readData()

                    for (student in students) {
                        if (student.id == data.studentId) {
                            setTexts(data, student)
                            caseStudyObj = data
                        }
                    }
                }
            }
        }

        btnSaveAccommodation.setOnClickListener {
            if (!this::caseStudyObj.isInitialized || !caseStudyList.contains(caseStudyObj)) {
                showToastShort(getString(R.string.please_select_a_valid_university_id))
                return@setOnClickListener
            }

            if (edtInterior.text.isEmpty() ||
                edtWing.text.isEmpty() ||
                edtRoomNumber.text.isEmpty() ||
                edtBedNumber.text.isEmpty() ||
                edtCupboard.text.isEmpty()) {
                showToastShort(getString(R.string.please_complete_the_form))
                return@setOnClickListener
            }

            val accommodation = Accommodation(edtInterior.text.toString(),
            edtWing.text.toString(),
            edtRoomNumber.text.toString().toInt(),
            edtBedNumber.text.toString().toInt(),
            edtCupboard.text.toString().toInt());
            accommodation.caseStudyId = caseStudyObj.id
            val aDB = DatabaseHelper(this, accommodationTbl)
            if (aDB.insertData(accommodation)) {
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