package com.unsignedcharacters.dassie

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_edit_profile.*
import org.junit.experimental.results.ResultMatchers.isSuccessful
import com.google.android.gms.tasks.Task
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser



class EditProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)


        button_apply_changes.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser
            val name = fullName.text as String


            val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                    .build()

            user!!.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, ProfileActivity::class.java)
                            startActivity(intent)
                        }
                    }

        }
    }

}
