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
    private val db = DatabaseHelper<User>(this, usersTbl)

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        title = getString(R.string.app_name) + " - " + getString(R.string.register_title)

        btnRegister.setOnClickListener {
            if ((edtUsername.text.isNotEmpty() && edtPassword.text.isNotEmpty())) {
                if (edtPassword.text.length < 8)
                    Toast.makeText(this, getString(R.string.password_must_be_8), Toast.LENGTH_SHORT).show()
                else {
                    val user = User(edtUsername.text.toString(), edtPassword.text.toString())
                    if (db.insertData(user)) {
                        val intent = Intent(this, SignInActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.i("RegisterActivity", "Unable to register!")
                    }
                }
            } else
                Toast.makeText(this, getString(R.string.please_complete_the_form), Toast.LENGTH_SHORT).show()
        }
    }
}