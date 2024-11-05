package com.example.helphealth;// DashBoard.java

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Objects;

public class DashBoard extends AppCompatActivity implements OnHospitalClickListener{
    private HospitalDetails hospitalDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize hospitalDetails and load data
        hospitalDetails = HospitalDetails.getInstance();

        // Clear existing data to prevent duplication
        hospitalDetails.HospitalName.clear();
        hospitalDetails.HospitalDescription.clear();
        hospitalDetails.HospitalLocation.clear();

        // Load data
        hospitalDetails.loadData(this);


        ImageView homeButton = findViewById(R.id.homeButton);
        ImageView profileButton = findViewById(R.id.profileButton);



        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add the functionality for the home button
                // For example, you can navigate to the home activity
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoard.this, Profile.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        // Create an instance of HospitalRecyclerViewAdapter
        HospitalRecyclerViewAdapter adapter = new HospitalRecyclerViewAdapter(
                Objects.requireNonNull(hospitalDetails.HospitalName),
                Objects.requireNonNull(hospitalDetails.HospitalLocation),
                this // Pass the activity as the click listener
        );

        recyclerView.setAdapter(adapter);

        // Set the layout manager (you can use LinearLayoutManager)
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onBackPressed() {
        // Disable the back button functionality for this activity
    }
    @Override
    public void onHospitalClick(int hospitalIndex) {
        // Handle the hospital click event, for example, start a new activity
        Intent intent = new Intent(DashBoard.this, Donation.class);
        intent.putExtra("choose", hospitalIndex);
        startActivity(intent);
    }
}
