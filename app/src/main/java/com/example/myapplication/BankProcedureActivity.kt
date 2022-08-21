package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.example.myapplication.databasehelpers.*
import kotlinx.android.synthetic.main.activity_bank_procedure.*

class BankProcedureActivity : AppCompatActivity() {
    private val studentTbl = StudentTbl()
    private val bankProcedureTbl = BankProcedureTbl()
    private val names = ArrayList<String>()
    private val studentList = ArrayList<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_procedure)

        title = getString(R.string.bank_procedure)
        loadNamesForAutoComplete()
        val autoCompleteAdapter = ArrayAdapter(this, R.layout.auto_complete_dropdown_layout, R.id.txtResultName, names)
        edtStudentName.setAdapter(autoCompleteAdapter)

        btnBankProcedureSave.setOnClickListener {
            if (edtStudentName.text.isNotEmpty() && edtBank.text.isNotEmpty() && edtBankType.text.isNotEmpty() && edtAccountNumber.text.isNotEmpty() && edtTotalTuitionFees.text.isNotEmpty() && edtPeriod.text.isNotEmpty() && edtAmountOfRepayment.text.isNotEmpty() && (rbMonth.isChecked || rbYear.isChecked)) {

                val sId = getStudentId(edtStudentName.text.toString())

                val periodType = if (rbMonth.isSelected) 1 else 2
                val bankProcedure = BankProcedure(edtBank.text.toString(), edtBankType.text.toString(), edtAccountNumber.text.toString().toInt(), edtTotalTuitionFees.text.toString().toFloat(), edtPeriod.text.toString().toInt(), periodType, edtAmountOfRepayment.text.toString().toFloat())
                bankProcedure.studentId = sId
                val db = DatabaseHelper(this, bankProcedureTbl)
                if (db.insertData(bankProcedure)) {
                    val intent = Intent(this, DashBoardActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    showToastShort(getString(R.string.unable_to_save))
                }
            } else {
                showToastShort(getString(R.string.please_complete_the_form))
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

    private fun getStudentId(name: String): Int {
        for (s in studentList) {
            if (s.name == edtStudentName.text.toString())
                return s.id
        }

        return -1
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