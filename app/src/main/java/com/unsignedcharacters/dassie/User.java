package com.unsignedcharacters.dassie;

/**
 * Created by faisalmlalani on 4/21/18.
 */

public class User {

    private String name;
    private String email;
    private int rating;
    private int loanMax;
    private int loanMin;

    public User() {


    }

    public User(String name, String email) {

        this.name = name;
        this.email = email;
        rating = 3;
        loanMax = 1000;
        loanMin = 200;
    }
}
