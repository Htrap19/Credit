package com.example.myapplication

import android.app.Activity
import android.widget.Toast

infix fun Activity.showToastShort(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}