package com.example.wisatabandung.fragment


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

        initData()

        rv_category.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false)
        rv_category.adapter = CategoryAdapter(activity!!.applicationContext,categories){
            Toast.makeText(activity,it.id,Toast.LENGTH_SHORT).show()

        }
    }

    private val categories:MutableList<Category> = mutableListOf()

    private fun initData() {
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


}
