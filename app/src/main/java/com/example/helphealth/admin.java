// admin.java
package com.example.helphealth;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class admin extends AppCompatActivity {

    private EditText hospitalNameEditText, hospitalDescEditText, hospitalLocEditText, hospitalnameDelete;
    private TextView hospitalListTextView;
    private Button addHospitalButton, deleteHospitalButton;

    private HospitalDetails hospitalDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        hospitalNameEditText = findViewById(R.id.editTextTextPersonName);
        hospitalDescEditText = findViewById(R.id.editTextTextPersonName2);
        hospitalLocEditText = findViewById(R.id.editTextTextPersonName3);
        hospitalnameDelete = findViewById(R.id.editTextTextPersonName4);
        hospitalListTextView = findViewById(R.id.textView4);
        addHospitalButton = findViewById(R.id.button9);
        deleteHospitalButton = findViewById(R.id.button10);

        hospitalDetails = HospitalDetails.getInstance();
        // Clear existing data to prevent duplication
        hospitalDetails.HospitalName.clear();
        hospitalDetails.HospitalDescription.clear();
        hospitalDetails.HospitalLocation.clear();

        // Load data
        hospitalDetails.loadData(this);

        addHospitalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = hospitalNameEditText.getText().toString().trim();
                String desc = hospitalDescEditText.getText().toString();
                String loc = hospitalLocEditText.getText().toString();

                if (!name.isEmpty() && !desc.isEmpty() && !loc.isEmpty()) {
                    hospitalDetails.HospitalName.add(name);
                    hospitalDetails.HospitalDescription.add(desc);
                    hospitalDetails.HospitalLocation.add(loc);

                    hospitalDetails.saveData(admin.this); // Save the updated data
                    updateHospitalList();
                    Toast.makeText(admin.this, "Hospital added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(admin.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteHospitalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameToDelete = hospitalnameDelete.getText().toString().trim();
                if (!nameToDelete.isEmpty()) {
                    if (hospitalDetails.removeHospital(nameToDelete)) {
                        hospitalDetails.saveData(admin.this); // Save the updated data
                        updateHospitalList();
                        Toast.makeText(admin.this, "Hospital deleted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(admin.this, "Hospital not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(admin.this, "Please enter the hospital name to delete", Toast.LENGTH_SHORT).show();
                }
            }
        });

        updateHospitalList();
    }

    private void updateHospitalList() {
        StringBuilder hospitalList = new StringBuilder();

        for (int i = 0; i < hospitalDetails.HospitalName.size(); i++) {
            hospitalList.append(hospitalDetails.HospitalName.get(i)).append("\n");
        }

        hospitalListTextView.setText(hospitalList.toString());
    }
}
