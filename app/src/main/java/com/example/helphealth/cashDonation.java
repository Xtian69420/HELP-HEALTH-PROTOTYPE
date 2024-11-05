package com.example.helphealth;

import static java.lang.System.out;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

// inheritance checkk! :D
// polymorphism also check! 8> (si Appcompat is ineextend ni android.fragment class)
public class cashDonation extends AppCompatActivity {
    EditText cash;
    EditText name;
    Button donate;
    DonationDetails donationDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_donation);

        // components instantiation
        cash = findViewById(R.id.giftInput);
        name = findViewById(R.id.nameOfDonator);
        donate = findViewById(R.id.buttonDonate);
        ImageView homeButton = findViewById(R.id.homeButton1);
        ImageView profileButton = findViewById(R.id.profileButton1);

        int choose = getIntent().getIntExtra("index", 0);

        donationDetails = DonationDetails.getInstance(); // instance ng DonationDetails

        donate.setOnClickListener(v -> {
            // getting values sa user input
            String giftText = cash.getText().toString();
            String nameText = name.getText().toString();

            String a = giftText;
            double b = Double.parseDouble(a);
            out.println("value: " + b);
            // checking if may laman yung fields
            if( b<0.5){
                Toast.makeText(this, "Invalid Amount", Toast.LENGTH_SHORT).show();
            }
            else if (!giftText.isEmpty() && !nameText.isEmpty()) {

                // format the text into ph currency bago i-push
                giftText = formatGiftText(giftText);
                // push
                donationDetails.Donation.push(giftText);
                donationDetails.Name.push(nameText);
                // loading for pushing
                ProgressDialog progressDialog = new ProgressDialog(cashDonation.this);
                progressDialog.setMessage("Donating...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                // delay of pushing
                new Handler().postDelayed(() -> {
                    progressDialog.dismiss(); // Dismiss the ProgressDialog
                    Intent intent = new Intent(cashDonation.this, DonateSuccess.class);
                    intent.putExtra("index", choose);
                    startActivity(intent);
                }, 3000); // 3 seconds delay
            } else {
                // display a toast if any field is empty
                Toast.makeText(cashDonation.this, "Please input all fields!", Toast.LENGTH_SHORT).show();
            }
        });

        // Navigations
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cashDonation.this, DashBoard.class);
                startActivity(intent);
            }
        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cashDonation.this, Profile.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Para pag napindot ang back walang gagawin ang app :>
    }

    // format the giftText by adding commas between thousands para maangas
    private String formatGiftText(String giftText) {
        try {


            // parse input sa double muna
            double giftValue = Double.parseDouble(giftText);

            // format the input into philippine peso kahit di ako proud pinoy
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("fil", "PH"));
            Currency currency = Currency.getInstance("PHP");
            currencyFormat.setCurrency(currency);

            return currencyFormat.format(giftValue);

        } catch (NumberFormatException e) {
            // handle the case where parsing fails (invalid input or pag bonak nag input)
            e.printStackTrace();
            return giftText;
        }
    }

}
