package com.example.wisatabandung.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.wisatabandung.R
import com.example.wisatabandung.item.MyTicket
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.myticket_item.view.*

class MyTicketAdapter (private val context: Context, private val tickets : List<MyTicket>, private val listener : (MyTicket)->(Unit))
    : RecyclerView.Adapter<MyTicketViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int)=
        MyTicketViewHolder(LayoutInflater.from(context).inflate(R.layout.myticket_item, p0, false))


    override fun getItemCount(): Int = tickets.size

    override fun onBindViewHolder(p0: MyTicketViewHolder, p1: Int) {
        p0.bindItem(tickets[p1], listener)
    }

}

class MyTicketViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val ivQrTicket : ImageView = view.findViewById(R.id.iv_qr_ticket)

    fun bindItem(items : MyTicket, listener: (MyTicket) -> Unit){
        itemView.tv_name_ticket.text = items.name
        itemView.tv_jumlah_ticket.text = items.ticketValue.toString()
        itemView.tv_tgl_ticket.text = items.date
        items.qr?.let {
            Picasso.get().load(it).into(ivQrTicket)
        }

        itemView.setOnClickListener {
            listener(items)
        }

    }

}
