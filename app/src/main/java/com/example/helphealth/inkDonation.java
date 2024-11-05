package com.example.helphealth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class inkDonation extends AppCompatActivity {
    EditText gift;
    EditText name;
    Button donate;
    DonationDetails donationDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ink_donation);

        //components instantiation
        gift = findViewById(R.id.giftInput);
        name = findViewById(R.id.nameOfDonator);
        donate = findViewById(R.id.buttonDonate);
        ImageView homeButton = findViewById(R.id.homeButton1);
        ImageView profileButton = findViewById(R.id.profileButton1);

        int choose = getIntent().getIntExtra("index", 0);

        donationDetails = DonationDetails.getInstance(); // Create an instance of DonationDetails

        donate.setOnClickListener(v -> {
            String giftText = gift.getText().toString();
            String nameText = name.getText().toString();

            if (!giftText.isEmpty() && !nameText.isEmpty()) {
                donationDetails.Donation.push(giftText);
                donationDetails.Name.push(nameText);

                Log.d("Donation", "Gift: " + giftText + ", Name: " + nameText);

                // Show loading alert
                ProgressDialog progressDialog = new ProgressDialog(inkDonation.this);
                progressDialog.setMessage("Donating...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                // Delay for loading diba ang cool
                new Handler().postDelayed(() -> {
                    progressDialog.dismiss();
                    Intent intent = new Intent(inkDonation.this, DonateSuccess.class);
                    intent.putExtra("index", choose);
                    startActivity(intent);
                }, 3000); // 3000 milliseconds = 3 seconds
            } else {
                Toast.makeText(inkDonation.this, "Please input all fields!", Toast.LENGTH_SHORT).show();
            }
        });

        //Navigations
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inkDonation.this, DashBoard.class);
                startActivity(intent);
            }
        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inkDonation.this, Profile.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Para pag napindot ang back walang gagawin ang app :>
    }
}
