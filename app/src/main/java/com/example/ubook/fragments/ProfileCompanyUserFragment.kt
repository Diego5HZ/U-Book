package com.example.ubook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ubook.R
import com.example.ubook.adapter.ActiveServiceAdapter
import com.example.ubook.adapter.InactiveServiceAdapter
import com.example.ubook.adapter.SearchDataAdapter
import com.example.ubook.data.CompanyUserData
import com.example.ubook.data.ServiceCompanyUserData
import com.example.ubook.databinding.FragmentHistoryCommonUserBinding
import com.example.ubook.databinding.FragmentProfileCommonUserBinding
import com.example.ubook.databinding.FragmentProfileCompanyUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileCompanyUserFragment : Fragment() {

    //binding
    private lateinit var binding: FragmentProfileCompanyUserBinding

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    //Reference to database
    private lateinit var database: DatabaseReference

    //Recycler view
    lateinit var activeServiceRecyclerView: RecyclerView
    lateinit var inactiveServiceRecyclerView: RecyclerView

    //Data reader
    lateinit var activeServiceArrayList: ArrayList<ServiceCompanyUserData>
    //Data reader
    lateinit var inactiveServiceArrayList: ArrayList<ServiceCompanyUserData>

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
        val newEmail = email?.lowercase()?.replace('.',' ')
        //set to text view
        binding.emailTv.text = email

        //Recycler view adapter
        activeServiceRecyclerView = binding.activeServicesRv
        activeServiceRecyclerView.layoutManager = LinearLayoutManager(activity)
        activeServiceRecyclerView.setHasFixedSize(true)

        inactiveServiceRecyclerView = binding.inactiveServicesRv
        inactiveServiceRecyclerView.layoutManager = LinearLayoutManager(activity)
        inactiveServiceRecyclerView.setHasFixedSize(true)
        //Data reader
        activeServiceArrayList = arrayListOf<ServiceCompanyUserData>()
        inactiveServiceArrayList = arrayListOf<ServiceCompanyUserData>()
        newEmail?.let { getActiveServiceData(it) }
        newEmail?.let { getInactiveServiceData(it) }

        // Inflate the layout for this fragment
        return view
    }

    private fun getInactiveServiceData(email: String) {
        //create adapter
        val adapter = InactiveServiceAdapter(inactiveServiceArrayList)
        //change branch
        database = FirebaseDatabase.getInstance().getReference("services").child(email)
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (servicesSnapshots in snapshot.children) {
                        //Change value
                        val inactiveService = servicesSnapshots.getValue(ServiceCompanyUserData::class.java)
                        if (inactiveService!!.email == email && inactiveService.status == false) {
                            inactiveServiceArrayList.add(inactiveService)
                        }
                    }
                }
                inactiveServiceRecyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun getActiveServiceData(email: String) {
        //create adapter
        val adapter = ActiveServiceAdapter(activeServiceArrayList)
        var counter = 0
        var validator = ""
        //change branch
        database = FirebaseDatabase.getInstance().getReference("services").child(email)
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (servicesSnapshots in snapshot.children) {
                        //Change value
                        val activeService = servicesSnapshots.getValue(ServiceCompanyUserData::class.java)
                        if (activeService!!.email == email && activeService.status == true) {
                            activeServiceArrayList.add(activeService)
                        }
                    }
                }
                activeServiceRecyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}