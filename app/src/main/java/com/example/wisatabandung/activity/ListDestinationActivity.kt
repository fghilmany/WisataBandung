package com.example.wisatabandung.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.wisatabandung.R
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wisatabandung.item.Destination
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_list_destination.*
import kotlinx.android.synthetic.main.destination_item.view.*
import kotlinx.android.synthetic.main.for_you_item.view.*
import org.jetbrains.anko.startActivity


class ListDestinationActivity : AppCompatActivity() {

    private lateinit var ref : DatabaseReference

    private var id_destination : String = ""
    private var username : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_destination)

        addToolabar()

        username = intent.getStringExtra("username")
        id_destination = intent.getStringExtra("id_destination")

        ref = FirebaseDatabase.getInstance().getReference("destination").child(id_destination)
        //rv_list_destination.setHasFixedSize(true)
        rv_list_destination.layoutManager = LinearLayoutManager(this)

        initDataFirebase()
        }

    private fun initDataFirebase() {
        val options = FirebaseRecyclerOptions.Builder<Destination>()
            .setQuery(ref, Destination::class.java)
            .setLifecycleOwner(this)
            .build()

        val firebaseRecyclerAdapter = object:FirebaseRecyclerAdapter<Destination, destinationViewHolder>(options){
            override fun onCreateViewHolder(p0: ViewGroup, p1: Int): destinationViewHolder {
                return destinationViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.destination_item,p0,false))
            }

            override fun onBindViewHolder(holder: destinationViewHolder, position: Int, model: Destination) {
                holder.itemView.tv_name_destination.setText(model.name)
                holder.itemView.tv_address_destination.setText(model.address)
                holder.itemView.tv_destination_price.setText(model.price.toString())
                Picasso.get().load(model.photo_1).into(holder.itemView.iv_destination_poster)
                holder.itemView.setOnClickListener{
                    startActivity<DetailDestination>()
                }
            }

        }

        rv_list_destination.adapter = firebaseRecyclerAdapter
    }

    private fun addToolabar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar_acashmemoreport)
        setSupportActionBar(toolbar)

        // Show menu icon
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        toolbar.setNavigationOnClickListener {
            startActivity<HomeActivity>(
                "username" to username
            )
            finish()
        }
    }
}

class destinationViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
