package com.example.pertemuan5_recycleview;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pertemuan5_recycleview.databinding.ActivityLoginBinding;
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    MyDBHelper MyDBHelper;
    private ActivityLoginBinding binding;
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//setup view binding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        session = new SessionManager(getApplicationContext());
        if (session.isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);startActivity(intent);
            finish();
        }
        session = new SessionManager(getApplicationContext());
        MyDBHelper = new MyDBHelper(this);
        SQLiteDatabase sqLiteDatabase = MyDBHelper.getWritableDatabase();
        binding.Signinbuttonid.setOnClickListener(this);
        binding.SignUpHerebuttonid.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        String username = binding.signinusernameEditText.getText().toString();
        String password = binding.signinpasswordEditText.getText().toString();
        if (v.getId() == R.id.Signinbuttonid) {
            Boolean result = MyDBHelper.findPassword(username, password);
            if (result == true) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                session.setLogin(true);
                finish();
            } else {
                Toast.makeText(this, "username and password didn`t match", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.SignUpHerebuttonid) {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
    }
}