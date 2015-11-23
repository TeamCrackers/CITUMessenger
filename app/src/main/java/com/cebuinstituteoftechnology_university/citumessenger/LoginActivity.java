package com.cebuinstituteoftechnology_university.citumessenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.registerButton)
    public void startRegisterActivty(){
        Intent intent = new Intent(this, RegisterActivity.class);
        this.startActivity(intent);
    }

}

