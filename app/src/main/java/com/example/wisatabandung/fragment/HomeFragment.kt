package com.example.wisatabandung.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wisatabandung.R
import com.example.wisatabandung.activity.DetailDestination
import com.example.wisatabandung.activity.ListDestinationActivity
import com.example.wisatabandung.item.Category
import com.example.wisatabandung.adapter.CategoryAdapter
import com.example.wisatabandung.item.ForYou
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profil.*
import org.jetbrains.anko.support.v4.startActivity
import com.google.firebase.database.DataSnapshot
import kotlinx.android.synthetic.main.for_you_item.view.*


class HomeFragment : Fragment() {

    private lateinit var ref : DatabaseReference
    private lateinit var ref1 : DatabaseReference
    private val categories:MutableList<Category> = mutableListOf()

    private var username : String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.e("gambar",FirebaseDatabase.getInstance().getReference("destination").child("camp").child("gunung_putri").child("photo_1").toString())

        initDataCategory()
        //initDataForYou()
        initDataFirebase()
        ref1 = FirebaseDatabase.getInstance().getReference("destination").child("camp")


        rv_category.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false)
        rv_category.adapter = CategoryAdapter(activity!!.applicationContext,categories){
            startActivity<ListDestinationActivity>(
                "id_category" to it.id,
                "slogan" to it.slogan,
                "username" to username

            )

        }

        //rv_for_you.setHasFixedSize(true)
        rv_for_you.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)

        foryouDataFromFirebase()


    }

    private fun foryouDataFromFirebase() {

        val options = FirebaseRecyclerOptions.Builder<ForYou>()
            .setQuery(ref1,ForYou::class.java)
            .setLifecycleOwner(this)
            .build()

        val firebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<ForYou, forYouViewHolder>(options){
            override fun onCreateViewHolder(p0: ViewGroup, p1: Int): forYouViewHolder {
                return forYouViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.for_you_item, p0, false))
            }

            override fun onBindViewHolder(holder: forYouViewHolder, position: Int, model: ForYou) {
                holder.itemView.tv_for_you.setText(model.name)
                Picasso.get().load(model.photo_1).into(holder.itemView.iv_for_you)
                holder.itemView.setOnClickListener {
                    startActivity<DetailDestination>(
                        "username" to username,
                        "id_category" to "camp",
                        "id_destination" to model.id
                    )
                }

            }

        }

        rv_for_you.adapter = firebaseRecyclerAdapter

    }

    private fun initDataFirebase() {
        val intent = activity!!.intent
            username = intent.getStringExtra("username")

        ref = FirebaseDatabase.getInstance().getReference().child("user").child(username)
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                tv_name_home.setText(p0.child("name").value.toString())
                tv_balance_home.setText(p0.child("balance").value.toString())
                Picasso.get().load(p0.child("url_foto").value.toString()).centerCrop().fit().into(iv_profil_home)

                Log.e("gambar2",p0.child("url_foto").value.toString())
            }

        })

    }

    private fun initDataCategory() {
        val categoryId = resources.getStringArray(R.array.id_category)
        val categoryName = resources.getStringArray(R.array.name_category)
        val categoryImage = resources.obtainTypedArray(R.array.image_category)
        val slogan = resources.getStringArray(R.array.slogan_category)

        categories.clear()
        for (i in categoryName.indices){
            categories.add(Category(categoryId[i]
                ,categoryName[i]
                ,categoryImage.getResourceId(i,0)
                ,slogan[i]))
        }

        categoryImage.recycle()

    }

    class forYouViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}


