package com.example.helphealth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helphealth.DBHelper;
import com.example.helphealth.DashBoard;
import com.example.helphealth.MainActivity;
import com.example.helphealth.R;

public class Profile extends AppCompatActivity {
    TextView name;
    TextView gender;
    TextView email;
    ImageView profilepic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageView homeButton = findViewById(R.id.homeButton1);
        ImageView profileButton = findViewById(R.id.profileButton1);
        ImageView logoutButton = findViewById(R.id.imageButton);
        name = findViewById(R.id.fullname);
        gender = findViewById(R.id.gender);
        email = findViewById(R.id.email);
        profilepic = findViewById(R.id.profilepic);

        //para kunin yung latest logged in para i-match sa database
        SharedPreferences preferences = getSharedPreferences("MY_PREFS", MODE_PRIVATE);
        String storedUsername = preferences.getString("last_username", "");

        //database para makuha yung data na need
        DBHelper dbHelper = new DBHelper(this);
        String fullName = dbHelper.getFullname(storedUsername);
        String userGender = dbHelper.getGender(storedUsername);
        String userEmail = dbHelper.getEmail(storedUsername);

        //setting text
        name.setText(fullName);
        gender.setText(userGender);
        email.setText(userEmail);
        if ("Female".equals(userGender)) {
            setFemaleProfilePic();
        }

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // clear preference when log out
                clearSharedPreferences();

                // go to login screen

                ProgressDialog progressDialog = new ProgressDialog(Profile.this);
                progressDialog.setMessage("Logging out...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                // Delay for loading diba ang cool
                new Handler().postDelayed(() -> {
                    progressDialog.dismiss();
                    Intent intent = new Intent(Profile.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    Toast.makeText(Profile.this, "Logged out.", Toast.LENGTH_SHORT).show();
                }, 3000); // 3000 milliseconds = 3 seconds
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, DashBoard.class);
                startActivity(intent);
            }
        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something when the profile button is clicked
            }
        });
    }

    private void clearSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
    private void setFemaleProfilePic() {
        profilepic.setImageResource(R.drawable.femalepic);
    }
}