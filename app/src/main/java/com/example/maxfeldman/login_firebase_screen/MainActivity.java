package com.example.maxfeldman.login_firebase_screen;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuthException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText email_et;
    EditText password_et;
    Button register_btn;
    Button sign_btn;
    TextView sign_tv;
    private ProgressDialog progressDialog;
    private FirebaseAuthException firebaseAuthException;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email_et = findViewById(R.id.email_et);
        password_et = findViewById(R.id.password_et);
        register_btn = findViewById(R.id.register_btn);
        sign_btn = findViewById(R.id.sign_btn);
        sign_tv = findViewById(R.id.sign_tv);

        sign_tv.setOnClickListener(this);
        register_btn.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);


    }
    private void registerUser(){
        String  email = email_et.getText().toString().trim();
        String  password = email_et.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this, "please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering user....");
        progressDialog.show();
    }

    @Override
    public void onClick(View view) {
        if(view == register_btn){
            registerUser();
        }
        if(view == sign_tv){
            // will open login activity
        }
    }
}
