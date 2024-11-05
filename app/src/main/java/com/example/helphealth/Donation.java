package com.example.helphealth;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.text.HtmlCompat;

public class Donation extends AppCompatActivity {
    HospitalDetails HD = HospitalDetails.getInstance();
    TextView HName;
    TextView HDesc;
    TextView HLoc;
    ImageButton inkind;
    ImageButton cash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);
        //inputAllData
        HD.inputData();
        HD.loadData(this);

        //Instantiation lahat ng components sa xml sa loob ng onCreate()
        ImageView homeButton = findViewById(R.id.homeButton1);
        ImageView profileButton = findViewById(R.id.profileButton1);
        HName = findViewById(R.id.headerText);
        HDesc = findViewById(R.id.descriptionText);
        HLoc = findViewById(R.id.boldDescriptionText);
        ImageButton inkind = findViewById(R.id.inkind);
        ImageButton cash = findViewById(R.id.cash);

        //Setting values for Hospital Datas
        int choose = getIntent().getIntExtra("choose", 0);
        String holderName = HD.HospitalName.get(choose);
        String holderDesc = HD.HospitalDescription.get(choose);
        String holderLoc = HD.HospitalLocation.get(choose);
        HName.setText(holderName);
        HDesc.setText(HtmlCompat.fromHtml("<b>Description:</b> " + holderDesc, HtmlCompat.FROM_HTML_MODE_LEGACY));
        HLoc.setText(HtmlCompat.fromHtml("<b>Location:</b> " + holderLoc, HtmlCompat.FROM_HTML_MODE_LEGACY));

        //inkind button
        inkind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Donation.this, inkDonation.class);
                intent.putExtra("index",choose);
                startActivity(intent);
            }
        });
        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Donation.this, cashDonation.class);
                intent.putExtra("index",choose);
                startActivity(intent);
            }
        });
        //Navigation buttons
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Donation.this, DashBoard.class);
                startActivity(intent);
            }
        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Donation.this, Profile.class);
                startActivity(intent);
            }
        });
    }

}