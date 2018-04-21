package com.unsignedcharacters.dassie

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_edit_profile.*
import com.google.android.gms.tasks.Task
import android.support.annotation.NonNull
import android.widget.TextView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase


class EditProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {

            fullName.setText(user.displayName, TextView.BufferType.EDITABLE)
        }

        button_apply_changes.setOnClickListener {

            val name = fullName.text.toString()

            val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                    .build()

            user!!.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            updateUser(name, edt_amount_min.text.toString(), edt_amount_max.text.toString())

                            val intent = Intent(this, ProfileActivity::class.java)
                            startActivity(intent)
                        }
                    }

        }

        button_home2.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateUser(name: String, loanMin: String, loanMax: String) {

        val user = User(name)

        user.setMinAndMax(loanMin, loanMax)

        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference()
        val userRef = ref.child("users").child(user.id)

        userRef.setValue(user)
    }
}
