package com.cebuinstituteoftechnology_university.citumessenger.BackgroundServices;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.cebuinstituteoftechnology_university.citumessenger.Models.User;


public class AuthenticationService extends IntentService {

    public static final String ACTION_CHECK_ACCESS_TOKEN = "CHECK_TOKEN";
    private static final String EXTRA_USERID = "userid";
    private static final String EXTRA_PASSWORD = "password";
    private static final String EXTRA_CURRENT_TOKEN = "token";

    public static void startAccessTokenCheck(Context context,User user ){
        Intent intent = new Intent(context, AuthenticationService.class);
        intent.setAction(ACTION_CHECK_ACCESS_TOKEN);
        intent.putExtra(EXTRA_USERID,user.getId());
        intent.putExtra(EXTRA_PASSWORD,user.getPassword());
        intent.putExtra(EXTRA_CURRENT_TOKEN,user.getAccessToken());
        context.startService(intent);
    }

    public AuthenticationService() {
        super("AuthenticationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CHECK_ACCESS_TOKEN.equals(action)) {
                final String userid = intent.getStringExtra(EXTRA_USERID);
                final String password = intent.getStringExtra(EXTRA_PASSWORD);
                final String currentToken = intent.getStringExtra(EXTRA_CURRENT_TOKEN);
                handleActionCheckToken(userid,password,currentToken);
            }
        }
    }

    private void handleActionCheckToken(String usernid,String password,String currentToken){
        Intent intent = new Intent(ACTION_CHECK_ACCESS_TOKEN);
        intent.putExtra("message","Nice ka one");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }
}
