package com.example.task

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.FirebaseUser



class LoginScreenActivity : AppCompatActivity() {

    val RC_SIGN_IN = 234
    val tag = "LoginScreenActivity"

    var mAuth : FirebaseAuth? = null
    var googleSignInClient : GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginscreen)

        mAuth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this,gso)

        findViewById<com.google.android.gms.common.SignInButton>(R.id.signin).setOnClickListener{

            signIn()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN){

            val task : Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)

            try{
                val account : GoogleSignInAccount? = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            }catch (e : Exception){
                Toast.makeText(applicationContext,e.message,Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {

        Log.d(tag, "firebaseAuthWithGoogle:" + acct!!.getId())

        val credential : AuthCredential = GoogleAuthProvider.getCredential(acct.idToken,null)

        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this, OnCompleteListener {
                task ->

                if(task.isSuccessful){
                    Log.d(tag, "signInWithCredential:success")
                    val user = mAuth!!.getCurrentUser()

                    Toast.makeText(applicationContext, "User Signed In", Toast.LENGTH_SHORT).show()
                }else{

                    Log.w(tag, "signInWithCredential:failure"+" "+task.exception)
                    Toast.makeText(applicationContext, "Authentication failed.",Toast.LENGTH_SHORT).show()

                }

            })
    }

    private fun signIn() {
        var signInIntent = googleSignInClient!!.signInIntent
        startActivityForResult(signInIntent,RC_SIGN_IN)
    }

    override fun onStart() {
        super.onStart()

        if(mAuth?.currentUser != null){
            finish()
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}

