package com.blumer.codename;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;

public class OptionsDialog extends Dialog {

	Context context;
	SharedPreferences settings;
	RadioButton rbProject;
	RadioButton rbOperation;
	CheckBox cbSound;
	OptionsListener readyListener;
	
	public interface OptionsListener {
		public void ready();
	}
	
	public OptionsDialog(Context context, OptionsListener readyListener) {
		super(context);
		this.context = context;
		this.readyListener = readyListener;
		this.setTitle("Options");
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);
		settings = context.getSharedPreferences("CodenamePrefs", 0);
		
		rbProject = (RadioButton)findViewById(R.id.radioProject);
		rbOperation = (RadioButton)findViewById(R.id.radioOperation);
		cbSound = (CheckBox)findViewById(R.id.cbSound);
		String labelPref = settings.getString("labelPref", "Project");
		Boolean sound = settings.getBoolean("sound", true);
		
		rbProject.setChecked(labelPref.equals("Project"));
		rbOperation.setChecked(labelPref.equals("Operation"));
		
		cbSound.setChecked(sound);
		
		Button btnSave = (Button)findViewById(R.id.btnSavePrefs);
		btnSave.setOnClickListener((android.view.View.OnClickListener) saveListener);
	}
	
	private android.view.View.OnClickListener saveListener = new android.view.View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			SharedPreferences.Editor editor = settings.edit();
			if (rbOperation.isChecked()) {
				editor.putString("labelPref", "Operation");
				Log.d("Codename", "Setting pref to Operation");
			} else {
				editor.putString("labelPref", "Project");	
			}
			editor.putBoolean("sound", cbSound.isChecked());
			
			editor.commit();
			readyListener.ready();
			OptionsDialog.this.dismiss();
		}
	};
}
