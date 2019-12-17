package com.example.wisatabandung.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.wisatabandung.R
import com.example.wisatabandung.item.ForYou
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.for_you_item.view.*
import java.text.FieldPosition
import com.example.wisatabandung.item.MyTicket




class ForYouAdapter(private val context: Context, private val forYou : List<ForYou>, private val listener : (ForYou)->(Unit))
    :RecyclerView.Adapter<ForYouViewPager>(){


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int)=
        ForYouViewPager(LayoutInflater.from(context).inflate(R.layout.for_you_item, p0, false))

    override fun getItemCount(): Int = forYou.size

    override fun onBindViewHolder(holder: ForYouViewPager, position: Int) {
        holder.bindItem(forYou[position],listener)
    }
}

class ForYouViewPager(view: View):RecyclerView.ViewHolder(view) {

    private val forYouImage : ImageView = view.findViewById(R.id.iv_for_you)

    fun bindItem(items:ForYou, listener: (ForYou) -> Unit){
        itemView.tv_for_you.text =  items.name
        items.photo_1?.let {
            Picasso.get().load(it).into(forYouImage)
        }

        itemView.setOnClickListener{
            listener(items)
        }

    }

}
