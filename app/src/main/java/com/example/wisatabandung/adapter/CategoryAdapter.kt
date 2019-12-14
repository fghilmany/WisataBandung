package com.example.wisatabandung.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.wisatabandung.R
import com.example.wisatabandung.item.Category
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.category_item.view.*

class CategoryAdapter(private val context: Context, private val categories : List<Category>, private val listener : (Category)->(Unit))
    : RecyclerView.Adapter<CategoryViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.category_item, parent, false))


    override fun getItemCount(): Int = categories.size


    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bindItem(categories[position], listener)
    }

}

class CategoryViewHolder(view: View):RecyclerView.ViewHolder(view) {

    private val categoryImage : ImageView = view.findViewById(R.id.iv_category)

    fun bindItem (items:Category,listener : (Category)->(Unit)){
        itemView.tv_category.text = items.name
        items.image?.let {
            Picasso.get().load(it).into(categoryImage)
        }

        itemView.setOnClickListener{
            listener(items)
        }
    }

}
