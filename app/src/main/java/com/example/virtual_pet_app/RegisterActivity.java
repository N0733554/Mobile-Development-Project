package com.example.virtual_pet_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private Button button;
    private EditText email, username, password, password2;
    private TextView toLogin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        button = (Button) findViewById(R.id.button_register);
        username = (EditText) findViewById(R.id.r_username);
        email = (EditText) findViewById(R.id.r_email);
        password = (EditText) findViewById(R.id.r_password);
        password2 = (EditText) findViewById(R.id.r_password2);
        toLogin = (TextView) findViewById(R.id.t_goToLogin);

        mAuth = FirebaseAuth.getInstance();

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String l_email = email.getText().toString();
                String l_username = username.getText().toString();
                String l_password = password.getText().toString();
                String l_password2 = password2.getText().toString();

                if(l_email.isEmpty())
                {
                    username.setError("Field Empty");
                    return;
                }
                if(l_username.isEmpty())
                {
                    username.setError("Field Empty");
                    return;
                }
                if(l_password.isEmpty())
                {
                    password.setError("Field Empty");
                    return;
                }
                if(l_password2.isEmpty())
                {
                    password.setError("Field Empty");
                    return;
                }
                if(!l_password.equals(l_password2))
                {
                    password.setError("Passwords do not match");
                    return;
                }

                createAccount(l_email, l_username, l_password);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check is user is signed in

        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);


    }

    protected void createAccount(String email, String username, String password)
    {
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful())
                        {
                            // If sign in fails, display a message to the user
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "createUserWithEmail:failure", Toast.LENGTH_SHORT).show();

                            //updateUI(null);

                        } else {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            //updateUI(user);
                        }

                        // ...

                    }
                });
    }
}
