package com.cebuinstituteoftechnology_university.citumessenger.BackgroundServices;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.cebuinstituteoftechnology_university.citumessenger.Config.AppConfig;
import com.cebuinstituteoftechnology_university.citumessenger.Events.AuthenticationEvent;
import com.cebuinstituteoftechnology_university.citumessenger.Events.MessageEvent;
import com.cebuinstituteoftechnology_university.citumessenger.APIRestInterfaces.UserService;
import com.cebuinstituteoftechnology_university.citumessenger.Models.User;

import java.io.IOException;

import de.greenrobot.event.EventBus;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class AuthenticationService extends IntentService {

    public static final String ACTION_CHECK_ACCESS_TOKEN = "CHECK_TOKEN";
    public static final String ACTION_LOGIN = "USER_LOGIN";
    public static final String ACTION_REGISTER = "USER_REGISTER";

    private static final String EXTRA_USERID = "userid";
    private static final String EXTRA_USER_OBJECT = "user";
    private static final String EXTRA_PASSWORD = "password";
    private static final String EXTRA_CURRENT_TOKEN = "token";

    public static final String FLAG_SUCCESSFUL = "SUCCESS";
    public static final String FLAG_FAILED = "FAILED";


    private static UserService userService;

    public static void startAccessTokenCheck(Context context,User user ){
        Intent intent = new Intent(context, AuthenticationService.class);
        intent.setAction(ACTION_CHECK_ACCESS_TOKEN);
        intent.putExtra(EXTRA_USERID,user.getId());
        intent.putExtra(EXTRA_PASSWORD, user.getPassword());
        intent.putExtra(EXTRA_CURRENT_TOKEN,user.getAccessToken());
        context.startService(intent);
    }
    public static void startActionLogin(Context context,User user ){
        Intent intent = new Intent(context, AuthenticationService.class);
        intent.setAction(ACTION_LOGIN);
        intent.putExtra(EXTRA_USER_OBJECT,user);
        context.startService(intent);
    }

    public static void startActionRegister(Context context,User user ){
        Intent intent = new Intent(context, AuthenticationService.class);
        intent.setAction(ACTION_REGISTER);
        intent.putExtra(EXTRA_USER_OBJECT,user);
        context.startService(intent);
    }

    public AuthenticationService() {
        super("AuthenticationService");
    }

    private static void startUserService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.host + ":" + AppConfig.port + "")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            if(userService==null)
                startUserService();
            final String action = intent.getAction();
            if (ACTION_CHECK_ACCESS_TOKEN.equals(action)) {
                final String userid = intent.getStringExtra(EXTRA_USERID);
                final String password = intent.getStringExtra(EXTRA_PASSWORD);
                final String currentToken = intent.getStringExtra(EXTRA_CURRENT_TOKEN);
                handleActionCheckToken(userid,password,currentToken);
            }
            else if(ACTION_LOGIN.equals(action)){
                handleActionLogin((User)intent.getSerializableExtra(EXTRA_USER_OBJECT));
            }
            if(ACTION_REGISTER.equals(action)){
                handleActionRegister((User) intent.getSerializableExtra(EXTRA_USER_OBJECT));
            }
        }
    }

    private void handleActionLogin(User user){
        try {
            Response<User> response  = userService.login(user).execute();
            AuthenticationEvent event = new AuthenticationEvent();
            User currentUser = response.body();
            if(currentUser!=null && currentUser.getErrorMessage() == null) {
                event.setEventType(AuthenticationEvent.LOGIN_EVENT);
                event.setMessage(AuthenticationEvent.LOGIN_SUCCESS);
                event.setUser(currentUser);
            }
            else{
                event.setEventType(AuthenticationEvent.LOGIN_EVENT);
                event.setMessage(AuthenticationEvent.LOGIN_WRONG_CREDENTIALS_ERROR);
            }
            EventBus.getDefault().post(event);

        } catch (IOException e) {
            EventBus.getDefault().post(new MessageEvent(FLAG_FAILED));
        }
    }

    private void handleActionRegister(User user){
        try {
            Response<Boolean> response  = userService.register(user).execute();
            if(response.body()) {
                EventBus.getDefault().post(new MessageEvent(FLAG_SUCCESSFUL));
            }

        } catch (IOException e) {
            EventBus.getDefault().post(new MessageEvent(FLAG_FAILED));
        }
    }


    private void handleActionCheckToken(String usernid,String password,String currentToken){


    }
}
