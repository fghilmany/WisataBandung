package com.example.wisatabandung.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wisatabandung.R
import com.example.wisatabandung.activity.LoginActivity
import kotlinx.android.synthetic.main.fragment_profil.*
import org.jetbrains.anko.support.v4.startActivity

class ProfilFragment : Fragment(), View.OnClickListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_logout.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.tv_logout -> {
                startActivity<LoginActivity>()
            }
        }
    }


}
