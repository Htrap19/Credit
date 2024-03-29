package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databasehelpers.DatabaseHelper
import com.example.myapplication.databasehelpers.UserTbl
import kotlinx.android.synthetic.main.activity_signin.*
import kotlin.system.exitProcess

class SignInActivity : AppCompatActivity()
{
    private val userTbl = UserTbl()
    private var exit: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        exit = false

        title = getString(R.string.app_name) + " - " + getString(R.string.signin_title)

        btnSignIn.setOnClickListener {
            if (edtUsername.text.isNotEmpty() && edtUsername.text.isNotEmpty()) {
                val db = DatabaseHelper(this, userTbl)
                val user = db.readData()[0]
                db.close()
                val userName = edtUsername.text.toString()
                val passWord = edtPassword.text.toString()
                if (user.username == userName && user.password == passWord) {
                    val intent = Intent(this, DashBoardActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    intent.putExtra("username", userName)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, getString(R.string.wrong_access), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        exit = false
    }

    override fun onResume() {
        super.onResume()
        exit = false
    }

    override fun onBackPressed() {
        if (!exit) {
            Toast.makeText(this, getString(R.string.exit_disclaimer), Toast.LENGTH_SHORT).show()
            exit = true
            return
        }
        finish()
        exitProcess(0)
    }
}