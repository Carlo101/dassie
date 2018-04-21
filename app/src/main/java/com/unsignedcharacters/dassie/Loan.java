package com.unsignedcharacters.dassie;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by faisalmlalani on 4/21/18.
 */

public class Loan {

    public int max;
    public int min;

    public Loan() {


    }

    public Loan(int max, int min) {

        this.max = max;
        this.min = min;
    }
}