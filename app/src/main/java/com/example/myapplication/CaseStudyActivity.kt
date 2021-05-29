package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myapplication.databasehelpers.Guardian
import com.example.myapplication.databasehelpers.Student
import kotlinx.android.synthetic.main.activity_case_study.*

class CaseStudyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_case_study)

        title = getString(R.string.case_study)
        val studentObj = intent.getSerializableExtra("studentObj") as Student
        val guardianObj = intent.getSerializableExtra("guardianObj") as Guardian
        Log.i("Student", studentObj.name)
        Log.i("Guardian", guardianObj.name)

        btnNewAdditionSave.setOnClickListener {
            Toast.makeText(this, "New student added!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, DashBoardActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
    }
}