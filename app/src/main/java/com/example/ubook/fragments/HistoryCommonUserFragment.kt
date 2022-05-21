package com.example.ubook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.ubook.R
import com.example.ubook.data.CommonUserBookingData
import com.example.ubook.data.ServiceCompanyUserData
import com.example.ubook.databinding.FragmentAddServiceCompanyUserBinding
import com.example.ubook.databinding.FragmentHistoryCommonUserBinding
import com.example.ubook.databinding.FragmentSettingsCommonUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HistoryCommonUserFragment : Fragment() {

    //binding
    private lateinit var binding: FragmentHistoryCommonUserBinding
    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    //Realtime database
    private lateinit var database: DatabaseReference

    private var bookId = 0
    private var country = ""
    private var city =""
    private var address=""
    private var date=""
    private var hour=""
    private var place=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //init data binding in a fragment
        binding = FragmentHistoryCommonUserBinding.inflate(layoutInflater)
        //this value must be returned
        val view : View = binding.root

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        //recognize current user
        val firebaseUser = firebaseAuth.currentUser
        //user not null, user is logged in, get user info
        val email = firebaseUser?.email
        val newEmail = email!!.lowercase().replace('.',' ')
        //init realtime database
        database = FirebaseDatabase.getInstance().getReference("bookings")

        binding.bookBtn.setOnClickListener {
            storeData(newEmail)
        }

        // Inflate the layout for this fragment
        return view
    }

    private fun storeData(email: String) {
        country = binding.countryEt.text.toString().trim()
        city = binding.cityEt.text.toString().trim()
        address = binding.addressEt.text.toString().trim()
        date = binding.dateEt.text.toString().trim()
        hour = binding.hourEt.text.toString().trim()
        place = binding.placeEt.text.toString().trim()

        //tag of the profile
        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists())
                    bookId=(dataSnapshot.childrenCount.toInt())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        bookId += 1
        val commonUserBookingData = CommonUserBookingData(bookId.toString(),country,city,address,date,hour,place,email)
        database.child(bookId.toString()).setValue(commonUserBookingData)
        Toast.makeText(this.activity,"You have booked an appointment :)", Toast.LENGTH_SHORT).show()
        clearAll()
    }

    private fun clearAll() {
        binding.countryEt.text.clear()
        binding.cityEt.text.clear()
        binding.addressEt.text.clear()
        binding.dateEt.text.clear()
        binding.hourEt.text.clear()
        binding.placeEt.text.clear()
    }
}