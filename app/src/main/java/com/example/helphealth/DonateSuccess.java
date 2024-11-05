package com.example.helphealth;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.text.HtmlCompat;
import androidx.appcompat.app.AppCompatActivity;

public class DonateSuccess extends AppCompatActivity {
    private static final String TAG = "DonateSuccess";

    TextView nameH;
    TextView donated;
    TextView nameD;
    Button continueBttn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_success);
        Toast.makeText(this, "Donation Success!", Toast.LENGTH_SHORT).show();

        nameH = findViewById(R.id.hospitalNameText);
        donated = findViewById(R.id.giftText);
        nameD = findViewById(R.id.donatorNameText);
        continueBttn = findViewById(R.id.button);
        ImageView homeButton = findViewById(R.id.homeButton1);
        ImageView profileButton = findViewById(R.id.profileButton1);

        DonationDetails DD = DonationDetails.getInstance();
        HospitalDetails HD = HospitalDetails.getInstance();
        HD.inputData();

        try {
            Log.d(TAG, "HospitalName size: " + HD.HospitalName.size());
            int choose = getIntent().getIntExtra("choose", 0);
            Log.d(TAG, "Choose: " + choose);

            if (!HD.HospitalName.isEmpty() && choose >= 0 && choose < HD.HospitalName.size()) {
                nameH.setText(HtmlCompat.fromHtml("<b>HOSPITAL:</b> " + HD.HospitalName.get(choose), HtmlCompat.FROM_HTML_MODE_LEGACY));
                Log.d(TAG, "Hospital at index " + choose + ": " + HD.HospitalName.get(choose));
            } else {
                nameH.setText(HtmlCompat.fromHtml("<b>HOSPITAL:</b> N/A", HtmlCompat.FROM_HTML_MODE_LEGACY));
            }

            Log.d(TAG, "Donation size: " + DD.Donation.size());

            if (!DD.Donation.isEmpty()) {
                donated.setText(HtmlCompat.fromHtml("<b>DONATED:</b> " + DD.Donation.peek(), HtmlCompat.FROM_HTML_MODE_LEGACY));
            } else {
                donated.setText(HtmlCompat.fromHtml("<b>DONATED:</b> N/A", HtmlCompat.FROM_HTML_MODE_LEGACY));
            }

            Log.d(TAG, "Name size: " + DD.Name.size());

            if (!DD.Name.isEmpty()) {
                nameD.setText(HtmlCompat.fromHtml("<b>NAME OF DONATOR:</b> " + DD.Name.peek(), HtmlCompat.FROM_HTML_MODE_LEGACY));
            } else {
                nameD.setText(HtmlCompat.fromHtml("<b>NAME OF DONATOR:</b> N/A", HtmlCompat.FROM_HTML_MODE_LEGACY));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error displaying information: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        continueBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DonateSuccess.this, DashBoard.class);
                startActivity(intent);
            }
        });

        // Navigations
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonateSuccess.this, DashBoard.class);
                startActivity(intent);
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonateSuccess.this, Profile.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Prevent the back button from doing anything
    }
}
