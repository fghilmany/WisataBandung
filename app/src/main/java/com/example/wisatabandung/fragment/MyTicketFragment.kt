package com.example.wisatabandung.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.wisatabandung.R
import com.example.wisatabandung.adapter.MyTicketAdapter
import com.example.wisatabandung.item.MyTicket
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_my_ticket.*

class MyTicketFragment : Fragment() {

    private val ticket:MutableList<MyTicket> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_ticket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDataTicket()

        rv_my_ticket.layoutManager = LinearLayoutManager(activity)
        rv_my_ticket.adapter = MyTicketAdapter(activity!!.applicationContext,ticket){
            Toast.makeText(activity,it.id, Toast.LENGTH_SHORT).show()

        }

    }

    private fun initDataTicket() {
        ticket.clear()
        ticket.add(MyTicket("1",R.drawable.house,"Gunung Putri Lembang",10,"11/01/2020"))
        ticket.add(MyTicket("1",R.drawable.house,"Gunung Putri Lembang",10,"11/01/2020"))
        ticket.add(MyTicket("1",R.drawable.house,"Gunung Putri Lembang",10,"11/01/2020"))
    }


}
