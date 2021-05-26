package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class NewAddition : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_addition)

        title = getString(R.string.new_addition)
    }
}