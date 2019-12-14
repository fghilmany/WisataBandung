package com.example.wisatabandung.fragment


import android.media.Image
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.wisatabandung.R
import com.example.wisatabandung.item.Category
import com.example.wisatabandung.adapter.CategoryAdapter
import com.example.wisatabandung.adapter.ForYouAdapter
import com.example.wisatabandung.item.ForYou
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDataCategory()
        initDataForYou()

        rv_category.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false)
        rv_category.adapter = CategoryAdapter(activity!!.applicationContext,categories){
            Toast.makeText(activity,it.id,Toast.LENGTH_SHORT).show()

        }

        rv_for_you.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        rv_for_you.adapter = ForYouAdapter(activity!!.applicationContext,forYou){
            Toast.makeText(activity,"Bandung",Toast.LENGTH_SHORT).show()
        }
    }

    private val categories:MutableList<Category> = mutableListOf()
    private val forYou:MutableList<ForYou> = mutableListOf()

    private fun initDataCategory() {
        val categoryId = resources.getStringArray(R.array.id_category)
        val categoryName = resources.getStringArray(R.array.name_category)
        val categoryImage = resources.obtainTypedArray(R.array.image_category)

        categories.clear()
        for (i in categoryName.indices){
            categories.add(Category(categoryId[i]
                ,categoryName[i]
                ,categoryImage.getResourceId(i,0)))
        }

        categoryImage.recycle()

    }

    private fun initDataForYou() {
        forYou.clear()
        forYou.add(ForYou("1", R.drawable.gunung_putri, "Gunung Putri Lembang"))
        forYou.add(ForYou("1", R.drawable.gunung_putri, "Gunung Putri Lembang"))
    }


}
