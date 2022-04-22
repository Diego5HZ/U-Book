package com.example.ubook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ubook.ProfileCommonUserActivity
import com.example.ubook.R
import com.example.ubook.databinding.FragmentSettingsCommonUserBinding
import com.example.ubook.databinding.FragmentSettingsCompanyUserBinding
import com.google.firebase.auth.FirebaseAuth

class SettingsCompanyUserFragment : Fragment() {

    //binding
    private lateinit var binding: FragmentSettingsCompanyUserBinding

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //init data binding in a fragment
        binding = FragmentSettingsCompanyUserBinding.inflate(layoutInflater)
        //this value must be returned
        val view : View = binding.root
        //init auth
        firebaseAuth = FirebaseAuth.getInstance()

        binding.outBtn.setOnClickListener{
            //logout from current account
            firebaseAuth.signOut()
            //invoke an activity function
            (activity as ProfileCommonUserActivity).checkUser()
        }
        return view
    }
}