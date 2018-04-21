package com.unsignedcharacters.dassie

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import com.afollestad.materialdialogs.MaterialDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_profile.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listenForNotify()

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

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }

        userRef.addValueEventListener(userListener)

        val user = FirebaseAuth.getInstance().currentUser

        if (user == null) {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        } else {

            val userName = user.displayName

            if (userName == "") {

                val intent = Intent(this, EditProfileActivity::class.java)
                startActivity(intent)
            }
        }

        image_profile.setOnClickListener {

            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        image_settings.setOnClickListener {

            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        button_borrow.setOnClickListener {

            val amount = edt_amount.text.toString()
            val request = Request(user!!.uid, amount)

            val requestKey = ref.child("requests").push().key
            val requestRef = ref.child("requests").child(requestKey)
            requestRef.setValue(request)

            traverseUsers(requestKey)
        }

    }
    public fun traverseUsers(requestKey: String){

        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference()
        val userRef = ref.child("users")

        val currentUs = FirebaseAuth.getInstance().currentUser

        val userListener = object: ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val users = dataSnapshot.children.iterator()
                val inputLoanAmount = edt_amount.text.toString().toInt()

                while (users.hasNext()) {

                    val currentUser = users.next()

                    val user = currentUser.getValue() as HashMap<String, Any>
                    val userID = currentUser.key

                    val userLoanMin: String? = user.get("loanMin") as? String
                    val userLoanMax: String? = user.get("loanMax") as? String

                    if (userLoanMax != null && userLoanMin != null && userID != currentUs!!.uid) {

                        if(inputLoanAmount >= userLoanMin.toInt() && inputLoanAmount <= userLoanMax.toInt()){
                            userRef.child(userID).child("notify").setValue(requestKey)
                        }
                    }

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }

        userRef.addListenerForSingleValueEvent(userListener)
    }

    public fun listenForNotify() {

        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference()
        val userRef = ref.child("users")
        val requestRef = ref.child("requests")
        val user = FirebaseAuth.getInstance().currentUser

        val userListener = object: ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val users = dataSnapshot.children.iterator()

                while (users.hasNext()) {

                    val currentUser = users.next()

                    val taggedUser = currentUser.getValue() as HashMap<String, Any>

                    val userId = currentUser.key

                    if (user != null) {

                        if (user.uid == userId) {

                            val notify: String? = taggedUser.get("notify") as? String

                            if (notify != "") {

                                val requestListener = object: ValueEventListener {

                                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                                        val requests = dataSnapshot.children.iterator()

                                        while (requests.hasNext()) {

                                            val currentRequest = requests.next()

                                            val request = currentRequest.getValue() as HashMap<String, Any>
                                            val requestKey = currentRequest.key

                                            val borrower: String? = request.get("borrower") as? String
                                            val amount: String? = request.get("amount") as? String

                                            if (requestKey == notify) {

                                                MaterialDialog.Builder(this@MainActivity)
                                                        .title("Hey!")
                                                        .content("You received a request to lend R" + amount  + "! Would you like to proceed?")
                                                        .positiveText("Yes")
                                                        .onPositive { dialog, which ->

                                                            val intent = Intent(this@MainActivity, RequestActivity::class.java)
                                                            intent.putExtra("amount", amount)
                                                            startActivity(intent)
                                                        }
                                                        .negativeText("No")
                                                        .onNegative { dialog, which ->

                                                            userRef.child(userId).child("notify").setValue("")
                                                        }
                                                        .show()
                                            }

                                        }
                                    }

                                    override fun onCancelled(databaseError: DatabaseError) {

                                    }
                                }

                                requestRef.addListenerForSingleValueEvent(requestListener)

                            }

                        }
                    }

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }

        userRef.addValueEventListener(userListener)
    }
}
