package com.example.wisatabandung.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.wisatabandung.R
import com.example.wisatabandung.item.Destination
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.destination_item.view.*

class DestinationAdapter (private val context: Context, private val destinations:List<Destination>,private val listener : (Destination)->(Unit))
    : RecyclerView.Adapter<DestinationViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) = DestinationViewHolder(LayoutInflater.from(context).inflate(R.layout.destination_item,p0,false))


    override fun getItemCount(): Int= destinations.size

    override fun onBindViewHolder(p0: DestinationViewHolder, p1: Int) {
        p0.bindItem(destinations[p1],listener)
    }

}

class DestinationViewHolder(view: View):RecyclerView.ViewHolder(view) {

    private val poster : ImageView = view.findViewById(R.id.iv_destination_poster)

    fun bindItem(items:Destination,listener: (Destination) -> Unit){
        itemView.tv_name_destination.text = items.name
        itemView.tv_address_destination.text = items.address
        itemView.tv_destination_price.text = items.price.toString()
        items.image?.let {
            Picasso.get().load(it).into(poster)
        }

        itemView.setOnClickListener {
            listener(items)
        }

    }

}
