package com.example.ubook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ubook.R
import com.example.ubook.databinding.FragmentAddServiceCompanyUserBinding
import com.example.ubook.databinding.FragmentHistoryCommonUserBinding
import com.example.ubook.databinding.FragmentSettingsCommonUserBinding

class HistoryCommonUserFragment : Fragment() {

    //binding
    private lateinit var binding: FragmentHistoryCommonUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //init data binding in a fragment
        binding = FragmentHistoryCommonUserBinding.inflate(layoutInflater)
        //this value must be returned
        val view : View = binding.root

        //--Insert actions here--//

        // Inflate the layout for this fragment
        return view
    }
}