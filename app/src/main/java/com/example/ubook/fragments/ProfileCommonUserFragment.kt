package com.example.ubook.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ubook.LogInActivity
import com.example.ubook.ProfileCommonUserActivity
import com.example.ubook.R
import com.example.ubook.adapter.BookingServiceAdapter
import com.example.ubook.adapter.InactiveServiceAdapter
import com.example.ubook.data.CommonUserBookingData
import com.example.ubook.data.ServiceCompanyUserData
import com.example.ubook.databinding.FragmentProfileCommonUserBinding
import com.example.ubook.databinding.FragmentSettingsCommonUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileCommonUserFragment : Fragment() {

    //binding
    private lateinit var binding: FragmentProfileCommonUserBinding

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    //Reference to database
    private lateinit var database: DatabaseReference

    lateinit var bookingsRecyclerView: RecyclerView
    //Data reader
    lateinit var bookingsArrayList: ArrayList<CommonUserBookingData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //init data binding in a fragment
        binding = FragmentProfileCommonUserBinding.inflate(layoutInflater)
        //this value must be returned
        val view : View = binding.root

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        val firebaseUser = firebaseAuth.currentUser
        //user not null, user is logged in, get user info
        val email = firebaseUser?.email
        val newEmail = email?.lowercase()?.replace('.',' ')
        //set to text view
            binding.emailTv.text = email

        //Recycler view adapter
        bookingsRecyclerView = binding.bookingsRv
        bookingsRecyclerView.layoutManager = LinearLayoutManager(activity)
        bookingsRecyclerView.setHasFixedSize(true)

        bookingsArrayList = arrayListOf<CommonUserBookingData>()
        newEmail?.let { getBookingServiceData(it) }
        // Inflate the layout for this fragment
        return view
    }

    private fun getBookingServiceData(email: String) {
        //create adapter
        val adapter = BookingServiceAdapter(bookingsArrayList)
        //change branch
        database = FirebaseDatabase.getInstance().getReference("bookings")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (servicesSnapshots in snapshot.children) {
                        //Change value
                        val bookingService = servicesSnapshots.getValue(CommonUserBookingData::class.java)
                        if (bookingService!!.email == email) {
                            bookingsArrayList.add(bookingService)
                        }
                    }
                }
                bookingsRecyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}