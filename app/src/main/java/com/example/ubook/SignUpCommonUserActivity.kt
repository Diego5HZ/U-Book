package com.example.ubook

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.ubook.data.CommonUserData
import com.example.ubook.databinding.ActivitySignUpCommonUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpCommonUserActivity : AppCompatActivity() {
    //ViewBinding
    private lateinit var binding: ActivitySignUpCommonUserBinding
    //ActionBar
    private lateinit var actionBar: ActionBar
    //ProgressDialog
    private lateinit var progressDialog: ProgressDialog
    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    //Realtime database
    private lateinit var database: DatabaseReference

    private var username = ""
    private var email =""
    private var dateBirth=""
    private var name=""
    private var surname=""
    private var password = ""
    private var rePassword = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivitySignUpCommonUserBinding.inflate(layoutInflater)
            setContentView(binding.root)

//        //configure actionbar
//        actionBar = supportActionBar!!
//        actionBar.title = "Sign Up Com mon User"
//        actionBar.setDisplayHomeAsUpEnabled(true)
//        actionBar.setDisplayShowHomeEnabled(true)

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Signing Up...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        //init realtime database
        database = Firebase.database.reference

        //handle click, to company user sign up
        binding.companyUserTv.setOnClickListener {
            startActivity(Intent(this, SignUpCompanyUserActivity::class.java))
        }

        //handle click, begin login
        binding.signUpBtn.setOnClickListener{
            //validate data
            validateData()
        }
    }

    private fun validateData() {
        //get data
        email=binding.emailEt.text.toString().trim()
        password=binding.passwordEt.text.toString().trim()
        rePassword=binding.rePasswordEt.text.toString().trim()

        username=binding.usernameEt.text.toString().trim()
        dateBirth=binding.dateBirthEt.text.toString().trim()
        name=binding.nameEt.text.toString().trim()
        surname=binding.surnameEt.text.toString().trim()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
            binding.emailEt.error = "Invalid email format"
        }
        else if(TextUtils.isEmpty(password)){
            //no password entered
            binding.passwordEt.error = "Please enter password"
        }
        else if(password.length<6){
            //password length is less than 6
            binding.passwordEt.error = "Password must at least have 6 characters long"
        }
        else if(password!=rePassword){
            //password length is less than 6
            binding.passwordEt.error = "Passwords must coincide"
        }
        else{
            //data is valid, continue signup
            storeData()
            firebaseSignUp()
        }
    }

    private fun storeData() {
        val newEmail = email.lowercase().replace('.',' ')
        val commonUser = CommonUserData(username,email,dateBirth,name, surname, password)
        //tag of the profile
        database.child("common_users").child(username).setValue(commonUser)
    }

    private fun firebaseSignUp() {
        //show progress
        progressDialog.show()

        //create account
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                //signup success
                progressDialog.dismiss()
                //get user info
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this,"Account created with  email $email", Toast.LENGTH_SHORT).show()

                //open profile
                startActivity(Intent(this, ProfileCommonUserActivity::class.java))
                finish()
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(this,"SignUp failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() //go back to previous activity, when back button of actionbar clicked
        return super.onSupportNavigateUp()
    }
}