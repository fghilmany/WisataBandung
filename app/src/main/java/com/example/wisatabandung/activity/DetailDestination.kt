package com.example.wisatabandung.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.example.wisatabandung.R
import com.example.wisatabandung.adapter.PagerAdapter
import kotlinx.android.synthetic.main.activity_detail_destination.*

class DetailDestination : AppCompatActivity() {

    private lateinit var pagerAdapter : PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_destination)

        val toolbar: Toolbar = findViewById(R.id.toolbar_acashmemoreport)
        setSupportActionBar(toolbar)

        // Show menu icon
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        toolbar.setNavigationOnClickListener {
            val i = Intent(this, ListDestinationActivity::class.java)
            startActivity(i)
            finish()
        }


        pagerAdapter = PagerAdapter(this)
        vp_poster.adapter = pagerAdapter
        ci_poster.setViewPager(vp_poster)
    }
}
