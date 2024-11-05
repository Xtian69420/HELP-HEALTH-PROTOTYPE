package com.example.helphealth;
public class UserDetails extends DDetails{
    private static UserDetails instance;
    private String fullname;
    private String email;
    private String gender;
    public UserDetails() {

    }
    public static UserDetails getInstance() {
        if (instance == null) {
            instance = new UserDetails();
        }
        return instance;
    }
    // encapsulation cheeeeeckk!
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public void getAllDetails() {
        super.getAllDetails();
    }
}