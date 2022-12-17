package org.setu.bookReview.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.setu.bookReview.R


class RegistrationActivity : AppCompatActivity() {
    lateinit var etEmail: EditText
    lateinit var etName: EditText
    lateinit var etConfPass: EditText
    private lateinit var etPass: EditText
    private lateinit var btnSignUp: Button
    private lateinit var passHideBtn: ImageButton
    private lateinit var verPassHideBtn: ImageButton
    lateinit var tvRedirectLogin: TextView
    var passflag: Boolean = false
    var verPassFlag: Boolean = false

    // create Firebase authentication object
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration)

        // View Bindings
        etEmail = findViewById(R.id.enter_your_email)
        etName = findViewById(R.id.enter_your_name)
        etConfPass = findViewById(R.id.verify_password)
        etPass = findViewById(R.id.password)
        btnSignUp = findViewById(R.id.sign_up)
        tvRedirectLogin = findViewById(R.id.btnLinkToRegisterScreen)
        passHideBtn = findViewById(R.id.showHideBtn)
        verPassHideBtn = findViewById(R.id.showHideBtn2)

        // Initialising auth object
        auth = Firebase.auth

        btnSignUp.setOnClickListener {
            signUpUser()
        }

        // switching from signUp Activity to Login Activity
        tvRedirectLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        passHideBtn.setOnClickListener {
            if(passflag){
                etPass.transformationMethod = HideReturnsTransformationMethod.getInstance()
                passflag = false
                passHideBtn.setImageResource(R.drawable.password_closed)
            } else{
                etPass.transformationMethod = PasswordTransformationMethod.getInstance()
                passflag = true
                passHideBtn.setImageResource(R.drawable.password_open)
            }
        }

        verPassHideBtn.setOnClickListener {
            if(verPassFlag){
                etConfPass.transformationMethod = HideReturnsTransformationMethod.getInstance()
                verPassFlag = false
                verPassHideBtn.setImageResource(R.drawable.password_closed)
            } else{
                etConfPass.transformationMethod = PasswordTransformationMethod.getInstance()
                verPassFlag = true
                verPassHideBtn.setImageResource(R.drawable.password_open)
            }
        }

    }

    private fun signUpUser() {
        val email = etEmail.text.toString()
        val name = etName.text.toString()
        val pass = etPass.text.toString()
        val confirmPassword = etConfPass.text.toString()

        // check pass
        if (name.isBlank() || email.isBlank() || pass.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(this, "Name, Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }

        if (pass != confirmPassword) {
            Toast.makeText(this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT)
                .show()
            return
        }
        // If all credential are correct
        // We call createUserWithEmailAndPassword
        // using auth object and pass the
        // email and pass in it.
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Successfully Singed Up", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, BookReviewListActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Singed Up Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}