package com.unsignedcharacters.dassie;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by faisalmlalani on 4/21/18.
 */

public class Request {

    public String borrowerKey;
    public String loanerKey;
    public String amount;
    public String interest;

    public Request() {


    }

    public Request(String borrowerKey, String amount) {

        this.borrowerKey = borrowerKey;
        this.amount = amount;
    }

    public void setLoaner (String loanerKey) {

        this.loanerKey = loanerKey;
    }
}