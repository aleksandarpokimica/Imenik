package com.aleksandarpokimica.imenik;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchAdapter extends ArrayAdapter<AddressBean>{
	
	private Activity activity;
	private ViewHolder mHolder;
	public ArrayList<AddressBean> item = new ArrayList<AddressBean>();
	public String date,doctor_id;
	
	public SearchAdapter(Activity activity,int textViewResourceId,ArrayList<AddressBean> items) {
		super(activity,textViewResourceId, items);
		this.item = items;
		this.activity = activity;
	}

	@Override
	public int getCount() {
		return item.size();
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView( final int position,  View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.search_result_row, null);

			mHolder = new ViewHolder();
			v.setTag(mHolder);
			
			mHolder.tv_name = (TextView)v.findViewById(R.id.tv_name);
			mHolder.ll_container = (LinearLayout)v.findViewById(R.id.ll_container);
		}
		else {
			mHolder =  (ViewHolder) v.getTag();
		}
		
		mHolder.ll_container.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String id = item.get(position).getId();
				Intent i = new Intent(activity,IzmeniKontakt.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				i.putExtra("kontaktId", id);
				activity.startActivity(i);
			}
		});
		
		
		
		final AddressBean mVendor = item.get(position);

		if(mVendor != null){
			
				mHolder.tv_name.setText(mVendor.getFirstName() + " " + mVendor.getLastName());
		}		
		return v;
	}

	class ViewHolder {
		public TextView tv_name;
		public LinearLayout ll_container;
	}
}
