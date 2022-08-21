package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.myapplication.databasehelpers.*
import kotlinx.android.synthetic.main.activity_permissions.*

class PermissionsActivity : AppCompatActivity() {
    private val caseStudyTbl = CaseStudyTbl()
    private val studentTbl = StudentTbl()
    private val accommodationTbl = AccommodationTbl()
    private val permissionsTbl = PermissionsTbl()
    private val universityIds = ArrayList<String>()
    private val caseStudyList = ArrayList<CaseStudy>()
    private lateinit var caseStudyObj: CaseStudy
    private lateinit var accommodationObj: Accommodation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permissions)
        title = getString(R.string.permissions)

        val unids = ArrayAdapter(this, R.layout.auto_complete_dropdown_layout, R.id.txtResultName, universityIds)
        edtUniversityId.setAdapter(unids)

        edtUniversityId.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
            val selectedUniversityId = parent.getItemAtPosition(position).toString()

            for (data in caseStudyList) {
                if (selectedUniversityId == data.universityId.toString()) {
                    val sDB = DatabaseHelper(this, studentTbl)
                    val students = sDB.readData()
                    sDB.close()
                    val aDB = DatabaseHelper(this, accommodationTbl)
                    val accommodations = aDB.readData()
                    aDB.close()

                    for (student in students) {
                        val haveAccommodation = accommodations.find { it.caseStudyId == data.id }
                        if (student.id == data.studentId && haveAccommodation != null) {
                            setTexts(data, student)
                            caseStudyObj = data
                            accommodationObj = haveAccommodation
                        }
                    }
                }
            }
        }

        btnSavePermissions.setOnClickListener {
            if (!this::caseStudyObj.isInitialized || !caseStudyList.contains(caseStudyObj)) {
                showToastShort(getString(R.string.please_select_a_valid_university_id))
                return@setOnClickListener
            }

            val yearHistory = edtYearHistory.text
            val returnDate = edtReturnDate.text
            val numberOfDays = edtNumberOfDays.text
            val visitingToName = edtVisitingToName.text
            val visitingToAddress = edtVisitingToAddress.text
            val visitingToPhoneNumber = edtVisitingToPhoneNumber.text

            if (yearHistory.isEmpty() ||
                returnDate.isEmpty() ||
                numberOfDays.isEmpty() ||
                visitingToName.isEmpty() ||
                visitingToAddress.isEmpty() ||
                visitingToPhoneNumber.isEmpty()) {
                showToastShort(getString(R.string.please_complete_the_form))
                return@setOnClickListener
            }

            val permissions = Permissions(yearHistory.toString(),
            returnDate.toString(), numberOfDays.toString().toInt(),
            visitingToName.toString(), visitingToAddress.toString(),
            visitingToPhoneNumber.toString())
            permissions.accommodationId = accommodationObj.id

            val pDB = DatabaseHelper(this, permissionsTbl)
            if (pDB.insertData(permissions)) {
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