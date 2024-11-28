package com.example.try_sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etUsername, etPassword;
    Button btnRegister, btnLogin;
    TextView tvAllRegisteredUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        tvAllRegisteredUser = findViewById(R.id.tvAllRegisteredUsers);
        UserDAO userDao = new UserDAO();
        String strAllUsers = userDao.convertUsersToStr(userDao.getAllUsers(this));
        tvAllRegisteredUser.setText("All Registered Users:\n"+strAllUsers);
    }

    @Override
    public void onClick(View v) {
        String strUsername = etUsername.getText().toString();
        String strPassword = etPassword.getText().toString();
        if(v.getId()==R.id.btnRegister){

            UserDAO userDao = new UserDAO();
            userDao.addUser(this,new User(strUsername,strPassword));

            String strAllUsers = userDao.convertUsersToStr(userDao.getAllUsers(this));
            tvAllRegisteredUser.setText("All Registered Users:\n"+strAllUsers);

        }else if(v.getId()==R.id.btnLogin){

            UserDAO userDao = new UserDAO();
            User resultUser = userDao.getSpecificUser(this,strUsername,strPassword);

            if(resultUser!=null){
                Intent niat = new Intent(this,MainActivity2.class);
                niat.putExtra("loginUser",resultUser.username);
                startActivity(niat);
            }

        }
    }


}