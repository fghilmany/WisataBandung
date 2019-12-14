package com.example.wisatabandung.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.wisatabandung.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var animSplash : Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        animSplash = AnimationUtils.loadAnimation(this, R.anim.anim_splash)

        iv_logo_splash.startAnimation(animSplash)

        //intent otomatis setelah 2 detik (2000 ms)
        Handler().postDelayed({
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()

        },2000)
    }
}
