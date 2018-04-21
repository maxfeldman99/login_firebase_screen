package com.example.maxfeldman.login_firebase_screen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText email_et;
    EditText password_et;
    Button register_btn;
    Button log_in_btn;
    TextView sign_tv;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email_et = findViewById(R.id.email_et);
        password_et = findViewById(R.id.password_et);
        progressBar = findViewById(R.id.progress_bar);

        register_btn = findViewById(R.id.register_btn);
        sign_tv = findViewById(R.id.sign_tv);
        log_in_btn = findViewById(R.id.log_in_btn);


        sign_tv.setOnClickListener(this);////
        register_btn.setOnClickListener(this);
        log_in_btn.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();



    }
    private void createAccount(){
        String  email = email_et.getText().toString().trim();
        String  password = password_et.getText().toString().trim();

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

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_et.setError("Please enter a valid email");
            return;
        }


        if(password.length() < 6){
            password_et.setError("minimum password length should be at least 6");
            password_et.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "user registered successful", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  // this will clear our activities , otherwise we come back to our sign up activity on BackPressed button
                    startActivity(intent);

                }
                else{
                   if(task.getException() instanceof FirebaseAuthUserCollisionException){
                       Toast.makeText(getApplicationContext(),"you are already registered ",Toast.LENGTH_SHORT).show();
                   }
                   else{
                       Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                   }

                }
            }
        });

       // progressDialog.setMessage("Registering user....");
        //progressDialog.show();
    }





    @Override
    public void onClick(View view) {
      switch (view.getId()){
          case R.id.register_btn:
              createAccount();
              break;

          case R.id.log_in_btn:
              startActivity(new Intent(this, SignUpActivity.class));

              break;


      }
    }



}
