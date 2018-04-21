package com.unsignedcharacters.dassie

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_request.*

class RequestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)

        nameFieldTextView.text = "Carlo Gomez requests R" + getIntent().extras.getString("amount")

        button_smart_contract.setOnClickListener{
            val intent = Intent(this, ContractActivity::class.java)
            startActivity(intent)
        }

        button_reject.setOnClickListener {

            val database = FirebaseDatabase.getInstance()
            val ref = database.getReference()
            val userRef = ref.child("users")
            val user = FirebaseAuth.getInstance().currentUser

            userRef.child(user!!.uid).child("notify").setValue("")

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        button_home4.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


}
