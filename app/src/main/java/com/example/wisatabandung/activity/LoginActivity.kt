package com.example.wisatabandung.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.wisatabandung.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_daftar.setOnClickListener(this)
        btn_login.setOnClickListener(this)


    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_login ->{
                val i = Intent(this, HomeActivity::class.java)
                startActivity(i)
            }

            R.id.btn_daftar ->{
                val i = Intent(this, SignupActivity::class.java)
                startActivity(i)
            }
        }



    }
}
