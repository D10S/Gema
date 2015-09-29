package com.org.gema.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.d10s.gema.R;
import com.org.gema.activities.EscuelaPickerActivity;
import com.org.gema.activities.ValidateEmail;
import com.org.gema.activities.ValidatePassword;
import com.org.gema.networking.PerformLogIn;
import com.org.gema.networking.SignIn;
import com.org.gema.networking.SignUp;
import com.org.gema.security.MD5Hash;

import java.util.Calendar;

/**
 * Created by jcapiz on 28/09/15.
 */
public class Registrar extends Fragment {

    private static final String ARG_SECTION_NUMBER = "arg_section_number";
    Activity activity;
    public static final int ESCUELA_PICKER = 2;
    EditText userKeyValue;
    boolean isMailCorrect;
    EditText userPasswordValue;
    EditText userMailValue;
    EditText userNameValue;
    EditText userPassword;
    TextView userEscuelaValue;
    DatePicker userDateOfBirthValue;
    PerformLogIn login;
    ValidatePassword passwordWatcher;

    public static Registrar newInstance(int sectionNumber) {
        Registrar fragment = new Registrar();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        super.onCreateView(inflater,parent,savedInstanceState);
        View rootView = inflater.inflate(R.layout.registrar,parent,false);
        userKeyValue = (EditText)rootView.findViewById(R.id.input_user_key_value);
        userPassword = (EditText)rootView.findViewById(R.id.input_user_password);
        userMailValue = (EditText)rootView.findViewById(R.id.input_user_mail_value);
        userNameValue = (EditText)rootView.findViewById(R.id.input_user_name_value);
        userPasswordValue = (EditText)rootView.findViewById(R.id.input_user_password_value);
        userDateOfBirthValue = (DatePicker)rootView.findViewById(R.id.input_user_date_of_birth);
        userEscuelaValue = (TextView)rootView.findViewById(R.id.input_user_escuela_value);
        passwordWatcher = new ValidatePassword(userPasswordValue);
        userPasswordValue.addTextChangedListener(passwordWatcher);
        userMailValue.addTextChangedListener(new ValidateMailAddress());
        login = new PerformLogIn();
        userEscuelaValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchEscuelaPicker();
            }
        });
        return rootView;
    }

    public void launchEscuelaPicker(){
        String[] escuelas = {"UPIITA","UPIICSA"};
        Intent i = new Intent(activity,EscuelaPickerActivity.class);
        i.putExtra("escuelas", escuelas);
        startActivityForResult(i, ESCUELA_PICKER);
    }

    public void completeSignIn(View view) {
        new SignIn(activity).execute(userKeyValue.getText().toString(), new MD5Hash().makeHash(userPassword.getText().toString()));
    }

    public void completeSignUp(View view){
        if( isMailCorrect &&  passwordWatcher.isValid
                &&
                new java.util.GregorianCalendar().get(Calendar.YEAR)
                        - userDateOfBirthValue.getYear() > 5){
            new SignUp(activity).execute(userMailValue.getText().toString()
                    ,userNameValue.getText().toString()
                    ,new MD5Hash().makeHash(userPasswordValue.getText().toString())
                    ,userDateOfBirthValue.getDayOfMonth()
                    +"/"+userDateOfBirthValue.getMonth()
                    +"/"+userDateOfBirthValue.getYear());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == activity.RESULT_OK){
            switch (requestCode){
                case ESCUELA_PICKER:
                    userEscuelaValue.setText(data.getStringExtra("escuela"));
                    break;
            }
        }
    }

    private class ValidateMailAddress implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            if( !new ValidateEmail().validate(s.toString()) ){
                userMailValue.setBackgroundColor(Color.rgb(250, 125, 125));
                if( isMailCorrect )
                    isMailCorrect = false;
            }else{
                userMailValue.setBackgroundColor(Color.TRANSPARENT);
                isMailCorrect = true;
            }
        }
    }
}
