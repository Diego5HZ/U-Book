package com.example.ubook

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import com.example.ubook.databinding.ActivityLoginBinding
import com.example.ubook.databinding.ActivityProfileCommonUserBinding
import com.example.ubook.databinding.ActivityProfileCompanyUserBinding
import com.example.ubook.databinding.ActivitySignUpCompanyUserBinding
import com.example.ubook.fragments.*
import com.google.firebase.auth.FirebaseAuth

class ProfileCompanyUserActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivityProfileCompanyUserBinding
    //ActionBar
    private lateinit var actionBar: ActionBar
    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    //Navigation Fragments
    private val profileFragment = ProfileCompanyUserFragment()
    private val addServiceFragment = AddServiceCompanyUserFragment()
    private val settingsFragment = SettingsCompanyUserFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileCompanyUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        //configure actionbar
//        actionBar = supportActionBar!!
//        actionBar.title = "Profile"

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //show fragment
        replaceFragment(profileFragment)
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.icProfile -> replaceFragment(profileFragment)
                R.id.icAddService -> replaceFragment(addServiceFragment)
                R.id.icSettings -> replaceFragment(settingsFragment)
            }
            true
        }

        //handle click, logout
//        binding.logoutBtn.setOnClickListener{
//            firebaseAuth.signOut()
//            checkUser()
//        }
    }

    private fun checkUser() {
        //check user is logged in or not
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null){
            //user not null, user is logged in, get user info
            val email = firebaseUser.email
            //set to text view
//            binding.emailTv.text = email
        }
        else {
            //user is null, user is not logged in
            startActivity(Intent(this, LogInActivity::class.java))
            finish()
        }
    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.companyUserFragmentContainer, fragment)
            transaction.commit()
        }
    }
}