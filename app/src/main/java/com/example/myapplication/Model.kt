package com.example.myapplication

import android.content.Intent

class Model(val logo: Int, val title: String, val onClickCallback: () -> Intent) {
}