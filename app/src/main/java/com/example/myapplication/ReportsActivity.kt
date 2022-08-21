package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.myapplication.databasehelpers.CaseStudyTbl
import com.example.myapplication.databasehelpers.DatabaseHelper
import com.example.myapplication.databasehelpers.Repayments
import com.example.myapplication.databasehelpers.RepaymentsTbl
import kotlinx.android.synthetic.main.activity_reports.*

class ReportsActivity : AppCompatActivity() {
    private val repaymentsTbl = RepaymentsTbl()
    private val universityIds = ArrayList<String>()
    private val caseStudyTbl = CaseStudyTbl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reports)

        title = getString(R.string.reports)
        loadUniversityIds()

        val arrayList = ArrayAdapter(this, R.layout.auto_complete_dropdown_layout, R.id.txtResultName, universityIds)
        edtReportsUniversityId.setAdapter(arrayList)

        edtReportsUniversityId.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            val db = DatabaseHelper(this, repaymentsTbl)
            val result = db.readData("${Repayments.COL_UNIVERSITY_ID} = '${selectedItem}'")
            db.close()

            edtReportsPaymentDetails.text.clear()
            for (detail in result) {
                edtReportsPaymentDetails.append("Amount: ${detail.paidAmount} Date: ${detail.paidDate} \n")
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        loadUniversityIds()
    }

    private fun loadUniversityIds() {
        val db = DatabaseHelper(this, caseStudyTbl)
        val result = db.readData()
        db.close()
        if (universityIds.size != result.size) {
            universityIds.clear()
            for (id in result) {
                universityIds.add(id.universityId.toString())
            }
        }
    }
}