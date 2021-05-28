package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_guardian.*

class GuardianActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guardian)

        title = getString(R.string.guardian)

        btnGuardianNext.setOnClickListener {
            val intent = Intent(this, CaseStudyActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}