package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databasehelpers.Guardian
import com.example.myapplication.databasehelpers.Student
import kotlinx.android.synthetic.main.activity_guardian.*

class GuardianActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guardian)

        title = getString(R.string.guardian)

        var studentObj = intent.getSerializableExtra("studentObj") as Student

        btnGuardianNext.setOnClickListener {
            if (edtGuardianName.text.isNotEmpty()  && edtGuardianAddress.text.isNotEmpty() && edtGuardianPhoneNumber.text.isNotEmpty() && edtGuardianIdNumber.text.isNotEmpty() && edtGuardianIdType.text.isNotEmpty() && edtOccupation.text.isNotEmpty() && edtMonthlyIncome.text.isNotEmpty() && (rbFather.isChecked || rbMother.isChecked || rbBrother.isChecked || rbSister.isChecked)) {
                val adjective = if (rbFather.isChecked) 1 else if (rbMother.isChecked) 2 else if (rbBrother.isChecked) 3 else 4
                val guardian = Guardian(edtGuardianName.text.toString(), adjective, edtGuardianPhoneNumber.text.toString().toInt(), edtOccupation.text.toString(), edtMonthlyIncome.text.toString().toInt(), edtGuardianAddress.text.toString(), edtGuardianIdNumber.text.toString(), edtGuardianIdType.text.toString())
                val intent = Intent(this, CaseStudyActivity::class.java)
                intent.putExtra("studentObj", studentObj)
                intent.putExtra("guardianObj", guardian)
                startActivity(intent)
                finish()
            }
        }
    }
}