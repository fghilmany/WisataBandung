package com.example.wisatabandung.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import com.example.wisatabandung.R
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.startActivity

class SignupActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        btn_daftar_baru.setOnClickListener {
            startActivity<SuccessSignupActivity>()
        }
    }
}
