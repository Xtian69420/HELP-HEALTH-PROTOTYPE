package com.example.helphealth;
import java.util.Stack;
// stacks para makuha yung details na pinaka latest na nag donate
public class DonationDetails {
    private static DonationDetails instance;
    Stack<String> Name = new Stack<>();
    Stack<String> Donation = new Stack<>();

    // singleton pattern para makuha nya yung isang instance across the app B)
    private DonationDetails() {}

    public static DonationDetails getInstance() {
        if (instance == null) {
            instance = new DonationDetails();
        }
        return instance;
    }
}