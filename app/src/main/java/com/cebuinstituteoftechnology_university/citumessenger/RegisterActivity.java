package com.cebuinstituteoftechnology_university.citumessenger;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.cebuinstituteoftechnology_university.citumessenger.BackgroundServices.AuthenticationService;
import com.cebuinstituteoftechnology_university.citumessenger.Events.MessageEvent;
import com.cebuinstituteoftechnology_university.citumessenger.APIRestInterfaces.UserService;
import com.cebuinstituteoftechnology_university.citumessenger.Models.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import de.greenrobot.event.EventBus;

public class RegisterActivity extends AppCompatActivity {
    @Bind(R.id.idTextBox)
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
    @Bind(R.id.nickName)
    AppCompatAutoCompleteTextView nickName;

    @Bind(R.id.idLabel)
    TextView idLabel;
    @Bind(R.id.firstNameLabel)
    TextView firstNameLabel;
    @Bind(R.id.lastNameLabel)
    TextView lastNameLabel;
    @Bind(R.id.nickNameLabel)
    TextView nickNameLabel;

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
            user.setPassword(password.getText().toString());
            AuthenticationService.startActionRegister(this,user);
        }
    }
    @OnClick(R.id.cancelButton)
    public void cancel(){
        this.finish();
    }

    public void onEvent(MessageEvent messageEvent){
        if(messageEvent.getMessage().contentEquals(AuthenticationService.FLAG_SUCCESSFUL)){
            Snackbar snack = Snackbar.make(this.findViewById(R.id.registerRootLayout), "Successfully registered user.", Snackbar.LENGTH_INDEFINITE);
            snack.setAction("Go Back", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            snack.show();
        }

        else
            Toast.makeText(this,"Unsuccessful Registration",Toast.LENGTH_SHORT).show();

    }

    @OnTextChanged(R.id.idTextBox)
    public void schoolIdChange(){
        idLabel.setText(this.idNumber.getText().toString());
    }

    @OnTextChanged(R.id.firstName)
    public void firstNameChange(){
        firstNameLabel.setText(this.firstName.getText().toString());
    }
    @OnTextChanged(R.id.lastName)
    public void lastNameChange(){
       lastNameLabel.setText(this.lastName.getText().toString());
    }

    @OnTextChanged(R.id.nickName)
    public void nickNameChange(){
        nickNameLabel.setText(this.nickName.getText().toString());
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
