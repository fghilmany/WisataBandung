package com.example.wisatabandung.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import com.example.wisatabandung.R
import com.example.wisatabandung.item.MyTicket
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.google.zxing.qrcode.encoder.QRCode
import kotlinx.android.synthetic.main.dialog_refund_ticket.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_my_ticket.*
import kotlinx.android.synthetic.main.myticket_item.*
import kotlinx.android.synthetic.main.myticket_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class MyTicketFragment : Fragment() {

    private val ticket:MutableList<MyTicket> = mutableListOf()
    private lateinit var ref:DatabaseReference

    private var username : String = ""
    private var id_transaction : String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_ticket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_my_ticket.layoutManager = LinearLayoutManager(activity)

        val intent = activity!!.intent
        username = intent.getStringExtra("username")

        ref = FirebaseDatabase.getInstance().getReference("transaction").child(username)

        initDataTicket()


    }

    private fun initDataTicket() {
        val options = FirebaseRecyclerOptions.Builder<MyTicket>()
            .setQuery(ref, MyTicket::class.java)
            .setLifecycleOwner(this)
            .build()

        val firebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<MyTicket, myTicketViewHolder>(options){
            override fun onCreateViewHolder(p0: ViewGroup, p1: Int): myTicketViewHolder {
                return myTicketViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.myticket_item,p0,false))
            }

            override fun onBindViewHolder(holder: myTicketViewHolder, position: Int, model: MyTicket) {

                //set qr code image
                id_transaction = model.transaction_id.toString()
                val qrCode = QRGEncoder(id_transaction, null, QRGContents.Type.TEXT, 300)
                val bitmap = qrCode.encodeAsBitmap()
                holder.itemView.iv_qr_ticket.setImageBitmap(bitmap)

                holder.itemView.tv_name_ticket.setText(model.name_destination)
                holder.itemView.tv_jumlah_ticket.setText(model.purchased_ticket.toString())
                holder.itemView.tv_tgl_ticket.setText("exp. "+model.date_exp)
                holder.itemView.setOnClickListener {
                    Toast.makeText(activity,model.name_destination,Toast.LENGTH_SHORT).show()
                }

                //delete ticket
                val date = Date()
                val formatter = SimpleDateFormat("dd-MM-yyyy")
                val answer : String = formatter.format(date)

                if (answer == model.date_exp){
                    ref.child(model.transaction_id.toString()).removeValue()
                }

                //refund ticket
                holder.itemView.btn_refund.setOnClickListener {
                    val mDialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_refund_ticket,null)
                    val mBuilder = AlertDialog.Builder(ContextThemeWrapper(activity,R.style.myDialog))
                        .setView(mDialogView)
                    val mAlertDialog = mBuilder.show()
                    mAlertDialog.btn_yes.setOnClickListener {
                        mAlertDialog.dismiss()
                        ref.child(model.transaction_id.toString()).removeValue()
                        ref = FirebaseDatabase.getInstance().getReference("user").child(username)
                        ref.addListenerForSingleValueEvent(object : ValueEventListener{
                            override fun onCancelled(p0: DatabaseError) {

                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                val balanceNow = p0.child("balance").value as Long
                                val balanceUpdate = balanceNow + model.total_pay as Long

                                Log.e("baup",balanceUpdate.toString())

                                p0.child("balance").ref.setValue(balanceUpdate)

                            }

                        })
                    }

                    mAlertDialog.btn_no.setOnClickListener {
                        mAlertDialog.dismiss()

                    }

                }
            }

        }

        rv_my_ticket.adapter = firebaseRecyclerAdapter

    }


}

class myTicketViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
