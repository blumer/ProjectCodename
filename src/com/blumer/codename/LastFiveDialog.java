package com.blumer.codename;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LastFiveDialog extends Dialog {

	public interface LastFiveListener {
		public void ready(String a, String b);
	}
	
	Context context;
	LastFiveListener readyListener;
	ArrayList<String> Last5;
	public LastFiveDialog(Context context, LastFiveListener readyListener, ArrayList<String> Last5) {
		super(context);
		this.context = context;
		this.readyListener = readyListener;
		this.Last5 = Last5;
		this.setTitle("Last Five");
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lastfive);
		ListView lvLastFive = (ListView)findViewById(R.id.lvLastFive);
		ArrayAdapter<String> adapter = new ArrayAdapter(this.context, android.R.layout.simple_list_item_1, this.Last5);
		lvLastFive.setAdapter(adapter);
		lvLastFive.setOnItemClickListener(codenamelistener);
	}
	
	private OnItemClickListener codenamelistener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			ListView lvLastFive = (ListView)findViewById(R.id.lvLastFive);
			String codename = (String)lvLastFive.getAdapter().getItem(arg2);
			String a = codename.split(" ")[0];
			String b = codename.split(" ")[1];
			readyListener.ready(a, b);
			LastFiveDialog.this.dismiss();

			
		}
		
	};

}
