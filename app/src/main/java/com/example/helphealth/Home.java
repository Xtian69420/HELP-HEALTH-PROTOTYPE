package com.example.helphealth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    TextView text;
    Button enter;
    DBHelper DB;
    UserDetails userDetails;
    private HospitalDetails hospitalDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        DB = new DBHelper(this);
        text = findViewById(R.id.greet);
        enter = findViewById(R.id.buttonenter);
        userDetails = UserDetails.getInstance();


        // Retrieve the last logged-in username from SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MY_PREFS", MODE_PRIVATE);
        String storedUsername = preferences.getString("last_username", "");

        // Use the username to update the greeting text
        userDetails.setFullname(DB.getFullname(storedUsername));

        updateGreetingText();

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, DashBoard.class);
                startActivity(intent);
            }
        });
    }

    private void updateGreetingText() {
        // Get the full name using the DBHelper
        String fullName = userDetails.getFullname();

        // Update the greeting text
        String greeting = "Welcome, " + fullName + "!";
        SpannableString spannedText = new SpannableString(greeting);
        text.setText(spannedText);
        text.bringToFront();
    }

    @Override
    public void onBackPressed() {
        // Para pag napindot ang back walang gagawin ang app :>
    }
}
