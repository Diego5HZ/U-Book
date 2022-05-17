package com.example.ubook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ubook.R
import com.example.ubook.databinding.FragmentServiceViewCommonUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class ServiceViewCommonUserFragment : Fragment() {

    //binding
    private lateinit var binding : FragmentServiceViewCommonUserBinding

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    //Realtime database
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //init data binding in a fragment
        binding = FragmentServiceViewCommonUserBinding.inflate(layoutInflater)
        //this value must be returned
        val view : View = binding.root

        //init realtime database
        database = FirebaseDatabase.getInstance().getReference("services")

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        val args = this.arguments
        binding.placeTv.text = args?.get("currPlace").toString().trim()
        binding.descriptionTil.text = args?.get("currDescription").toString().trim()
        binding.countryTil.text = args?.get("currCountry").toString().trim()
        binding.cityTil.text = args?.get("currCity").toString().trim()
        binding.addressTil.text = args?.get("currAddress").toString().trim()
        binding.weekdaysTv.text = args?.get("currWeekdays").toString().trim()
        binding.weekendTv.text = args?.get("currWeekend").toString().trim()
        binding.contactUsTil.text = args?.get("currContactUs").toString().trim()
        binding.motivPhrTil.text = args?.get("currMotivPhr").toString().trim()

        // Inflate the layout for this fragment
        return view
    }
}