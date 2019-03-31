package com.example.cinematimisoara;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Button btnRegister,btnLogin;
    EditText emailET, passwordET;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRegister = findViewById(R.id.registerbutton);
        btnLogin = findViewById(R.id.loginButton);
        passwordET = findViewById(R.id.editTextPasswordLogin);
        emailET = findViewById(R.id.editTextEmailLogin);
        firebaseAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailET.getText().toString().trim();
                String password = passwordET.getText().toString().trim();

//                FirebaseUser fbuser = firebaseAuth.getCurrentUser();
//                Toast.makeText(MainActivity.this,""+fbuser.getEmail(),Toast.LENGTH_LONG).show();
                firebaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(MainActivity.this,"Login succesful!",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(MainActivity.this,NextActivity.class));
                                }
                                else{
                                    Toast.makeText(MainActivity.this,"Could not login...",Toast.LENGTH_LONG).show();
                                }

                            }
                        });

            }
        });



    }

}
