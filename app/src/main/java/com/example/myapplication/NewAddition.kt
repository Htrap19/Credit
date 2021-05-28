package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_new_addition.*

class NewAddition : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_addition)

        title = getString(R.string.new_addition)

        btnNewAdditionNext.setOnClickListener {
            val intent = Intent(this, GuardianActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}