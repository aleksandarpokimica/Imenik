package com.aleksandarpokimica.imenik;

import java.util.HashMap;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class IzmeniKontakt extends Activity {
	
	EditText ime;
	EditText prezime;
	EditText telbroj;
	EditText email;
	EditText adresa;
	
	SQLiteBaza baza = new SQLiteBaza(this);
	
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.izmeni_kontakt);
		ime = (EditText) findViewById(R.id.etIme);
		prezime = (EditText) findViewById(R.id.etPrezime);
		telbroj = (EditText) findViewById(R.id.etTelBroj);
		email = (EditText) findViewById(R.id.etEmail);
		adresa = (EditText) findViewById(R.id.etAdresa);
		
		Intent intent = getIntent();
		
		String kontaktId = intent.getStringExtra("kontaktId");
		
		HashMap<String, String> kontaktiLista = baza.vratiKontakt(kontaktId);
		
		if(kontaktiLista.size() != 0){
			
			ime.setText(kontaktiLista.get("ime"));
			prezime.setText(kontaktiLista.get("prezime"));
			telbroj.setText(kontaktiLista.get("telbroj"));
			email.setText(kontaktiLista.get("email"));
			adresa.setText(kontaktiLista.get("adresa"));
		}
	}
	
	public void izmeniKontakt(View view){
		
		HashMap<String, String> hmValues = new HashMap<String, String>();
		
		ime = (EditText) findViewById(R.id.etIme);
		prezime = (EditText) findViewById(R.id.etPrezime);
		telbroj = (EditText) findViewById(R.id.etTelBroj);
		email = (EditText) findViewById(R.id.etEmail);
		adresa = (EditText) findViewById(R.id.etAdresa);
		
		Intent intent = getIntent();
		
		String kontaktId = intent.getStringExtra("kontaktId");
		
		hmValues.put("kontaktId", kontaktId);
		hmValues.put("ime", ime.getText().toString());
		hmValues.put("prezime", prezime.getText().toString());
		hmValues.put("telbroj", telbroj.getText().toString());
		hmValues.put("email", email.getText().toString());
		hmValues.put("adresa", adresa.getText().toString());
		
		baza.izmeniKontakt(hmValues);
		this.callMainActivity(view);
	}
	
	public void obrisiKontakt(View view){
		
		Intent intent = getIntent();
		
		String kontaktId = intent.getStringExtra("kontaktId");
		
		baza.obrisiKontakt(kontaktId);
		
		this.callMainActivity(view);		
	}
	
	private void callMainActivity(View view) {
		Intent intent = new Intent(getApplication(), MainActivity.class);
		
		startActivity(intent);
	}
	
}















