package com.example.myapplication

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import com.example.myapplication.databasehelpers.*
import kotlinx.android.synthetic.main.activity_housing_fee.*

class HousingFeeActivity : AppCompatActivity() {
    private val caseStudyTbl = CaseStudyTbl()
    private val studentTbl = StudentTbl()
    private val accommodationFeesTbl = AccommodationFeesTbl()
    private val universityIds = ArrayList<String>()
    private val caseStudyList = ArrayList<CaseStudy>()
    private lateinit var caseStudyObj: CaseStudy

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_housing_fee)
        title = getString(R.string.residential_fees)

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

        val years = ArrayList<String>()
        years.add("2022");
        years.add("2021");
        years.add("2020");

        val yearAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, years)
        edtYear.adapter = yearAdapter

        btnSaveAccommodationFees.setOnClickListener {
            if (!this::caseStudyObj.isInitialized || !caseStudyList.contains(caseStudyObj)) {
                showToastShort(getString(R.string.please_select_a_valid_university_id))
                return@setOnClickListener
            }

            val amount = edtAmount.text
            val receiptNumber = edtReceiptNumber.text
            val year = edtYear.selectedItem.toString()

            if (amount.isEmpty() ||
                receiptNumber.isEmpty()) {
                showToastShort(getString(R.string.please_complete_the_form))
                return@setOnClickListener
            }

            val accommodationFees = AccommodationFees(year, amount.toString().toDouble(), receiptNumber.toString())
            accommodationFees.caseStudyId = caseStudyObj.id
            val aDB = DatabaseHelper(this, accommodationFeesTbl)
            if (aDB.insertData(accommodationFees)) {
                val intent = Intent(this, HousingActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
            } else {
                showToastShort(getString(R.string.unable_to_save))
            }
        }
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