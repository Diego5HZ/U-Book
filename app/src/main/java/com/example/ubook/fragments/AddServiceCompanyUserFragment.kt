package com.example.ubook.fragments

import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.ubook.R
import com.example.ubook.data.CommonUserData
import com.example.ubook.data.ServiceCompanyUserData
import com.example.ubook.databinding.FragmentAddServiceCompanyUserBinding
import com.example.ubook.databinding.FragmentSettingsCommonUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.nio.file.Files.exists

class AddServiceCompanyUserFragment : Fragment() {

    //binding
    private lateinit var binding: FragmentAddServiceCompanyUserBinding
    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    //Realtime database
    private lateinit var database: DatabaseReference

    private var ServiceId = 0
    private var Place = ""
    private var Description =""
    private var Country=""
    private var City=""
    private var Address=""
    private var Weekdays = ""
    private var Weekend = ""
    private var Contact = ""
    private var Motivationalphrase = ""
    private var Status = true

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
        //recognize current user
        val firebaseUser = firebaseAuth.currentUser
        //user not null, user is logged in, get user info
        val email = firebaseUser?.email
        val newEmail = email!!.lowercase().replace('.',' ')
        //init realtime database
        database = FirebaseDatabase.getInstance().getReference("services")

        binding.saveBtn.setOnClickListener {
            storeData(newEmail)
        }
        binding.clearBtn.setOnClickListener{
            clearAll()
        }

        val args = this.arguments
        binding.placeEt.setText(args?.get("currPlace").toString().trim())
        binding.descriptionEt.setText(args?.get("currDescription").toString().trim())
        binding.countryEt.setText(args?.get("currCountry").toString().trim())
        binding.cityEt.setText(args?.get("currCity").toString().trim())
        binding.addressEt.setText(args?.get("currAddress").toString().trim())
        binding.weekdaysEt.setText(args?.get("currWeekdays").toString().trim())
        binding.weekendEt.setText(args?.get("currWeekend").toString().trim())
        binding.contactUsEt.setText(args?.get("currContactUs").toString().trim())
        binding.motivPhrEt.setText(args?.get("currMotivPhr").toString().trim())
        if (args?.get("currStatus") == "true"){
            binding.activeRb.isChecked = true
            binding.inactiveRb.isChecked = false
        }
        else{
            binding.activeRb.isChecked = false
            binding.inactiveRb.isChecked = true
        }
        binding.activeRb.isChecked
        // Inflate the layout for this fragment
        return view
    }

    private fun clearAll() {
        binding.placeEt.text.clear()
        binding.descriptionEt.text.clear()
        binding.countryEt.text.clear()
        binding.cityEt.text.clear()
        binding.addressEt.text.clear()
        binding.weekdaysEt.text.clear()
        binding.weekendEt.text.clear()
        binding.contactUsEt.text.clear()
        binding.motivPhrEt.text.clear()
    }

    private fun storeData(email: String) {
        Place = binding.placeEt.text.toString().trim()
        Description = binding.descriptionEt.text.toString().trim()
        Country = binding.countryEt.text.toString().trim()
        City = binding.cityEt.text.toString().trim()
        Address = binding.addressEt.text.toString().trim()
        Weekdays = binding.weekdaysEt.text.toString().trim()
        Weekend = binding.weekendEt.text.toString().trim()
        Contact = binding.contactUsEt.text.toString().trim()
        Motivationalphrase = binding.motivPhrEt.text.toString().trim()
        Status = binding.activeRb.isChecked

        //tag of the profile
        database.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if(dataSnapshot.exists())
                        ServiceId=(dataSnapshot.childrenCount.toInt())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        ServiceId += 1
        val serviceCompanyUser = ServiceCompanyUserData(ServiceId.toString(),Place,Description,Country, City, Address, Weekdays, Weekend, Contact, Motivationalphrase, Status,email)
        database.child(ServiceId.toString()).setValue(serviceCompanyUser)
        Toast.makeText(this.activity,"Your service $ServiceId was created :)", Toast.LENGTH_SHORT).show()
        clearAll()
    }
}