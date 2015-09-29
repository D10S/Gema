package com.org.gema.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.d10s.gema.R;
import com.org.gema.activities.ValidatePassword;
import com.org.gema.networking.PerformLogIn;

/**
 * Created by jcapiz on 28/09/15.
 */
public class Ingresar extends Fragment {

    private static final String ARG_SECTION_NUMBER = "arg_section_number";
    Activity activity;
    public static final int ESCUELA_PICKER = 2;
    EditText userKeyValue;
    EditText userPasswordValue;
    EditText userMailValue;
    EditText userNameValue;
    EditText userPassword;
    TextView userEscuelaValue;
    DatePicker userDateOfBirthValue;
    RelativeLayout formsContainer;
    ScrollView signInContainer;
    ScrollView signUpContainer;
    PerformLogIn login;
    ValidatePassword passwordWatcher;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.activity = activity;
    }

    public static Ingresar newInstance(int sectionNumber) {
        Ingresar fragment = new Ingresar();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View rootView = (View)inflater.inflate(R.layout.ingresar,parent,false);
        return rootView;
    }
}
