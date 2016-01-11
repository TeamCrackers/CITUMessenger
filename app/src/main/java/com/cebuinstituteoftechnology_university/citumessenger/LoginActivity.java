package com.cebuinstituteoftechnology_university.citumessenger;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cebuinstituteoftechnology_university.citumessenger.Config.AppConfig;
import com.cebuinstituteoftechnology_university.citumessenger.Interfaces.UserService;
import com.cebuinstituteoftechnology_university.citumessenger.Models.User;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class LoginActivity extends AppCompatActivity  {

    Retrofit retrofit;
    UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.host + ":" + AppConfig.port + "")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);
    }

    @OnClick(R.id.registerButton)
    public void startRegisterActivty(){
        Intent intent = new Intent(this, RegisterActivity.class);
        this.startActivity(intent);
    }

    @OnClick(R.id.email_sign_in_button)
    public void startChatActivity() {
        AsyncTask s = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                User user = new User("username","password");
                try {
                    Response<User> st  = userService.login(user).execute();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return  null;
            }
        };
        s.execute();



            /*
            Call<String> res = userService.login(user);
            res.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Response<String> response, Retrofit retrofit) {
                    String s = response.toString();
                }

                @Override
                public void onFailure(Throwable t) {
                    String s = t.toString();
                }
            });*/

        Intent intent = new Intent(this, ChatActivity.class);

        this.startActivity(intent);
    }

}

