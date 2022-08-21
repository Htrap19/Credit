package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.myapplication.databasehelpers.*
import kotlinx.android.synthetic.main.activity_repayments.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RepaymentsActivity : AppCompatActivity() {
    private val caseStudyTbl = CaseStudyTbl()
    private val repaymentsTbl = RepaymentsTbl()
    private val universityIds = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repayments)

        title = getString(R.string.repayments)
        loadUniversityIdForAutoComplete()

        val unids = ArrayAdapter(this, R.layout.auto_complete_dropdown_layout, R.id.txtResultName, universityIds)
        edtRepaymentUniversityId.setAdapter(unids)

        edtRepaymentUniversityId.onItemClickListener = AdapterView.OnItemClickListener{
                parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            val db = DatabaseHelper(this, caseStudyTbl)
            val result = db.readData("${CaseStudy.COL_UNIVERSITY_ID} = '${selectedItem}'")
            db.close()
            var fees = result[0].fees
            val db2 = DatabaseHelper(this, repaymentsTbl)
            val paymentList = db2.readData("${Repayments.COL_UNIVERSITY_ID} = '$selectedItem'")
            db2.close()
            for (p in paymentList) {
                fees -= p.paidAmount
            }
            if (fees < 0.0f)
                fees = 0.0f
            edtFeesDetails.text = fees.toString()
        }

        btnRepaymentSave.setOnClickListener {
            if (edtRepaymentUniversityId.text.isNotEmpty() && edtRepaymentAmount.text.isNotEmpty()) {
                if (universityIds.contains(edtRepaymentUniversityId.text.toString())) {
                    val currentDate = Calendar.getInstance().time
                    val simpleDateFormatter = SimpleDateFormat("yyyy-MM-dd")
                    val date = simpleDateFormatter.format(currentDate)
                    val repayments = Repayments(edtRepaymentUniversityId.text.toString().toInt(), edtRepaymentAmount.text.toString().toFloat(), date)
                    val db = DatabaseHelper(this, repaymentsTbl)
                    if (db.insertData(repayments)) {
                        showToastShort(getString(R.string.payment_done))
                        edtRepaymentUniversityId.text.clear()
                        edtFeesDetails.text = getString(R.string.empty_field)
                        edtRepaymentAmount.text.clear()
                    } else {
                        showToastShort(getString(R.string.unable_to_save))
                    }
                } else {
                    showToastShort(getString(R.string.university_id_not_found))
                }
            } else {
                showToastShort(getString(R.string.please_complete_the_form))
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

    private fun loadUniversityIdForAutoComplete() {
        val db = DatabaseHelper(this, caseStudyTbl)
        val results = db.readData()
        db.close()
        if (results.size != universityIds.size) {
            universityIds.clear()
            for (caseStudy in results) {
                universityIds.add(caseStudy.universityId.toString())
            }
        }
    }
}