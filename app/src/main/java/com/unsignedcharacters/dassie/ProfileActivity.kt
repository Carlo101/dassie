package com.unsignedcharacters.dassie

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            fullName.text = user.displayName
        }
        button_edit.setOnClickListener {

            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

    }

}
