package com.cebuinstituteoftechnology_university.citumessenger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.CardView;
import android.support.v7.widget.AppCompatAutoCompleteTextView;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {
    @Bind(R.id.idNummber)
    AppCompatAutoCompleteTextView idNumber;
    @Bind(R.id.firstName)
    AppCompatAutoCompleteTextView firstName;
    @Bind(R.id.lastName)
    AppCompatAutoCompleteTextView lastName;
    @Bind(R.id.emailAddress)
    AppCompatAutoCompleteTextView email;
    @Bind(R.id.password)
    AppCompatEditText password;
    @Bind(R.id.confirmPassword)
    AppCompatEditText conf_password;
    @Bind(R.id.previewCard)
    CardView previewCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.registerButton)
    public void register(){

    }
    @OnClick(R.id.cancelButton)
    public void cancel(){
        this.finish();
    }



}
