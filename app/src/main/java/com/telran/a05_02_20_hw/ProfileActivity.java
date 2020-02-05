package com.telran.a05_02_20_hw;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    public static final String AUTH = "AUTH";
    public static final String TOKEN = "Token";
    public static final String PROFILE = "PROFILE";

    TextView nameTxt, lastNameTxt, phoneTxt, addressTxt;
    EditText inputName, inputLastName, inputPhone, inputAddress;
    Button logoutBtn, editBtn, saveBtn;
    ViewGroup textWrapper, editWrapper;

    Profile currProfile;
    boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
        currProfile = getProfile();
        showTextMode();

        logoutBtn.setOnClickListener(v -> logout());
        saveBtn.setOnClickListener(v -> onSave());
        editBtn.setOnClickListener(v -> showEditMode());
    }

    private void init() {
        nameTxt = findViewById(R.id.nameTxt);
        lastNameTxt = findViewById(R.id.lastNameTxt);
        phoneTxt = findViewById(R.id.phoneTxt);
        addressTxt = findViewById(R.id.addressTxt);

        inputName = findViewById(R.id.inputName);
        inputLastName = findViewById(R.id.inputLastName);
        inputPhone = findViewById(R.id.inputPhone);
        inputAddress = findViewById(R.id.inputAddress);

        editWrapper = findViewById(R.id.editWrapper);
        textWrapper = findViewById(R.id.textWrapper);

        logoutBtn = findViewById(R.id.logoutBtn);
        saveBtn = findViewById(R.id.saveBtn);
        editBtn = findViewById(R.id.editBtn);
    }

    private void showTextMode() {
        isEdit = false;
        textWrapper.setVisibility(View.VISIBLE);
        editWrapper.setVisibility(View.GONE);
        logoutBtn.setVisibility(View.VISIBLE);
        editBtn.setVisibility(View.VISIBLE);
        saveBtn.setVisibility(View.GONE);

        nameTxt.setText(currProfile.name);
        lastNameTxt.setText(currProfile.lastName);
        phoneTxt.setText(currProfile.phone);
        addressTxt.setText(currProfile.address);
    }

    private void showEditMode() {
        isEdit = true;
        textWrapper.setVisibility(View.GONE);
        editWrapper.setVisibility(View.VISIBLE);
        logoutBtn.setVisibility(View.GONE);
        editBtn.setVisibility(View.GONE);
        saveBtn.setVisibility(View.VISIBLE);

        inputName.setText(currProfile.name);
        inputLastName.setText(currProfile.lastName);
        inputPhone.setText(currProfile.phone);
        inputAddress.setText(currProfile.address);
    }

    private void onSave() {
        currProfile.name = inputName.getText().toString();
        currProfile.lastName = inputLastName.getText().toString();
        currProfile.phone = inputPhone.getText().toString();
        currProfile.address = inputAddress.getText().toString();

        saveProfile(currProfile);
        showTextMode();
    }

    private void showDialog(){
        new AlertDialog.Builder(this)
                .setTitle("Save")
                .setMessage("Save before exit?")
                .setPositiveButton("Yes",(dialog, which) -> {
                    onSave();
                    finish();
                })
                .setNegativeButton("No",(dialog, which) -> finish())
                .setCancelable(false)
                .create()
                .show();
    }

    @Override
    public void onBackPressed() {
        if(isEdit){
            showDialog();
        }else {
            super.onBackPressed();
        }
    }

    private void logout() {
        clearToken();
        setResult(RESULT_OK);
        finish();
    }

    @Nullable
    private String getToken() {
        Log.d("MY_TAG", "getToken: ");
        return getSharedPreferences(AUTH, MODE_PRIVATE)
                .getString(TOKEN, null);
    }

    private Profile getProfile() {
        String str = getSharedPreferences(PROFILE, MODE_PRIVATE)
                .getString(getToken(), null);
        if (str != null) {
            return Profile.of(str);
        }
        return new Profile("", "", "", "");
    }

    private boolean saveProfile(Profile p) {
        return getSharedPreferences(PROFILE, MODE_PRIVATE)
                .edit()
                .putString(getToken(), p.toString())
                .commit();
    }

    private boolean clearToken() {
        return getSharedPreferences(AUTH, MODE_PRIVATE)
                .edit()
                .remove(TOKEN)
                .commit();
    }
}
