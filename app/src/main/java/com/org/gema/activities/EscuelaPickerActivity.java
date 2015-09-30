package com.org.gema.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class EscuelaPickerActivity extends Activity{
	
	String[] escuelas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		ListView lista = new ListView(this);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
		if( savedInstanceState == null ){
			escuelas = getIntent().getExtras().getStringArray("escuelas");
		}
		for(int i = 0; i< escuelas.length; i++)
			adapter.add(escuelas[i]);
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.putExtra("escuela", ((TextView)view).getText().toString());
				setResult(RESULT_OK,i);
				finish();
			}
		});
		lista.setAdapter(adapter);
		setContentView(lista);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		outState.putStringArray("escuelas", escuelas);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState){
		super.onRestoreInstanceState(savedInstanceState);
		escuelas = savedInstanceState.getStringArray("escuelas");
	}
}