package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.action_bar_layout.*
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashBoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.action_bar_layout)

        val userName = intent.getStringExtra("username")
        txtGreeting.text = getString(R.string.greeting) + " " + userName

        val cardViews = ArrayList<Model>()
        cardViews.add(Model(R.drawable.ic_baseline_add_circle_24, getString(R.string.new_addition)) {
            return@Model Intent(this, NewAddition::class.java)
        })
        cardViews.add(Model(R.drawable.ic_baseline_house_24, getString(R.string.housing)) {
            return@Model Intent(this, HousingActivity::class.java)
        })
        cardViews.add(Model(R.drawable.ic_baseline_credit_card_24, getString(R.string.bank_procedure)) {
            return@Model Intent(this, BankProcedureActivity::class.java)
        })
        cardViews.add(Model(R.drawable.ic_baseline_fact_check_24, getString(R.string.repayments)) {
            return@Model Intent(this, RepaymentsActivity::class.java)
        })
        cardViews.add(Model(R.drawable.ic_baseline_receipt_long_24, getString(R.string.reports)) {
            return@Model Intent(this, ReportsActivity::class.java)
        })
        cardViews.add(Model(R.drawable.ic_baseline_timeline_24, getString(R.string.student_record)) {
            return@Model Intent(this, StudentRecordActivity::class.java)
        })
        cardViews.add(Model(R.drawable.ic_baseline_person_search_24, getString(R.string.find)) {
            return@Model Intent(this, ResearchActivity::class.java)
        })

        val dashboardAdapter = DashboardAdapter(cardViews, this)
        rcvDashBoard.layoutManager = GridLayoutManager(this, 2)
        rcvDashBoard.adapter = dashboardAdapter

        btnSignOut.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        btnSignOut.performClick()
    }
}