package com.example.ubook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ubook.R
import com.example.ubook.data.CommonUserData
import com.example.ubook.data.ServiceCompanyUserData
import com.example.ubook.databinding.FragmentAddServiceCompanyUserBinding
import com.example.ubook.databinding.FragmentSettingsCommonUserBinding
import com.google.firebase.auth.FirebaseAuth

class AddServiceCompanyUserFragment : Fragment() {

    //binding
    private lateinit var binding: FragmentAddServiceCompanyUserBinding
    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //init data binding in a fragment
        binding = FragmentAddServiceCompanyUserBinding.inflate(layoutInflater)
        //this value must be returned
        val view : View = binding.root

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()



        private var Place = ""
        private var Description =""
        private var Country=""
        private var City=""
        private var Address=""
        private var Workinghours = ""
        private var Contact = ""
        private var Motivationalphrase = ""
        private var Status = ""
        private var email = ""

        private fun storeData() {
            val newEmail = email.lowercase().replace('.',' ')
            val servicecompanyuser = ServiceCompanyUserData(Place,email,Description,Country, City, Address, Workinghours, Contact, Motivationalphrase, Status)
            //tag of the profile
            database.child("service_company_user").child(email).setValue(servicecompanyuser)
        }

        binding.saveBtn.setOnClickListener {
            val firebaseUser = firebaseAuth.currentUser
//user not null, user is logged in, get user info
            val email = firebaseUser?.email
            val newEmail = email?.lowercase()?.replace('.',' ')
        }


        // Inflate the layout for this fragment
        return view
    }
}