package com.example.helphealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helphealth.DBHelper;
import com.example.helphealth.MainActivity;
import com.example.helphealth.R;

public class Register extends AppCompatActivity {
    EditText email, password, fullname;
    Button signup;
    RadioButton radioButton;
    RadioGroup radioGroup;
    DBHelper DB;
    ImageView passwordToggle;

    //Variable to track password visibility. boolean para maangas yung condition tatawagin nalang yung variable name B)
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Instantiation lahat ng components sa xml sa loob ng onCreate()
        TextView textSpan = findViewById(R.id.tosignin);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        fullname = findViewById(R.id.fullName);
        signup = findViewById(R.id.signUpButtn);
        radioGroup = findViewById(R.id.genderRadioGroup);
        DB = new DBHelper(this);

        // Onclick listener sa radio buttons ng gender
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkButton(group.findViewById(checkedId));
            }
        });
        //initialize the passwordToggle ImageView
        passwordToggle = findViewById(R.id.passwordToggle);

        //TextWatcher to see update the new input and still show the updated version, diba angas?
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                //Toggle password visibility based on the text length
                if (editable.length() > 0) {
                    passwordToggle.setVisibility(View.VISIBLE);
                    passwordToggle.setImageResource(R.drawable.eyeopen); // Change to eyeopen image
                } else {
                    passwordToggle.setVisibility(View.INVISIBLE);
                }
            }
        });
        //Set an OnClickListener for the password toggle ImageView
        passwordToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toggle password visibility
                if (isPasswordVisible) {
                    //Visible to hide
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordToggle.setImageResource(R.drawable.eyeclose);
                } else {
                    //Hide to visible
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordToggle.setImageResource(R.drawable.eyeopen);
                }

                // Invert
                isPasswordVisible = !isPasswordVisible;

                password.setSelection(password.length());
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = email.getText().toString();
                String pass = password.getText().toString();
                String full = fullname.getText().toString();
                String gender = getSelectedGender();

                if (user.equals("") || full.equals("") || gender.equals("")) {
                    Toast.makeText(Register.this, "Please enter all the fields!", Toast.LENGTH_SHORT).show();
                } else {
                    // Check for gender selection
                    if (gender.isEmpty()) {
                        Toast.makeText(Register.this, "Select a gender", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // Email validation using regex kasi im tryna be act cool B)
                    if (!user.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                        Toast.makeText(Register.this, "Incorrect email!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Password length check baka ma-hack ka, medj may kulang kulang ka pa naman
                    if (pass.length() < 8) {
                        Toast.makeText(Register.this, "Password must be at least 8 characters.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Password: meron dapat CAPITAL LETTER
                    if (!pass.matches(".*[A-Z].*")) {
                        Toast.makeText(Register.this, "Password must contain at least one capital letter.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Password NUMERICAL NUMBERS
                    if (!pass.matches(".*\\d.*")) {
                        Toast.makeText(Register.this, "Password must contain at least one number.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Boolean checkuser = DB.checkUsername(user);
                    if (checkuser == false) {
                        Boolean insert = DB.insertData(user, pass, full, gender);
                        if (insert) {


                            // Show loading alert
                            ProgressDialog progressDialog = new ProgressDialog(Register.this);
                            progressDialog.setMessage("Registering...");
                            progressDialog.setCancelable(false);
                            progressDialog.show();

                            // Delay for loading diba ang cool
                            new Handler().postDelayed(() -> {
                                progressDialog.dismiss();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(Register.this, "Registered Successfully!!", Toast.LENGTH_SHORT).show();
                            }, 4000); // 3000 milliseconds = 3 seconds
                        } else {
                            Toast.makeText(Register.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Register.this, "User already exists! Please sign in.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Clickable sign-in text: ito maangas kong natutunan thru out the development
        String text = "Already have an account? Click here to Sign In";

        SpannableString click = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                //Toast.makeText(Register.this, "Login", Toast.LENGTH_SHORT).show();
                openLoginActivity();
            }
        };
        click.setSpan(clickableSpan, 31, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textSpan.setText(click);
        textSpan.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private String getSelectedGender() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(selectedId);
        return radioButton != null ? radioButton.getText().toString() : "";
    }

    public void checkButton(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioMale:
                if (checked) {
                    Toast.makeText(Register.this, "You chose Male", Toast.LENGTH_SHORT).show();
                    radioButton = findViewById(R.id.radioMale);
                    break;
                }
            case R.id.radioFemale:
                if (checked) {
                    Toast.makeText(Register.this, "You chose Female", Toast.LENGTH_SHORT).show();
                    radioButton = findViewById(R.id.radioFemale);
                    break;
                }
        }
    }

    @Override
    public void onBackPressed() {
        // Para pag napindot ang back walang gagawin ang app :>
    }
}