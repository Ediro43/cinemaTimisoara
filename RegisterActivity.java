package com.example.cinematimisoara;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText emailET, passwordET,nameET,adressET,phoneET;
    private Button register;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailET = findViewById(R.id.editTextEmail);
        passwordET = findViewById(R.id.editTextID);
        nameET= findViewById(R.id.editTextName);
        adressET = findViewById(R.id.editTextAdress);
        phoneET = findViewById(R.id.editTextPhoneNumber);
        register = findViewById(R.id.register);
        FirebaseApp.initializeApp(RegisterActivity.this);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailET.getText().toString().trim();
                final String password = passwordET.getText().toString().trim();
                final String name = nameET.getText().toString().trim();
                final String address = adressET.getText().toString().trim();
                final String phone = phoneET.getText().toString().trim();

                if (!TextUtils.isEmpty(email) &&
                        !TextUtils.isEmpty(password) &&
                        !TextUtils.isEmpty(name) &&
                        !TextUtils.isEmpty(address) &&
                        !TextUtils.isEmpty(phone)) {
                    //daca nici una din astea nu e

                    firebaseAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        //daca s-a inregistrat in BD corect atunci facem astea
                                        String id = databaseReference.push().getKey();

                                        User usr = new User(id,name,email,password,address,phone);

                                        databaseReference.child(id).setValue(usr);
                                        Toast.makeText(RegisterActivity.this,"You just created your account !", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(RegisterActivity.this,MainActivity.class);
                                        startActivity(i);
                                    }
                                    Toast.makeText(RegisterActivity.this,"Couldn't create account , try again.", Toast.LENGTH_LONG).show();

                                }
                            });




                }
                else{
                    Toast.makeText(RegisterActivity.this,"You didn't complete all fields !",Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
    }
}
