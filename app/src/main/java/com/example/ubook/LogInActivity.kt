package com.example.ubook

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.ubook.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LogInActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding:ActivityLoginBinding
    //ActionBar
    private lateinit var actionBar: ActionBar
    //ProgressDialog
    private lateinit var progressDialog:ProgressDialog
    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    //Realtime database
    private lateinit var database: DatabaseReference
    private var email =""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        //configure actionbar
//        actionBar = supportActionBar!!
//        actionBar.title = "Login"

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //handle click, SignUpCommonUserActivity
        binding.noAccountClickTv.setOnClickListener {
            startActivity(Intent(this, SignUpCommonUserActivity::class.java))
        }

        //handle click, begin login
        binding.loginBtn.setOnClickListener{
            //before logging in, validate data
            validateData()
        }
    }

    private fun validateData() {
        email=binding.emailEt.text.toString().trim()
        password=binding.passwordEt.text.toString().trim()

        //validate data
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
            binding.emailEt.error = "Invalid email format"
        }
        else if(TextUtils.isEmpty(password)){
            //no password entered
            binding.passwordEt.error = "Please enter password"
        }
        else{
            //data is validated, begin login
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        //show progress
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                //login success
                progressDialog.dismiss()
                //get user info
                val firebaseUser = firebaseAuth.currentUser
                val uEmail = firebaseUser!!.email
                Toast.makeText(this,"LoggedIn as $uEmail", Toast.LENGTH_SHORT).show()
                //Choose user and open profile
                readData(email)
//                //open profile
//                startActivity(Intent(this,ProfileCommonUserActivity::class.java))
                finish()
            }
            .addOnFailureListener { e->
                //login failed
                progressDialog.dismiss()
                Toast.makeText(this,"Login failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun readData(email: String) {
        val newEmail=email.replace('.',' ')
        database = FirebaseDatabase.getInstance().getReference("company_users")
        database.child(newEmail).get()
            .addOnSuccessListener {
                if(it.exists()){
                    startActivity(Intent(this,ProfileCompanyUserActivity::class.java))
                }
                else{
                    startActivity(Intent(this,ProfileCommonUserActivity::class.java))
                }
            }
    }

    private fun checkUser() {
        //if user is already logged in go to profile activity
        //get current user
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null){
            firebaseUser.email?.let { readData(it) }
        }
    }
}