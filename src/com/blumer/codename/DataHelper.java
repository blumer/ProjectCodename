package com.blumer.codename;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataHelper {


	
	private static final String DATABASE_NAME = "codename.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "requests";
	
	private Context context;
	private SQLiteDatabase db;
	
	SimpleDateFormat dfm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
	public DataHelper(Context context) {
		this.context = context;
		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
		;
	}
	
	public void close() {
	   if (this.db != null) {
		   try {
			   this.db.close();
		   } catch (Exception e) {
			   //okay ...
		   }
	   }
	}
	
	public ArrayList favorites() {
		ArrayList favs = new ArrayList();
		Cursor cursor = this.db.query("favorites", new String[] {"codename"}, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
			favs.add(cursor.getString(0));
			} while (cursor.moveToNext());
		} 
		return favs;
	}
	
	public void SaveFav(String fav) {
		ContentValues cv = new ContentValues();
		cv.put("codename", fav);
		this.db.insert("favorites", "id", cv);
	}
	
	public void RemoveFav(String fav) {
		ContentValues cv = new ContentValues();
		cv.put("codename", fav);
		this.db.delete("favorites", "codename = ?", new String[]{fav});
	}
	
	private static class OpenHelper extends SQLiteOpenHelper {
		OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE Favorites (ID INTEGER, codename TEXT)");
			Log.i("Lofting", "Database Created");
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.i("Lofting", "Database Upgraded");
		}
	}
	
}
