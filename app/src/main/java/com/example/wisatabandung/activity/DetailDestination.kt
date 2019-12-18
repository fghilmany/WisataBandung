package com.example.wisatabandung.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.wisatabandung.R
import com.example.wisatabandung.adapter.PagerAdapter
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_destination.*
import org.jetbrains.anko.startActivity

class DetailDestination : AppCompatActivity(), View.OnClickListener {


    private lateinit var pagerAdapter : PagerAdapter
    private lateinit var ref : DatabaseReference

    private var username : String = ""
    private var id_destination : String = ""
    private var id_category : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_destination)

        username = intent.getStringExtra("username")
        initToolabar()
        initPager()
        initDataFirebase()

        btn_buy_ticket.setOnClickListener(this)


    }

    private fun initDataFirebase() {
        val intent = intent
        id_destination = intent.getStringExtra("id_destination")
        id_category = intent.getStringExtra("id_category")
        Log.e("id ca",id_category)
        Log.e("id de",id_destination)

        ref = FirebaseDatabase.getInstance().getReference().child("destination").child(id_category).child(id_destination)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                tv_name_destination.setText(p0.child("name").value.toString())
                tv_price_detail.setText(p0.child("price").value.toString())
                tv_detail_destination.setText(p0.child("description").value.toString())

            }

        })
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
