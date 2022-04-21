package com.example.ubook

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import com.example.ubook.databinding.ActivityProfileCommonUserBinding
import com.example.ubook.fragments.HistoryCommonUserFragment
import com.example.ubook.fragments.ProfileCommonUserFragment
import com.example.ubook.fragments.SearchCommonUserFragment
import com.example.ubook.fragments.SettingsCommonUserFragment
import com.google.firebase.auth.FirebaseAuth

class ProfileCommonUserActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivityProfileCommonUserBinding
    //ActionBar
    private lateinit var actionBar: ActionBar
    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    //Navigation Fragments
    private val profileFragment = ProfileCommonUserFragment()
    private val historyFragment = HistoryCommonUserFragment()
    private val searchFragment = SearchCommonUserFragment()
    private val settingsFragment = SettingsCommonUserFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileCommonUserBinding.inflate(layoutInflater)
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
                R.id.icHistory -> replaceFragment(historyFragment)
                R.id.icSearch -> replaceFragment(searchFragment)
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

    fun checkUser() {
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
            transaction.replace(R.id.commonUserFragmentContainer, fragment)
            transaction.commit()
        }
    }
}