package com.example.d10s.gema;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by D10S on 10/09/2015.
 */
public class ActivityLauncher {

    private Context context;
    private static Activity activityToBeLaunched;

    public ActivityLauncher(Context context){
        this.context = context;
    }

    public void setActivityToBeLaunched(Activity activityToBeLaunched){
        this.activityToBeLaunched = activityToBeLaunched;
    }

    public void launchLogueo(){
        Intent logueo = new Intent(context, Logueo.class);
        context.startActivity(logueo);
    }
}
