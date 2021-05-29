package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class RepaymentsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repayments)

        title = getString(R.string.repayments)
    }
}