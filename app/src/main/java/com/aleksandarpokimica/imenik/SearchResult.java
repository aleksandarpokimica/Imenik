package com.aleksandarpokimica.imenik;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class SearchResult extends Activity{
	
	public ListView listView1;
	public SearchAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_result);
		
		listView1 = (ListView)findViewById(R.id.listView1);
		
		if(Constant.addressList.size() == 0){
			Toast.makeText(getApplicationContext(), "No result found", 2000).show();
		}
		
		adapter = new SearchAdapter(SearchResult.this, R.layout.search_result_row, Constant.addressList);
		listView1.setAdapter(adapter);
		
	}
	
}
