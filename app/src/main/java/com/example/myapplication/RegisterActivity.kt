package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databasehelpers.DatabaseHelper
import com.example.myapplication.databasehelpers.User
import com.example.myapplication.databasehelpers.UserTbl
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity()
{
    private val usersTbl = UserTbl()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        title = getString(R.string.app_name) + " - " + getString(R.string.register_title)

        btnRegister.setOnClickListener {
            try {
                if ((edtUsername.text.isNotEmpty() && edtPassword.text.isNotEmpty())) {
                    if (edtPassword.text.length < 8)
                        showToastShort(getString(R.string.password_must_be_8))
                    else {
                        val user = User(edtUsername.text.toString(), edtPassword.text.toString())

                        val db = DatabaseHelper(this, usersTbl)
                        if (db.insertData(user)) {
                            db.close()
                            val intent = Intent(this, SignInActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(intent)
                            finish()
                        } else {
                            showToastShort(getString(R.string.unable_to_register))
                        }
                    }
                } else
                    showToastShort(getString(R.string.please_complete_the_form))
            } catch (e: Exception) {
                showToastShort(e.message.toString())
            }

        }
    }
}