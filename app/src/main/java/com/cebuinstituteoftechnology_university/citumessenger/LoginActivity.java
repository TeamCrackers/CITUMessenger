package com.cebuinstituteoftechnology_university.citumessenger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.cebuinstituteoftechnology_university.citumessenger.BackgroundServices.AuthenticationService;
import com.cebuinstituteoftechnology_university.citumessenger.Config.AppConfig;
import com.cebuinstituteoftechnology_university.citumessenger.Interfaces.UserService;
import com.cebuinstituteoftechnology_university.citumessenger.Models.User;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class LoginActivity extends AppCompatActivity  {

    Retrofit retrofit;
    UserService userService;
    AuthenticationReceiver myReceiver;
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
        myReceiver = new AuthenticationReceiver();
        IntentFilter filter = new IntentFilter(AuthenticationService.ACTION_CHECK_ACCESS_TOKEN);
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,filter);
        User n = new User("ian","osias");
        AuthenticationService.startAccessTokenCheck(this,n);
        /*
        AsyncTask s = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                User user = new User("username","password");
                try {
                    Response<User> st  = userService.login(user).execute();
                    User me = st.body();
                    if(me!=null){
                        Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
                        LoginActivity.this.startActivity(intent);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return  null;
            }
        };
        s.execute();*/



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
    }
    public class AuthenticationReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            Toast.makeText(getBaseContext(),message,Toast.LENGTH_SHORT).show();
        }
    }
}

