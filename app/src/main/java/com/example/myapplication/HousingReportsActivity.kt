package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.myapplication.databasehelpers.*
import kotlinx.android.synthetic.main.activity_housing_reports.*

class HousingReportsActivity : AppCompatActivity() {
    private val caseStudyTbl = CaseStudyTbl()
    private val studentTbl = StudentTbl()
    private val accommodationTbl = AccommodationTbl()
    private val accommodationFeesTbl = AccommodationFeesTbl()
    private val permissionsTbl = PermissionsTbl()
    private val guardianTbl = GuardianTbl()
    private val warrentyTbl = WarrentyTbl()

    private val universityIds = ArrayList<String>()
    private val caseStudyList = ArrayList<CaseStudy>()
    private val accommodationList = ArrayList<Accommodation>()
    private val accommodationFeesList = ArrayList<AccommodationFees>()
    private val permissionList = ArrayList<Permissions>()
    private val guardianList = ArrayList<Guardian>()
    private val warrentyList = ArrayList<Warrenty>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_housing_reports)
        title = getString(R.string.reports)

        loadUniversityIdForAutoComplete()

        val unids = ArrayAdapter(this, R.layout.auto_complete_dropdown_layout, R.id.txtResultName, universityIds)
        edtUniversityId.setAdapter(unids)

        edtUniversityId.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
            val selectedUniversityId = parent.getItemAtPosition(position).toString()

            for (caseStudy in caseStudyList) {
                if (selectedUniversityId == caseStudy.universityId.toString()) {
                    val sDB = DatabaseHelper(this, studentTbl)
                    val students = sDB.readData()

                    for (student in students) {
                        if (student.id == caseStudy.studentId) {
                            setTexts(caseStudy, student)
                            val accommodation = accommodationList.find { it.caseStudyId == caseStudy.id }
                            setAccommodationTexts(accommodation)
                            for (accommodationFees in accommodationFeesList)
                                if (accommodationFees.caseStudyId == caseStudy.id)
                                    appendAccommodationFeesTexts(accommodationFees)
                            if (accommodation != null)
                                for (permission in permissionList)
                                    if (permission.accommodationId == accommodation.id)
                                        appendPermissionTexts(permission)

                            val guardian = guardianList.find { it.studentId == student.id }
                            val warrenty = warrentyList.find { it.guardianId == guardian?.id }
                            if (warrenty != null)
                                guardian?.let { setWarrentyTexts(warrenty, it) }
                        }
                    }
                }
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

    private fun setAccommodationTexts(accommodation: Accommodation?) {
        if (accommodation == null)
            return

        tvInterior.text = accommodation.interior
        tvWing.text = accommodation.wing
        tvRoomNumber.text = accommodation.roomNumber.toString()
        tvBedNumber.text = accommodation.bedNumber.toString()
        tvCupBoardNumber.text = accommodation.cupBoardNumber.toString()
    }

    private fun appendAccommodationFeesTexts(accommodationFees: AccommodationFees) {
        edtReportsAccommodationFees.text.clear()
        edtReportsAccommodationFees.text.append("\n-\tAmount: ${accommodationFees.amount}\n" +
                "\tDate: ${accommodationFees.date}\n" +
                "\tYear: ${accommodationFees.year}\n")
    }

    private fun appendPermissionTexts(permissions: Permissions) {
        edtReportsPermissions.text.clear()
        edtReportsPermissions.text.append("\n-\tYear History: ${permissions.yearHistory}\n" +
                "\tReturn date: ${permissions.returnDate}\n" +
                "\tNumber of days: ${permissions.numberOfDays}\n" +
                "\tVisiting to name: ${permissions.visitingToName}\n" +
                "\tVisiting to address: ${permissions.visitingToAddress}\n" +
                "\tVisiting to phone number: ${permissions.visitingToPhoneNumber}\n"
        )
    }

    private fun setWarrentyTexts(warrenty: Warrenty, guardian: Guardian) {
        tvGuardianName.text = guardian.name
        tvMonthlyIncome.text = guardian.monthlyIncome.toString()
        tvOccupation.text = guardian.occupation
        tvNumberOfFamilyEscapes.text = warrenty.numberOfFamilyEscapes.toString()
        tvNumberOfBrothersInUniversity.text = warrenty.numberOfBrothersInUniversity.toString()
        tvIsFatherAlive.text = getString(if (warrenty.isFatherAlive) R.string.yes else R.string.no)
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

        val aDB = DatabaseHelper(this, accommodationTbl)
        val aResults = aDB.readData()
        aDB.close()
        if (aResults.size != accommodationList.size) {
            accommodationList.clear()
            for (accommodation in aResults)
                accommodationList.add(accommodation)
        }

        val afDB = DatabaseHelper(this, accommodationFeesTbl)
        val afResults = afDB.readData()
        afDB.close()
        accommodationFeesList.clear()
        for (accommodationFees in afResults)
            accommodationFeesList.add(accommodationFees)

        val pDB = DatabaseHelper(this, permissionsTbl)
        val pResults = pDB.readData()
        pDB.close()
        permissionList.clear()
        for (permissions in pResults)
            permissionList.add(permissions)

        val gDB = DatabaseHelper(this, guardianTbl)
        val gResults = gDB.readData()
        gDB.close()
        guardianList.clear()
        for (guardian in gResults)
            guardianList.add(guardian)

        val wDB = DatabaseHelper(this, warrentyTbl)
        val wResults = wDB.readData()
        wDB.close()
        warrentyList.clear()
        for (warrenty in wResults)
            warrentyList.add(warrenty)
    }
}