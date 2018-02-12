package com.aleksandarpokimica.imenik;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;
import android.widget.CheckBox;

@SuppressWarnings("unused")
public class SQLiteBaza extends SQLiteOpenHelper {
	
	public SQLiteBaza(Context context) {
		super(context, "imenik.db", null, 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String upitKreirajBazu = "CREATE TABLE kontakti (kontaktId INTEGER PRIMARY KEY, ime TEXT, prezime TEXT, telbroj TEXT, email TEXT, adresa TEXT, lat TEXT, lng TEXT)";
		db.execSQL(upitKreirajBazu);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String upitDropBazu = "DROP TABLE IF EXISTS kontakti";
		
		db.execSQL(upitDropBazu);
		onCreate(db);
	}
	
	public void ubaciKontakt(HashMap<String, String> upitVrednosti){
		
		SQLiteDatabase baza = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put("ime", upitVrednosti.get("ime"));
		values.put("prezime", upitVrednosti.get("prezime"));
		values.put("telbroj", upitVrednosti.get("telbroj"));
		values.put("email", upitVrednosti.get("email"));
		values.put("adresa", upitVrednosti.get("adresa"));
		values.put("lat", upitVrednosti.get("lat"));
		values.put("lng", upitVrednosti.get("lng"));
		
		Log.e("!!--lat", upitVrednosti.get("lat"));
		Log.e("!!--lat", upitVrednosti.get("lng"));
		
		baza.insert("kontakti", null, values);
		
		baza.close();
	}
	
	public int izmeniKontakt(HashMap<String, String> upitVrednosti){
		
		SQLiteDatabase baza = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put("ime", upitVrednosti.get("ime"));
		values.put("prezime", upitVrednosti.get("prezime"));
		values.put("telbroj", upitVrednosti.get("telbroj"));
		values.put("email", upitVrednosti.get("email"));
		values.put("adresa", upitVrednosti.get("adresa"));
		values.put("lat", upitVrednosti.get("lat"));
		values.put("lng", upitVrednosti.get("lng"));
		
		return baza.update("kontakti", values, "kontaktId" + " = ?", new String[] {upitVrednosti.get("kontaktId") });
		
	}
	
	public void obrisiKontakt(String id){
		
		SQLiteDatabase baza = this.getWritableDatabase();
		
		String obrisiUpit = "DELETE FROM kontakti WHERE kontaktId = '" + id + "'";
		
		baza.execSQL(obrisiUpit);
	}
	
	public ArrayList<HashMap<String, String>> vratiSveKontakte(){
		
		ArrayList<HashMap<String, String>> alKontakti = new ArrayList<HashMap<String,String>>();
		
		String selectUpit = "SELECT * FROM kontakti ORDER BY prezime";
		
		SQLiteDatabase baza = this.getWritableDatabase();
		
		Cursor kursor = baza.rawQuery(selectUpit, null);
		
		if(kursor.moveToFirst()){
			
			do{
				
				HashMap<String, String> kontaktiMapa = new HashMap<String, String>();
				
				kontaktiMapa.put("kontaktId", kursor.getString(0));
				kontaktiMapa.put("ime", kursor.getString(1));
				kontaktiMapa.put("prezime", kursor.getString(2));
				kontaktiMapa.put("telbroj", kursor.getString(3));
				kontaktiMapa.put("email", kursor.getString(4));
				kontaktiMapa.put("adresa", kursor.getString(5));
				kontaktiMapa.put("lat", kursor.getString(6));
				kontaktiMapa.put("lng", kursor.getString(7));
				
				alKontakti.add(kontaktiMapa);
				
			} while(kursor.moveToNext());
			
		}
		
		return alKontakti;
		
	}
	
	public HashMap<String, String> vratiKontakt(String id){
		
		HashMap<String, String> kontaktiMapa = new HashMap<String, String>();
		
		SQLiteDatabase baza = this.getReadableDatabase();
		
		String selectUpit = "SELECT * FROM kontakti WHERE kontaktId='" + id + "'";
		
		Cursor kursor = baza.rawQuery(selectUpit, null);
		
		if(kursor.moveToFirst()){
			
			do{
				
				kontaktiMapa.put("kontaktId", kursor.getString(0));
				kontaktiMapa.put("ime", kursor.getString(1));
				kontaktiMapa.put("prezime", kursor.getString(2));
				kontaktiMapa.put("telbroj", kursor.getString(3));
				kontaktiMapa.put("email", kursor.getString(4));
				kontaktiMapa.put("adresa", kursor.getString(5));
				kontaktiMapa.put("lat", kursor.getString(6));
				kontaktiMapa.put("lng", kursor.getString(7));
				
			} while(kursor.moveToNext());
			
		}
		
		return kontaktiMapa;
	}
	
	public ArrayList<AddressBean> selectAll(Location loc, int radius) {

		ArrayList<AddressBean> contactArr = new ArrayList<AddressBean>();

		SQLiteDatabase baza = this.getReadableDatabase();

		String selectUpit = "SELECT * FROM kontakti ";

		Cursor kursor = baza.rawQuery(selectUpit, null);
		Log.e("!!--", "reach here"+kursor.getCount());
		Location locationB = new Location("point B");
		if (kursor.moveToFirst()) {

			do {
				
				
				locationB.setLatitude(Double.parseDouble(kursor.getString(6)));
				locationB.setLongitude(Double.parseDouble(kursor.getString(7)));
				if(loc.distanceTo(locationB)<= radius){
					contactArr.add(new AddressBean(kursor.getString(0), kursor
							.getString(1), kursor.getString(2),
							kursor.getString(3), kursor.getString(4), kursor
									.getString(5), kursor.getString(6), kursor
									.getString(7)));
				}
				

			} while (kursor.moveToNext());

		}

		return contactArr;
	}
}