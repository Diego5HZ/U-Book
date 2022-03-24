package com.example.ubook.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ubook.LogInActivity
import com.example.ubook.ProfileCommonUserActivity
import com.example.ubook.R
import com.example.ubook.databinding.ActivityProfileCommonUserBinding
import com.example.ubook.databinding.FragmentProfileCommonUserBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileCommonUserFragment : Fragment() {

    //binding
    private lateinit var binding: FragmentProfileCommonUserBinding

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentProfileCommonUserBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_common_user, container, false)
    }
}