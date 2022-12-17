package org.setu.bookReview.activities

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import org.setu.bookReview.R

class LoginActivity : AppCompatActivity() {

    private lateinit var tvRedirectSignUp: TextView
    lateinit var etEmail: EditText
    private lateinit var etPass: EditText
    lateinit var btnLogin: Button
    private lateinit var passHideBtn: ImageButton
    var passflag: Boolean = false

    // Creating firebaseAuth object
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        // View Binding
        tvRedirectSignUp = findViewById(R.id.btnLinkToRegisterScreen)
        btnLogin = findViewById(R.id.log_in)
        etEmail = findViewById(R.id.enter_your_email)
        etPass = findViewById(R.id.password)
        passHideBtn = findViewById(R.id.showHideBtn3)

        // initialising Firebase auth object
        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            login()
        }

        tvRedirectSignUp.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            // using finish() to end the activity
            finish()
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
    }

    private fun login() {
        val email = etEmail.text.toString()
        val pass = etPass.text.toString()
        // calling signInWithEmailAndPassword(email, pass)
        // function using Firebase auth object
        // On successful response Display a Toast
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, BookReviewListActivity::class.java)
                startActivity(intent)
                finish()
            } else
                Toast.makeText(this, "Log In failed ", Toast.LENGTH_SHORT).show()
        }
    }

}