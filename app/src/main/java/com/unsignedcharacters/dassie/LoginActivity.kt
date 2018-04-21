package com.unsignedcharacters.dassie

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit
import com.google.firebase.auth.FirebaseUser



class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        button_submit.setOnClickListener {

            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    field_login_phoneno.text.toString(),        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    this,               // Activity (for callback binding)
                    mCallbacks);        // OnVerificationStateChangedCallba}
        }
    }

    var mCallbacks = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential?) {

            Log.d("Completed", "onVerificationCompleted:" + credential);

            //signInWithPhoneAuthCredential(credential);
        }

        override fun onVerificationFailed(e: FirebaseException?) {

            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w("ERROR", "onVerificationFailed", e);
        }

        override fun onCodeSent(id: String?, token: PhoneAuthProvider.ForceResendingToken?) {

            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d("Code", "onCodeSent:" + id);

            // Save verification ID and resending token so we can use them later
            val mVerificationId = id;
            val mResendToken = token;

            MaterialDialog.Builder(this@LoginActivity)
                    .title("Verify Phone No.")
                    .content("We sent you a code via SMS.")
                    .inputType(InputType.TYPE_CLASS_NUMBER)
                    .input("Enter code here", "", MaterialDialog.InputCallback { dialog, input ->

                        val credential = PhoneAuthProvider.getCredential(mVerificationId!!, input.toString())

                        signInWithPhoneAuthCredential(credential)

                    }).show()
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {

        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(this, OnCompleteListener<AuthResult>() { task ->

            if (task.isSuccessful) {

                MaterialDialog.Builder(this)
                        .title("Success!")
                        .content("You are now logged in.")
                        .positiveText("OK")
                        .onPositive { dialog, which ->



                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)

                        }
                        .show()
            }
        })
    }
}
