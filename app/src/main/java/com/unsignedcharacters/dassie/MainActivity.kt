package com.unsignedcharacters.dassie

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference()
        val userRef = ref.child("users")

        val userListener = object: ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val users = dataSnapshot.children.iterator()

                while (users.hasNext()) {

                    val currentUser = users.next()

                    val user = currentUser.getValue() as HashMap<String, Any>

                    val userId = currentUser.key
                    val userName: String? = user.get("name") as? String

                    text_name.text = userName
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }

        userRef.addValueEventListener(userListener)

        button_borrow.setOnClickListener {

            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

    }
}
