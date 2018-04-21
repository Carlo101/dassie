package com.unsignedcharacters.dassie;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by faisalmlalani on 4/21/18.
 */

public class User {

    public String id;
    public String name;
    public String rating;
    public String loanMax;
    public String loanMin;
    public String notify;

    public User() {


    }

    public User(String name) {

        id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.name = name;
        rating = "3";
        loanMax = "1000";
        loanMin = "200";
        notify = "";
    }

    public void setMinAndMax (String min, String max) {

        loanMin = min;
        loanMax = max;
    }

    public void isNotify(String notify){
        this.notify = notify;
    }
}
