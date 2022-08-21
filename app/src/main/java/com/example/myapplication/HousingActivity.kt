package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_housing.*

class HousingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_housing)
        title = getString(R.string.housing)

        val housingCardViews = ArrayList<Model>()
        housingCardViews.add(Model(R.drawable.ic_baseline_add_circle_24, getString(R.string.new_registration)) {
            return@Model Intent(this, HousingRegistrationActivity::class.java)
        })
        housingCardViews.add(Model(R.drawable.ic_baseline_monetization_on_24, getString(R.string.residential_fees)) {
            return@Model Intent(this, HousingFeeActivity::class.java)
        })
        housingCardViews.add(Model(R.drawable.ic_baseline_rule_24, getString(R.string.permissions)) {
            return@Model Intent(this, PermissionsActivity::class.java)
        })
        housingCardViews.add(Model(R.drawable.ic_baseline_article_24, getString(R.string.warranty)) {
            return@Model Intent(this, WarrentyActivity::class.java)
        })
        housingCardViews.add(Model(R.drawable.ic_baseline_receipt_long_24, getString(R.string.reports)) {
            return@Model Intent(this, HousingReportsActivity::class.java)
        })

        val housingAdapter = DashboardAdapter(housingCardViews, this)
        rcvHousing.layoutManager = GridLayoutManager(this, 2)
        rcvHousing.adapter = housingAdapter
    }
}