package com.aleksandarpokimica.imenik;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity implements OnClickListener {
	
	EditText ed_search;
	Button btn_location,btn_search;
	private static int REQUEST_CODE = 1;
	private String lat = "",lng = "";
	
	public int distance;
	
	public ArrayList<AddressBean> list = new ArrayList<AddressBean>();

	Intent intent;
	TextView kontaktId;
	SQLiteBaza baza = new SQLiteBaza(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ed_search = (EditText)findViewById(R.id.ed_search);
		
		btn_location = (Button)findViewById(R.id.btn_location);
		btn_location.setOnClickListener(this);
		
		btn_search = (Button)findViewById(R.id.btn_search);
		btn_search.setOnClickListener(this);
		
		ArrayList<HashMap<String, String>> listaKontakta = baza.vratiSveKontakte();
		
		if(listaKontakta.size()!=0){
			
			ListView listView = getListView(); 
			listView.setOnItemClickListener(new OnItemClickListener() {
				
				public void onItemClick(AdapterView<?> parent, View view, int position, long id){
					kontaktId = (TextView) view.findViewById(R.id.kontaktId);
					String kontaktIdValue = kontaktId.getText().toString();
					
					Intent intent = new Intent(getApplication(), IzmeniKontakt.class);
					intent.putExtra("kontaktId", kontaktIdValue);
					startActivity(intent);
				}
				
			});
			
			ListAdapter adapter = new SimpleAdapter(MainActivity.this, listaKontakta, R.layout.prikaz, new String[] { "kontaktId", "prezime", "ime"}, new int[] {R.id.kontaktId, R.id.prezime, R.id.ime});
			setListAdapter(adapter);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void dodajKontakt(View view){
		
		Intent intent = new Intent(getApplicationContext(), NoviKontakt.class);
		startActivity(intent);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_location:
			Intent i = new Intent(MainActivity.this, MapviewActivity.class);
			startActivityForResult(i, REQUEST_CODE);
			break;

		case R.id.btn_search:
			if(!lat.equalsIgnoreCase("")){
				Location locationA = new Location(".A");
				locationA.setLatitude(Double.parseDouble(lat));
				locationA.setLongitude(Double.parseDouble(lng));
				distance = Integer.parseInt(ed_search.getText().toString().trim());
				list = baza.selectAll(locationA, distance);
				
				Constant.addressList = list;
				
				Intent intent = new Intent(MainActivity.this,SearchResult.class);
				startActivity(intent);
				
			}else{
				Toast.makeText(MainActivity.this, "Please select location from amp", Toast.LENGTH_LONG).show();
			}

			break;
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	    if (requestCode == 1) {
	        if(resultCode == RESULT_OK){
	            String result=data.getStringExtra("result");
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
