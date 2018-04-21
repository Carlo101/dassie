package com.unsignedcharacters.dassie

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_settings.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {

            fullName.text = user.displayName

            val database = FirebaseDatabase.getInstance()
            val ref = database.getReference()
            val userRef = ref.child("users")

            val userListener = object: ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    val users = dataSnapshot.children.iterator()

                    while (users.hasNext()) {

                        val currentUser = users.next()

                        val taggedUser = currentUser.getValue() as HashMap<String, Any>

                        val userId = currentUser.key

                        if (user.uid == userId) {

                            val userLoanMin: String? = taggedUser.get("loanMin") as? String
                            val userLoanMax: String? = taggedUser.get("loanMax") as? String

                            loan_min_max.text = ("R" + userLoanMin + " - " + "R" + userLoanMax)
                        }

                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            }

            userRef.addValueEventListener(userListener)
        }


        button_borrow2.setOnClickListener {

            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        button_home.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

}
