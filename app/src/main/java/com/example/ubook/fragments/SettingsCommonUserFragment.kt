package com.example.ubook.fragments

import android.R.attr.data
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.ubook.ProfileCommonUserActivity
import com.example.ubook.databinding.FragmentSettingsCommonUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference


class SettingsCommonUserFragment : Fragment() {

    //binding
    private lateinit var binding: FragmentSettingsCommonUserBinding

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //init data binding in a fragment
        binding = FragmentSettingsCommonUserBinding.inflate(layoutInflater)
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