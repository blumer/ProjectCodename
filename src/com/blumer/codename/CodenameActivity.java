package com.blumer.codename;

import java.util.ArrayList;
import java.util.Random;

import com.blumer.codename.LastFiveDialog.LastFiveListener;
import com.blumer.codename.OptionsDialog.OptionsListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CodenameActivity extends Activity {
	
	TextView txtProject;
	TextView txtFirst;
	TextView txtSecond;
	Button btnGenerate;
	Button btnFavorite;
	ArrayList<String> Last5;
	SharedPreferences settings;
	Animation shake;
	MediaPlayer mp;
    
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Top_Secret.ttf"); 
        
        settings = this.getSharedPreferences("CodenamePrefs", 0);
        
        shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        
        mp = MediaPlayer.create(this, R.raw.thud);
        
        txtProject = (TextView)findViewById(R.id.txtProject);
        txtFirst = (TextView)findViewById(R.id.txtFirstWord);
        txtSecond = (TextView)findViewById(R.id.txtSecondWord);
        btnGenerate = (Button)findViewById(R.id.btnGenerate);
        btnFavorite = (Button)findViewById(R.id.btnFavorite);
        
        txtProject.setText(settings.getString("labelPref", "Project") + ":");
        txtProject.setTypeface(type);
        txtFirst.setTypeface(type);
        txtSecond.setTypeface(type);
        
        Last5 = new ArrayList<String>();
        OnClickListener generateListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				GenerateNew();
			}
		};
		
		OnClickListener favoriteListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 String fav = txtFirst.getText().toString() + " " + txtSecond.getText().toString();
				 DataHelper dh = new DataHelper(CodenameActivity.this);
				 dh.SaveFav(fav);
				 dh.close();
				 
				 Toast t = Toast.makeText(CodenameActivity.this, fav + " Saved", Toast.LENGTH_SHORT);
				 t.show();
			}
		};
		btnGenerate.setOnClickListener(generateListener);
		btnFavorite.setOnClickListener(favoriteListener);
		GenerateNew();
    }
    
    private void GenerateNew() {
    	txtFirst.setVisibility(View.INVISIBLE);
    	txtSecond.setVisibility(View.INVISIBLE);
		 String[] firsts = getResources().getStringArray(R.array.first_words);
		 String[] seconds = getResources().getStringArray(R.array.second_words);
		 Random r = new Random();
		 int i1=r.nextInt(firsts.length - 1);
		 int i2=r.nextInt(seconds.length - 1);
		 txtFirst.setText(firsts[i1]);
		 txtSecond.setText(seconds[i2]);
		 Handler handler1 = new Handler(); 
		    handler1.postDelayed(new Runnable() { 
		         public void run() { 
		        	 if(settings.getBoolean("sound", true)) {
		        		 mp.start();
		        	 }
		             txtFirst.setVisibility(View.VISIBLE); 
		             txtFirst.startAnimation(shake);
		         } 
		    }, 500);
	    Handler handler2 = new Handler(); 
		    handler2.postDelayed(new Runnable() { 
		         public void run() {
		        	 if(settings.getBoolean("sound", true)) {
		        		 mp.start();
		        	 }
		        	 txtSecond.setVisibility(View.VISIBLE);
		        	 txtSecond.startAnimation(shake);
		         } 
		    }, 1200);
		       
		 Last5.add(firsts[i1] + " " + seconds[i2]);
		 if (Last5.size() > 5) {
			 Last5.remove(0); 
		 }
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Favorites");
		menu.add("Last Five");
		menu.add("Options");
		return true;
	}
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().equals("Favorites")) {
			FavoriteDialog fd = new FavoriteDialog(CodenameActivity.this);
			fd.show();
		} else if (item.getTitle().equals("Last Five")){
			LastFiveListener lfl = new LastFiveListener() {
				
				@Override
				public void ready(String a, String b) {
					// TODO Auto-generated method stub
					txtFirst.setText(a);
					txtSecond.setText(b);
				}
			};
			LastFiveDialog lfd = new LastFiveDialog(CodenameActivity.this, lfl, this.Last5);
			lfd.show();
		} else if (item.getTitle().equals("Options")) {
			OptionsListener ol = new OptionsListener() {
				
				@Override
				public void ready() {
					Log.d("Codename", "Returned, setting to " + settings.getString("labelPref", "default"));
					txtProject.setText(settings.getString("labelPref", "Project") + ":");
				}
			};
			OptionsDialog od = new OptionsDialog(CodenameActivity.this, ol);
			od.show();
		}
		return true;
	}
    
    
}