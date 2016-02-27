package com.cebuinstituteoftechnology_university.citumessenger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.cebuinstituteoftechnology_university.citumessenger.BackgroundServices.AuthenticationService;
import com.cebuinstituteoftechnology_university.citumessenger.Config.AppConfig;
import com.cebuinstituteoftechnology_university.citumessenger.APIRestInterfaces.UserService;
import com.cebuinstituteoftechnology_university.citumessenger.Events.AuthenticationEvent;
import com.cebuinstituteoftechnology_university.citumessenger.Models.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;


public class LoginActivity extends AppCompatActivity  {

    Retrofit retrofit;
    UserService userService;
    AuthenticationReceiver myReceiver;
    @Bind(R.id.schoolId)
    TextView schoolId;
    @Bind(R.id.password)
    TextView password;

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
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onEvent(final AuthenticationEvent event){
        switch (event.getEventType()){
            case AuthenticationEvent.LOGIN_EVENT :
                        if(event.getMessage().equals(AuthenticationEvent.LOGIN_SUCCESS)) {
                            HomeActivity.CURRENT_USER = event.getUser();
                            finish();
                        }else
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Snackbar.make(findViewById(R.id.loginRootLayout), event.getMessage(), Snackbar.LENGTH_LONG).show();
                                    schoolId.setText("");
                                    password.setText("");
                                    schoolId.requestFocus();
                                }
                            });

                        }
                        break;

        }

    }

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.finish();
            return;
        }
        else { Toast.makeText(getBaseContext(), AppConfig.DOUBLE_BACK_PRESS_TO_EXIT_MESSAGE, Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }

    @OnClick(R.id.registerButton)
    public void startRegisterActivty(){
        Intent intent = new Intent(this, RegisterActivity.class);
        this.startActivity(intent);
    }

    @OnClick(R.id.sign_in_button)
    public void loginButton_Clicked(){
        AuthenticationService.startActionLogin(this,new User(schoolId.getText().toString(),password.getText().toString()));

    }






    public void startChatActivity() {
        /*myReceiver = new AuthenticationReceiver();
        IntentFilter filter = new IntentFilter(AuthenticationService.ACTION_CHECK_ACCESS_TOKEN);
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, filter);
        User n = new User("ian","osias");
        AuthenticationService.startAccessTokenCheck(this,n);
        Intent test = new Intent(this,ChatActivity.class);
        startActivity(test);
*/
        HomeActivity.CURRENT_USER = new User("Ian","osias");
        Intent test = new Intent(this,HomeActivity.class);
        startActivity(test);
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

