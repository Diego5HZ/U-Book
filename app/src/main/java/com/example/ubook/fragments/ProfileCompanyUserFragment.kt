package com.example.ubook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ubook.R
import com.example.ubook.databinding.FragmentHistoryCommonUserBinding
import com.example.ubook.databinding.FragmentProfileCommonUserBinding
import com.example.ubook.databinding.FragmentProfileCompanyUserBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileCompanyUserFragment : Fragment() {

    //binding
    private lateinit var binding: FragmentProfileCompanyUserBinding

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //init data binding in a fragment
        binding = FragmentProfileCompanyUserBinding.inflate(layoutInflater)
        //this value must be returned
        val view : View = binding.root

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        val firebaseUser = firebaseAuth.currentUser
        //user not null, user is logged in, get user info
        val email = firebaseUser?.email
        //set to text view
        binding.emailTv.text = email

        // Inflate the layout for this fragment
        return view
    }
}