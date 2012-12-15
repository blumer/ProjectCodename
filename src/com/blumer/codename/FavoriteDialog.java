package com.blumer.codename;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class FavoriteDialog extends Dialog {

	Context context;
	ArrayAdapter<String> adapter;
	public FavoriteDialog(Context context) {
		super(context);
		this.context = context;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DataHelper dh = new DataHelper(context);
		ArrayList<String> favs = dh.favorites();
		dh.close();
		
		setContentView(R.layout.lastfive);
		this.setTitle("Favorites");
		ListView lvLastFive = (ListView)findViewById(R.id.lvLastFive);
		adapter = new ArrayAdapter(this.context, android.R.layout.simple_list_item_1, favs);
		lvLastFive.setAdapter(adapter);
		lvLastFive.setOnItemClickListener(codenamelistener);
	}
	
	private OnItemClickListener codenamelistener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			ListView lvLastFive = (ListView)findViewById(R.id.lvLastFive);
			final String codename = (String)lvLastFive.getAdapter().getItem(arg2);
			AlertDialog.Builder alert = new AlertDialog.Builder(context);
			alert.setTitle("Remove Favorite");
			alert.setMessage("Remove " + codename + " from favorites?");
			alert.setPositiveButton("Yes", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// Remove it from the db
					DataHelper dh = new DataHelper(context);
					dh.RemoveFav(codename);
					dh.close();
					//ArrayList<String> favs = dh.favorites();
					adapter.remove(codename);
					
				}
			});
			alert.setNegativeButton("No", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// Uh, okay.
				}
				
			});
			
			alert.show();
			
			String a = codename.split(" ")[0];
			String b = codename.split(" ")[1];
			//FavoriteDialog.this.dismiss();

			
		}
		
	};
}
