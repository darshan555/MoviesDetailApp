package com.example.netflixmovieapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.netflixmovieapp.R;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameText,passwordText;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usernameText.getText().toString().isEmpty() || passwordText.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please Fill Your Username or Password", Toast.LENGTH_SHORT).show();
                } else if ((usernameText.getText().toString().equals("darshan") && passwordText.getText().toString().equals("darshan123"))) {
                    startActivity(new Intent( LoginActivity.this,MainActivity.class));
                }
            }
        });
    }

    private void initView() {
        usernameText =findViewById(R.id.usernameET);
        passwordText =findViewById(R.id.passwordET);
        loginBtn =findViewById(R.id.loginBTN);
    }
}