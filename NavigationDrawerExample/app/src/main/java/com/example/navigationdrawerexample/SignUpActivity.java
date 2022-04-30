package com.example.navigationdrawerexample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

        TextView existingAccount;
        EditText Name, Email, Password, confirmPassword;
        Button BtnSignup;
        String emailPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        ProgressDialog progressDialog;

        FirebaseAuth Auth;
        FirebaseUser User;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if(BuildConfig.DEBUG){

                StrictMode.enableDefaults();
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build();
                StrictMode.setThreadPolicy(policy);

                StrictMode.VmPolicy VmPolicy= new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build();
                StrictMode.setVmPolicy(VmPolicy);

            }
            setContentView(R.layout.activity_sign_up);
            existingAccount = findViewById(R.id.alreadyHaveAnAccount);

            Name = findViewById(R.id.person);
            Email = findViewById(R.id.name);
            Password = findViewById(R.id.pass);
            confirmPassword = findViewById(R.id.confirmPass);
            BtnSignup = findViewById(R.id.BtnSignup);
            progressDialog = new ProgressDialog(this);
            Auth = FirebaseAuth.getInstance();
            User = Auth.getCurrentUser();

            existingAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                }
            });

            BtnSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PerformAuthentication();
                }
            });
        }

        private void PerformAuthentication() {
            System.out.print("Inside PerformAuthentication");

            String name = Name.getText().toString();
            String email = Email.getText().toString();
            String password = Password.getText().toString();
            String confirmPass = confirmPassword.getText().toString();


            if (!email.matches(emailPattern)) {
                Email.setError("Please provide valid Username");
            } else if (password.isEmpty() || password.length() < 6) {
                Password.setError("Password has to be at least 6 characters long");
            } else if (!password.equals(confirmPass)) {
                confirmPassword.setError("Password mismatch");
            } else if (name.isEmpty()) {
                Name.setError("Please provide Full Name");
            } else {
                progressDialog.setMessage("One moment please...");
                progressDialog.setTitle("Signup");
                progressDialog.setCanceledOnTouchOutside(true);
                progressDialog.show();
                System.out.print("Before createUserWithEmailAndPassword");

                Auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    System.out.print("Inside createUserWithEmailAndPassword");
                                    Users users = new Users(name, email);

                                  //  FirebaseDatabase.getInstance().getReference("Users")
                                      FirebaseDatabase.getInstance().getReferenceFromUrl("https://navigationdrawerexample-e5fc6-default-rtdb.firebaseio.com/Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                progressDialog.dismiss();
                                                sendUserToNextActivity();
                                                Toast.makeText(SignUpActivity.this, "Thanks for signing up!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                progressDialog.dismiss();
                                                Toast.makeText(SignUpActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        private void sendUserToNextActivity() {
                                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    });
                                }
                            }
                        });
            }
        }
    }

