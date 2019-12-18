package com.example.wisatabandung.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.example.wisatabandung.R
import com.example.wisatabandung.adapter.PagerAdapter
import kotlinx.android.synthetic.main.activity_detail_destination.*
import org.jetbrains.anko.startActivity

class DetailDestination : AppCompatActivity(), View.OnClickListener {


    private lateinit var pagerAdapter : PagerAdapter

    private var username : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_destination)

        username = intent.getStringExtra("username")

        initToolabar()
        initPager()

        btn_buy_ticket.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn_buy_ticket->{
                startActivity<SuccessBuyActivity>(
                    "username" to username
                )
            }
        }
    }

    private fun initToolabar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar_acashmemoreport)
        setSupportActionBar(toolbar)

        // Show menu icon
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        toolbar.setNavigationOnClickListener {
            startActivity<ListDestinationActivity>(
                "username" to username
            )
            finish()
        }
    }

    private fun initPager() {
        pagerAdapter = PagerAdapter(this)
        vp_poster.adapter = pagerAdapter
        ci_poster.setViewPager(vp_poster)
    }

}
