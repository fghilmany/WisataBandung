package com.example.wisatabandung.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.wisatabandung.R
import kotlinx.android.synthetic.main.activity_success_buy.*

class SuccessBuyActivity : AppCompatActivity() {

    private lateinit var ttb : Animation
    private lateinit var btt : Animation
    private lateinit var splash : Animation
    private lateinit var stand : Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_buy)

        ttb = AnimationUtils.loadAnimation(this,R.anim.ttb)
        btt = AnimationUtils.loadAnimation(this,R.anim.btt)
        splash = AnimationUtils.loadAnimation(this,R.anim.anim_splash)
        stand = AnimationUtils.loadAnimation(this,R.anim.stand)

        //run animation
        iv_success.startAnimation(splash)
        tv_success.startAnimation(btt)
        tv_success2.startAnimation(btt)
        progress_bar_success.startAnimation(stand)

        Handler().postDelayed({
            val i = Intent(this, HomeActivity::class.java)
            startActivity(i)
            finish()

        },3500)
    }
}
