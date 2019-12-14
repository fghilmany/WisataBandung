package com.example.wisatabandung.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.wisatabandung.R
import com.example.wisatabandung.fragment.HomeFragment
import com.example.wisatabandung.fragment.MyTicketFragment
import com.example.wisatabandung.fragment.ProfilFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.home-> {
                    loadHomeFragment(savedInstanceState)
                }
                R.id.my_ticket->{
                    loadMyTicketFragment(savedInstanceState)
                }
                R.id.profil->{
                    loadProfilFragment(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = R.id.home
    }

    private fun loadHomeFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, HomeFragment(), HomeFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadMyTicketFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, MyTicketFragment(), MyTicketFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadProfilFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, ProfilFragment(), ProfilFragment::class.java.simpleName)
                .commit()
        }
    }
}
