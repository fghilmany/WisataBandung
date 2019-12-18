package com.example.wisatabandung.adapter

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import android.view.LayoutInflater
import android.support.v4.view.PagerAdapter
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.example.wisatabandung.R
import com.example.wisatabandung.fragment.ProfilFragment
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import org.jetbrains.anko.intentFor
import android.app.Activity





class PagerAdapter(private val context: Context) : PagerAdapter() {

    private lateinit var ref : DatabaseReference
    private var id_destination : String = ""
    private var id_category : String = ""
    /*
        This callback is responsible for creating a page. We inflate the layout and set the drawable
        to the ImageView based on the position. In the end we add the inflated layout to the parent
        container .This method returns an object key to identify the page view, but in this example page view
        itself acts as the object key
        */
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.pager_item, null)
        val imageView : ImageView = view.findViewById(R.id.iv_pager)
        getImageAt(position)
        val intent = (context as Activity).intent
        id_destination = intent.getStringExtra("id_destination")
        id_category = intent.getStringExtra("id_category")
        ref = FirebaseDatabase.getInstance().getReference("destination").child(id_category)
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(context,"db bermasalah",Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                Log.e("pica",p0.child(getImageAt(position)).value.toString())
                Log.e("pica1",Picasso.get().load(p0.child("gunung_putri").child(getImageAt(position)).value.toString()).into(imageView).toString())
                val a = p0.child(id_destination).child(getImageAt(position))
                Picasso.get().load(a.value.toString()).centerCrop().fit().into(imageView)
            }

        })

        /*imageView.setImageDrawable(context.resources.getDrawable(getImageAt(position)))*/
        container.addView(view)
        return view
    }

    /*
        This callback is responsible for destroying a page. Since we are using view only as the
        object key we just directly remove the view from parent container
        */
    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }

    /*
        Returns the count of the total pages
        */
    override fun getCount(): Int {
        return 3
    }

    /*
        Used to determine whether the page view is associated with object key returned by instantiateItem.
        Since here view only is the key we return view==object
        */
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return `object` === view
    }

    private fun getImageAt(position: Int): String {


        when (position) {
            0 -> return "photo_1"
            1 -> return "photo_2"
            else -> return "photo_3"
        }
    }
}