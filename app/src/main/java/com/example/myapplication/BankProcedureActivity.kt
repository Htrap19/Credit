package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class BankProcedureActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_procedure)

        title = getString(R.string.bank_procedure)
    }
}