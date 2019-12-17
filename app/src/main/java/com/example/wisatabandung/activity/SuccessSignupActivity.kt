package com.example.wisatabandung.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.wisatabandung.R
import kotlinx.android.synthetic.main.activity_success_signup.*
import org.jetbrains.anko.startActivity

class SuccessSignupActivity : AppCompatActivity() {

    private lateinit var ttb : Animation
    private lateinit var btt : Animation
    private lateinit var splash : Animation
    private lateinit var stand : Animation

    private var username : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_signup)

        ttb = AnimationUtils.loadAnimation(this,R.anim.ttb)
        btt = AnimationUtils.loadAnimation(this,R.anim.btt)
        splash = AnimationUtils.loadAnimation(this,R.anim.anim_splash)
        stand = AnimationUtils.loadAnimation(this,R.anim.stand)

        //run animation
        iv_hai.startAnimation(splash)
        tv_hai.startAnimation(btt)
        tv_hai2.startAnimation(btt)
        progress_bar_hai.startAnimation(stand)

        username = intent.getStringExtra("username")

        Handler().postDelayed({
            Toast.makeText(applicationContext,username,Toast.LENGTH_SHORT).show()
            startActivity<HomeActivity>(
                "username" to username
            )
            finish()

        },3500)
    }
}
