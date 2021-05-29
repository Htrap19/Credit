package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myapplication.databasehelpers.DatabaseHelper
import com.example.myapplication.databasehelpers.Student
import com.example.myapplication.databasehelpers.StudentTbl
import kotlinx.android.synthetic.main.activity_new_addition.*

class NewAddition : AppCompatActivity() {
    private val studentsTbl = StudentTbl()
    private val db = DatabaseHelper(this, studentsTbl)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_addition)

        title = getString(R.string.new_addition)

        btnNewAdditionNext.setOnClickListener {
            if (edtName.text.isNotEmpty() && edtDateOfBirth.text.isNotEmpty() && edtAddress.text.isNotEmpty() && edtPhoneNumber.text.isNotEmpty() && edtIdNumber.text.isNotEmpty() && edtIdType.text.isNotEmpty() && (rbMale.isChecked || rbFemale.isChecked)) {
                val gender = if (rbMale.isChecked) 1 else 0
                val student = Student(edtName.text.toString(), gender, edtDateOfBirth.text.toString(), edtPhoneNumber.text.toString().toInt(), edtAddress.text.toString(), edtIdNumber.text.toString(), edtIdType.text.toString())
                val intent = Intent(this, GuardianActivity::class.java)
                intent.putExtra("studentObj", student)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, getString(R.string.please_complete_the_form), Toast.LENGTH_SHORT).show()
            }
        }
    }
}