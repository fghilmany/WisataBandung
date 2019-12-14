package com.example.wisatabandung.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.wisatabandung.R
import android.support.v7.widget.Toolbar
import android.widget.Toast
import com.example.wisatabandung.adapter.DestinationAdapter
import com.example.wisatabandung.item.Destination
import kotlinx.android.synthetic.main.activity_list_destination.*
import org.jetbrains.anko.startActivity


class ListDestinationActivity : AppCompatActivity() {

    private val destinations:MutableList<Destination> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_destination)

        val toolbar:Toolbar = findViewById(R.id.toolbar_acashmemoreport)
        setSupportActionBar(toolbar)

/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.colorPrimaryDark)
        }*/

        // Show menu icon
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        toolbar.setNavigationOnClickListener {
            val i = Intent(this, HomeActivity::class.java)
            startActivity(i)
            finish()
        }

        initDataDestination()

        rv_list_destination.layoutManager = LinearLayoutManager(this)
        rv_list_destination.adapter = DestinationAdapter(this, destinations){
            Toast.makeText(this,"Bandung", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initDataDestination() {
        destinations.clear()
        destinations.add(Destination("1",R.drawable.gunung_putri,"Gunung Putri","Jl. Gunung Putri Lembang, Kabupaten Bandung Barat, Jawa Barat 40391",20000))
        destinations.add(Destination("1",R.drawable.gunung_putri,"Gunung Putri","Jl. Gunung Putri Lembang, Kabupaten Bandung Barat, Jawa Barat 40391",20000))
        destinations.add(Destination("1",R.drawable.gunung_putri,"Gunung Putri","Jl. Gunung Putri Lembang, Kabupaten Bandung Barat, Jawa Barat 40391",20000))
    }
}
