package com.example.helphealth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText email, password;
    Button btnlogin;
    DBHelper DB;

    // Variable to track password visibility
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences preferences = getSharedPreferences("MY_PREFS", MODE_PRIVATE);
        String lastUsername = preferences.getString("last_username", "");

        // check if naka log in naba yung user
        if (!lastUsername.isEmpty()) {
            // pag ang user ay naka sign in na dati pa
            Intent intent = new Intent(MainActivity.this, Home.class);
            startActivity(intent);
            finish(); // Finish this activity para pag binack ng user di siya mapupunta sa login xd
        }
        else {
            // if signed out or bago palang sa app, tutuloy sa login
            //same logic as the original
            setContentView(R.layout.activity_main);

            // Instantiate components from xml
            email = findViewById(R.id.email);
            password = findViewById(R.id.password);
            btnlogin = findViewById(R.id.signUpButtn);
            DB = new DBHelper(this);

            // For clickable signup
            TextView textSpan = findViewById(R.id.toSignup);
            String text = "Don't have an account yet? Click here to register.";
            // 33 to 37 index
            SpannableString click = new SpannableString(text);

            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View view) {
                    openRegisterActivity();
                }
            };


            click.setSpan(clickableSpan, 33, 37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            // using LinkMovementMethod para gawing clickable yung sinet nating span text
            textSpan.setText(click);
            textSpan.setMovementMethod(LinkMovementMethod.getInstance());

            // Password visibility toggle button
            ImageView passwordToggle = findViewById(R.id.passwordToggle);

            passwordToggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Visible
                    if (isPasswordVisible) {
                        // Password is currently visible, so hide it
                        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        passwordToggle.setImageResource(R.drawable.eyeclose);
                    } else {
                        // Hidden
                        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        passwordToggle.setImageResource(R.drawable.eyeopen);
                    }

                    //Invert
                    isPasswordVisible = !isPasswordVisible;

                    //Kunin lahat ng size
                    password.setSelection(password.length());
                }
            });

            //Button to sign in
            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String user = email.getText().toString();
                    String pass = password.getText().toString();

                    if(user.equals("admin") && pass.equals("admin")){
                        System.out.println("Youre admin");
                        Toast.makeText(MainActivity.this, "Admin mode", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, admin.class);
                        startActivity(intent);
                    }
                    else if (user.equals("") || pass.equals("")) {
                        Toast.makeText(MainActivity.this, "Please input all fields", Toast.LENGTH_SHORT).show();
                    } else {
                        Boolean checkuserpass = DB.checkUsernamePassword(user, pass);


                        if (checkuserpass) {
                            // Save the username in SharedPreferences
                            SharedPreferences preferences = getSharedPreferences("MY_PREFS", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("last_username", user);
                            editor.apply();

                            // Retrieve fullname from the database
                            String fullname = DB.getFullname(user);
                            String email = DB.getEmail(user);
                            String gender = DB.getGender(user);

                            UserDetails userDetails = UserDetails.getInstance();
                            userDetails.setFullname(fullname);
                            userDetails.setEmail(email);
                            userDetails.setGender(gender);

                            // Change screen to homepage

                            // Show loading alert
                            ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                            progressDialog.setMessage("Logging in...");
                            progressDialog.setCancelable(false);
                            progressDialog.show();

                            // Delay for loading diba ang cool
                            new Handler().postDelayed(() -> {
                                progressDialog.dismiss();
                                Intent intent = new Intent(MainActivity.this, Home.class);
                                startActivity(intent);
                                Toast.makeText(MainActivity.this, "Signed in Successfully!", Toast.LENGTH_SHORT).show();
                            }, 3000); // 3000 milliseconds = 3 seconds
                        } else {
                            Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

    //Opening RegisterActivity
    public void openRegisterActivity() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // Para pag napindot ang back walang gagawin ang app :>
    }

    // method to check if the user is alr signed in
    private boolean isUserSignedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return sharedPreferences.contains("user_email");
    }

    // method to save preference
    private void saveUserEmailToPrefs(String userEmail) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_email", userEmail);
        editor.apply();
    }
}
