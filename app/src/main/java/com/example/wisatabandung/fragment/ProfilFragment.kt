package com.example.wisatabandung.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wisatabandung.R
import com.example.wisatabandung.activity.LoginActivity
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profil.*
import org.jetbrains.anko.support.v4.startActivity
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.ContextThemeWrapper
import com.example.wisatabandung.activity.MainActivity
import kotlinx.android.synthetic.main.activity_detail_destination.view.*
import kotlinx.android.synthetic.main.dialog_top_up.*
import org.jetbrains.anko.db.INTEGER


class ProfilFragment : Fragment(), View.OnClickListener {

    private lateinit var ref : DatabaseReference

    private var username : String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDataFirebase()

        tv_logout.setOnClickListener(this)
        btn_top_up.setOnClickListener(this)


    }

    private fun initDataFirebase() {
        val intent = activity!!.intent
        username = intent.getStringExtra("username")

        ref = FirebaseDatabase.getInstance().getReference().child("user").child(username)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                tv_profil_name.setText(p0.child("name").value.toString())
                tv_balance_profil.setText(p0.child("balance").value.toString())
                tv_email_profil.setText(p0.child("email").value.toString())
                tv_username_profil.setText(p0.child("username").value.toString())
                Picasso.get().load(p0.child("url_foto").value.toString()).centerCrop().fit().into(iv_profil)
            }

        })
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.tv_logout -> {
                val intent = Intent(activity!!.applicationContext, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                activity!!.finish()
            }

            R.id.btn_top_up->{

                val mDialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_top_up,null)
                val mBuilder = AlertDialog.Builder(ContextThemeWrapper(activity,R.style.myDialog))
                    .setView(mDialogView)
                val mAlertDialog = mBuilder.show()

                mAlertDialog.btn_yes_topup.setOnClickListener{
                    mAlertDialog.dismiss()
                    ref = FirebaseDatabase.getInstance().getReference("user").child(username)
                    ref.addListenerForSingleValueEvent(object:ValueEventListener{
                        override fun onCancelled(p0: DatabaseError) {

                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            val valueTopUp = mAlertDialog.edt_top_up.text.toString().toInt()
                            val valueBalance = p0.child("balance").value.toString().toInt()


                            Log.e("topup",valueBalance.toString())


                            p0.ref.child("balance").setValue(valueBalance+valueTopUp)
                        }

                    })
                }

                mAlertDialog.btn_cancel_topup.setOnClickListener{
                    mAlertDialog.dismiss()
                }





            }
        }
    }


}
