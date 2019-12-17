package com.example.wisatabandung.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.wisatabandung.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        progress_bar.visibility = View.INVISIBLE

        btn_daftar.setOnClickListener(this)
        btn_login.setOnClickListener(this)


    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_login ->{
                progress_bar.visibility = View.VISIBLE
                btn_login.setText("")

                initFirebaseLogin()

            }

            R.id.btn_daftar ->{
                startActivity<SignupActivity>()
            }
        }



    }

    private fun initFirebaseLogin() {
        val username = edt_username.text.toString()
        val pass = edt_password.text.toString()

        ref = FirebaseDatabase.getInstance().getReference("user").child(username)
        ref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext,"Database Bermasalah", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){

                    val password : String = p0.child("password").getValue().toString()
                    if (pass.equals(password)){
                        startActivity<HomeActivity>(
                            "username" to username
                        )
                        finish()
                    }else{
                        Toast.makeText(applicationContext, " password salah ", Toast.LENGTH_SHORT).show()
                        progress_bar.visibility = View.INVISIBLE
                        btn_login.setText("LOGIN")
                    }
                }else{
                    Toast.makeText(applicationContext, "id  salah", Toast.LENGTH_SHORT).show()
                    progress_bar.visibility = View.INVISIBLE
                    btn_login.setText("LOGIN")
                }
            }

        })
    }
}
