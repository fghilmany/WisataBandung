package com.example.wisatabandung.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.example.wisatabandung.R
import com.example.wisatabandung.adapter.PagerAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detail_destination.*
import kotlinx.android.synthetic.main.dialog_buy_ticket.view.*
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent
import android.net.Uri


class DetailDestination : AppCompatActivity(), View.OnClickListener {


    private lateinit var pagerAdapter : PagerAdapter
    private lateinit var ref : DatabaseReference

    private var username : String = ""
    private var id_destination : String = ""
    private var id_category : String = ""
    private var name_destination : String = ""
    private var name_user : String = ""

    private var totalTicket : Int = 0
    private var userBalance : Long = 10
    private var totalCost : Long = 0
    private var ticketPrice : Long = 0

    private var bookDate : String = ""
    private var expiredDate : String = ""

    private var noIdTransaction : Long = 0
    private var idTransaction : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_destination)

        username = intent.getStringExtra("username")
        initToolabar()
        initPager()
        initDataFirebase()


        tv_total_detail.setText(totalTicket.toString())


        btn_buy_ticket.setOnClickListener(this)
        btn_min.setOnClickListener(this)
        btn_plus.setOnClickListener(this)
        fab_direction_location.setOnClickListener(this)

        btn_min.animate().alpha(0F).setDuration(300).start()
        btn_min.isEnabled = false
        btn_buy_ticket.isEnabled = false
        btn_buy_ticket.setBackgroundResource(R.drawable.btn_cancel)

    }

    private fun initDataFirebase() {
        val intent = intent
        id_destination = intent.getStringExtra("id_destination")
        id_category = intent.getStringExtra("id_category")


        ref = FirebaseDatabase.getInstance().getReference()
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val getDataDestination = p0.child("destination").child(id_category).child(id_destination)
                val getDataUser = p0.child("user").child(username)

                tv_name_destination.setText(getDataDestination.child("name").value.toString())
                tv_price_detail.setText(getDataDestination.child("price").value.toString())
                tv_detail_destination.setText(getDataDestination.child("description").value.toString())

                name_destination = getDataDestination.child("name").value.toString()
                name_user = getDataUser.child("name").value.toString()
                userBalance = getDataUser.child("balance").value as Long
                ticketPrice = getDataDestination.child("price").value as Long
                totalCost = ticketPrice * totalTicket

                Log.e("tot",totalCost.toString())
                tv_total_cost.setText("$totalCost")

            }

        })
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn_buy_ticket->{

                bookAndExpDate()


                val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_buy_ticket,null)
                val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogView)
                val mAlertDialog = mBuilder.show()

                mDialogView.tv_destination_dialog.setText(name_destination)
                mDialogView.tv_name_dialog.setText(name_user)
                mDialogView.tv_total.setText("$totalCost")
                mDialogView.tv_total_order.setText("$totalTicket")
                mDialogView.tv_expired.setText("$expiredDate")
                mDialogView.tv_date_order.setText("$bookDate")

                mDialogView.btn_buy.setOnClickListener {
                    mAlertDialog.dismiss()
                    userBalance = userBalance - totalCost
                    ref = FirebaseDatabase.getInstance().getReference()
                    ref.addListenerForSingleValueEvent(object : ValueEventListener{
                        override fun onCancelled(p0: DatabaseError) {

                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            val setBalance = p0.child("user").child(username).child("balance")
                            val setTransaction = p0.child("transaction").child(username)

                            setBalance.ref.setValue(userBalance)

                            noIdTransaction = setTransaction.childrenCount
                            Log.e("id",noIdTransaction.toString())
                            noIdTransaction = noIdTransaction + 1
                            idTransaction = noIdTransaction.toString()+username+id_category+id_destination+bookDate+expiredDate
                            Log.e("id_transaksi",idTransaction)

                            setTransaction.ref.child(idTransaction).child("transaction_id").setValue(idTransaction)
                            setTransaction.ref.child(idTransaction).child("date").setValue(bookDate)
                            setTransaction.ref.child(idTransaction).child("date_exp").setValue(expiredDate)
                            setTransaction.ref.child(idTransaction).child("purchased_ticket").setValue(totalTicket)
                            setTransaction.ref.child(idTransaction).child("name_destination").setValue(name_destination)
                            setTransaction.ref.child(idTransaction).child("total_pay").setValue(totalCost)

                        }

                    })
                    Log.e("sisbal", userBalance.toString())
                    startActivity<SuccessBuyActivity>(
                        "username" to username

                    )
                }

                mDialogView.btn_cancel.setOnClickListener{
                    mAlertDialog.dismiss()
                }

            }

            R.id.btn_min -> {
                totalTicket-=1
                tv_total_detail.setText(totalTicket.toString())
                if (totalTicket < 2){
                    btn_min.animate().alpha(0F).setDuration(300).start()
                    btn_min.isEnabled = false
                }
                totalCost = ticketPrice * totalTicket
                tv_total_cost.setText("$totalCost")

                if (totalCost <= userBalance){
                    btn_plus.animate().translationY(0F).alpha(1F).setDuration(350).start()
                    btn_plus.isEnabled = true

                    btn_buy_ticket.isEnabled = true
                    btn_buy_ticket.setBackgroundResource(R.drawable.bg_button_green_primary)

                }

            }

            R.id.btn_plus ->{
                totalTicket+=1
                tv_total_detail.setText(totalTicket.toString())
                if (totalTicket > 1){
                    btn_min.animate().alpha(1F).setDuration(300).start()
                    btn_min.isEnabled = true
                }
                totalCost = ticketPrice * totalTicket
                tv_total_cost.setText("$totalCost")

                if (totalCost > userBalance){
                    btn_plus.animate().alpha(0F).setDuration(300).start()
                    btn_plus.isEnabled = false

                    btn_buy_ticket.isEnabled = false
                    btn_buy_ticket.setBackgroundResource(R.drawable.btn_cancel)


                }else if (totalCost <= userBalance){
                    btn_plus.animate().translationY(0F).alpha(1F).setDuration(350).start()
                    btn_plus.isEnabled = true

                    btn_buy_ticket.isEnabled = true
                    btn_buy_ticket.setBackgroundResource(R.drawable.bg_button_green_primary)

                }

            }

            R.id.fab_direction_location->{
                ref = FirebaseDatabase.getInstance().getReference("destination").child(id_category).child(id_destination)
                ref.addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(p0.child("url_loc").value.toString())
                        )
                        startActivity(intent)
                    }

                })
                /*val intent = Intent(
                    android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345")
                )
                startActivity(intent)*/
            }
        }
    }

    private fun bookAndExpDate() {
        val date = Date()
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        val answer : String = formatter.format(date)

        Log.e("jam",answer)
        bookDate = answer

        val c = Calendar.getInstance()
        val cdt = "18-12-2019"
        val date2 = formatter.parse(cdt)
        c.time = date2
        c.add(Calendar.DAY_OF_MONTH, 7)
        val dateExp = formatter.format(c.time)


        expiredDate = dateExp.toString()

        Log.e("jam1",expiredDate)
    }

    private fun initToolabar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar_acashmemoreport)
        setSupportActionBar(toolbar)

        // Show menu icon
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        toolbar.setNavigationOnClickListener {
            startActivity<ListDestinationActivity>(
                "username" to username,
                "id_category" to id_category
            )
            finish()
        }
    }

    private fun initPager() {
        pagerAdapter = PagerAdapter(this)
        vp_poster.adapter = pagerAdapter
        ci_poster.setViewPager(vp_poster)
    }

}
