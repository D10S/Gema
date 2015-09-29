package com.example.d10s.gema.com.example.lardim.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.d10s.gema.R;

/**
 * Created by Sala9 on 28/09/2015.
 */
public class WelcomeView extends Fragment {

    Activity activity;
    ListView vendedores;

    public void WelcomeView(){
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup Parent, Bundle savedInstanceState){
        super.onCreateView(inflater,Parent,savedInstanceState);
        final View rootView = inflater.inflate(R.layout.activity_main, Parent, false);
        return rootView;
    }

}
