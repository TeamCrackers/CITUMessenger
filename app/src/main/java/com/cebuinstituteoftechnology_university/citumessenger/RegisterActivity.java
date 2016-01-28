package com.cebuinstituteoftechnology_university.citumessenger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.widget.Toast;


import com.cebuinstituteoftechnology_university.citumessenger.BackgroundServices.AuthenticationService;
import com.cebuinstituteoftechnology_university.citumessenger.Events.MessageEvent;
import com.cebuinstituteoftechnology_university.citumessenger.APIRestInterfaces.UserService;
import com.cebuinstituteoftechnology_university.citumessenger.Models.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

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

    UserService userService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @OnClick(R.id.registerButton)
    public void register(){
        if(conf_password.getText().toString().contentEquals(password.getText().toString())){
            User user = new User(
                                idNumber.getText().toString(),
                                password.getText().toString()
                                );
            user.setFirstName(firstName.getText().toString());
            user.setLastName(lastName.getText().toString());
            user.setEmail(email.getEditableText().toString());
            user.setSchoolId(idNumber.getEditableText().toString());
            AuthenticationService.startActionRegister(this,user);
        }
    }
    @OnClick(R.id.cancelButton)
    public void cancel(){
        this.finish();
    }

    public void onEvent(MessageEvent messageEvent){
        if(messageEvent.getMessage().contentEquals(AuthenticationService.FLAG_SUCCESSFUL))
            finish();
        else
            Toast.makeText(this,"Unsuccessful Registration",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
