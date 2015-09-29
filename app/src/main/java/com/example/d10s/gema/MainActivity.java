package com.example.d10s.gema;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
    EditText edit1;
    EditText edit2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void logueo(View view){
        new HelloWorldWeb(this).execute(edit1.getText().toString(), edit2.getText().toString());
    }

    public void registro(View view) {
        Intent registro = new Intent(this, Registro.class);
        startActivity(registro);
    }
}
