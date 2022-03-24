package com.example.ubook

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.ubook.databinding.ActivitySignUpCompanyUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpCompanyUserActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivitySignUpCompanyUserBinding
    //ActionBar
    private lateinit var actionBar: ActionBar
    //ProgressDialog
    private lateinit var progressDialog: ProgressDialog
    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    //Realtime database
    private lateinit var database: DatabaseReference

    private var companyName = ""
    private var typeService =""
    private var tags=""
    private var firstDateService=""
    private var email = ""
    private var password = ""
    private var rePassword = ""
    private var lei = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUpCompanyUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Sign Up Company User"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Signing Up...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        //init realtime database
        database = Firebase.database.reference

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

        companyName=binding.companyNameEt.text.toString().trim()
        typeService=binding.typeServiceEt.text.toString().trim()
        tags=binding.tagsEt.text.toString().trim()
        firstDateService=binding.firstDateServiceEt.text.toString().trim()
        lei=binding.leiEt.text.toString().trim()

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
        val companyUser = CompanyUserData(companyName,typeService,tags,firstDateService,newEmail,password,lei)
        //tag of the profile
        database.child("company_users").child(newEmail).setValue(companyUser)
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
                startActivity(Intent(this, ProfileCompanyUserActivity::class.java))
                finish()
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(this,"SignUp failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}