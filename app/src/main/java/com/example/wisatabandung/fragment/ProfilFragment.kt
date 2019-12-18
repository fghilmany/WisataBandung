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
                startActivity<LoginActivity>()
                activity!!.finish()
            }
        }
    }


}
