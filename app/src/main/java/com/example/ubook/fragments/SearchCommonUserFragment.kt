package com.example.ubook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ubook.R
import com.example.ubook.adapter.SearchDataAdapter
import com.example.ubook.data.CompanyUserData
import com.example.ubook.databinding.FragmentProfileCompanyUserBinding
import com.example.ubook.databinding.FragmentSearchCommonUserBinding
import com.google.firebase.database.*

class SearchCommonUserFragment : Fragment() {

    //binding
    private lateinit var binding: FragmentSearchCommonUserBinding
    //Reference to database
    private lateinit var database: DatabaseReference

    lateinit var boxRecyclerView: RecyclerView
    //Data reader
    lateinit var searchArrayList: ArrayList<CompanyUserData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //init data binding in a fragment
        binding = FragmentSearchCommonUserBinding.inflate(layoutInflater)
        //this value must be returned
        val view : View = binding.root

        //--Insert actions here--//
        //Recycler view adapter
        boxRecyclerView = binding.boxRv
        boxRecyclerView.layoutManager = LinearLayoutManager(activity)
        boxRecyclerView.setHasFixedSize(true)
        //Data reader
        searchArrayList = arrayListOf<CompanyUserData>()
        binding.searchBtn.setOnClickListener{
            val country : String = binding.countryEt.text.toString()
            getBoxData(country)
        }

        // Inflate the layout for this fragment
        return view
    }

    private fun getBoxData(country: String) {
        //create adapter
        val adapter = SearchDataAdapter(searchArrayList)
        //change branch
        database = FirebaseDatabase.getInstance().getReference("company_users")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (servicesSnapshots in snapshot.children) {
                        //Change value
                        val service = servicesSnapshots.getValue(CompanyUserData::class.java)
                        if (service!!.companyName == country) {
                            searchArrayList.add(service)
                        }
                    }
                }
                boxRecyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}