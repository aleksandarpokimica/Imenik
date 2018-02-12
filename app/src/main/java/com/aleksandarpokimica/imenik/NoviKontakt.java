package com.aleksandarpokimica.imenik;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class NoviKontakt extends Activity implements OnClickListener {

	private EditText ime;
	private EditText prezime;
	private EditText telbroj;
	private EditText email;
	private EditText adresa;
	private Button btnLocation,addButton;
	private static int REQUEST_CODE = 1;
	private String lat = "",lng = "";

	SQLiteBaza baza = new SQLiteBaza(this);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.novi_kontakt);
		
		ime = (EditText) findViewById(R.id.etIme);
		prezime = (EditText) findViewById(R.id.etPrezime);
		telbroj = (EditText) findViewById(R.id.etTelBroj);
		email = (EditText) findViewById(R.id.etEmail);
		adresa = (EditText) findViewById(R.id.etAdresa);
		
		addButton = (Button)findViewById(R.id.addButton);
		addButton.setOnClickListener(this);
		
		btnLocation = (Button)findViewById(R.id.btnlocation);
		btnLocation.setOnClickListener(this);
	}
	
	public void dodajNoviKontakt() {
		
		HashMap<String, String> queryValuesMap =  new  HashMap<String, String>();
		
		queryValuesMap.put("ime", ime.getText().toString());
		queryValuesMap.put("prezime", prezime.getText().toString());
		queryValuesMap.put("telbroj", telbroj.getText().toString());
		queryValuesMap.put("email", email.getText().toString());
		queryValuesMap.put("adresa", adresa.getText().toString());
		queryValuesMap.put("lat", lat);
		queryValuesMap.put("lng", lng);
		if(!lat.equalsIgnoreCase("")){
			baza.ubaciKontakt(queryValuesMap);
			
			this.callMainActivity();
		}else{
			Toast.makeText(getApplicationContext(), "Please select location", 3000).show();
		}
		
	}
	
	public void callMainActivity() {
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnlocation:
			Intent i = new Intent(NoviKontakt.this,MapviewActivity.class);
			startActivityForResult(i,REQUEST_CODE);
			break;
			
		case R.id.addButton:
			dodajNoviKontakt();
			break;
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	    if (requestCode == 1) {
	        if(resultCode == RESULT_OK){
	            String lat_=data.getStringExtra("lat");
	            lat = lat_;
	            String lng_ = data.getStringExtra("lng");
	            lng = lng_;
	        }
	        if (resultCode == RESULT_CANCELED) {
	            //Write your code if there's no result
	        }
	    }
	}
}
