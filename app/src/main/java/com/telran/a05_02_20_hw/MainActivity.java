package com.telran.a05_02_20_hw;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String AUTH = "AUTH";
    public static final String TOKEN = "Token";
    EditText inputEmail, inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String token = getToken();
        if (token != null) {
            showProfile();
        }
        setContentView(R.layout.activity_main);

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);

        findViewById(R.id.loginBtn).setOnClickListener(v -> {
            saveToken(inputEmail.getText().toString(),
                    inputPassword.getText().toString());
            showProfile();
        });
    }


    private void showProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivityForResult(intent, 1);
    }

    private boolean saveToken(@NonNull String email, @NonNull String password) {
        return getSharedPreferences(AUTH, MODE_PRIVATE)
                .edit()
                .putString(TOKEN, email + password)
                .commit();
    }

    @Nullable
    private String getToken() {
        return getSharedPreferences(AUTH, MODE_PRIVATE)
                .getString(TOKEN, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_CANCELED && requestCode == 1) {
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
