package com.guess.celebrities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.guess.db.MySQLiteHelper;
import com.guess.db.Tag;
import com.guess.db.Util;

public class MainActivity extends Activity implements OnClickListener {
	private Button easyButton;
	private Button normalButton;
	private Button settings;
	private Button hardButton;
	public static SharedPreferences mSharedPreferences;
	private Editor editor;
	private int launchCounter;
	private boolean showRateDialog = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_game);
		
		Util.db = new MySQLiteHelper(this.getApplicationContext());
		initializeButtons();		
		setListeners();
		mSharedPreferences = getApplicationContext().getSharedPreferences(
				"MyPref", 0);
		editor = mSharedPreferences.edit();
		boolean isAdded = mSharedPreferences.getBoolean("isAdded", false);
		if (!isAdded) {
			Tag.updateTable();			
			editor.putBoolean("isAdded", true);
			editor.commit();
		}

		setFonts();
		configureLaunchCounter();
		showDialogIfNecessary();
	}
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
	}
	private void initializeButtons() {
		easyButton = (Button) findViewById(R.id.easy);
		normalButton = (Button) findViewById(R.id.normal);
		settings = (Button) findViewById(R.id.settings);
		hardButton = (Button) findViewById(R.id.hard);
	}

	private void setListeners() {
		easyButton.setOnClickListener(this);
		normalButton.setOnClickListener(this);
		settings.setOnClickListener(this);
		hardButton.setOnClickListener(this);
	}

	private void setFonts() {
		AssetManager assets = getAssets();
		Typeface buttonTypeface = Util.getTypeFaceByName(
				Util.Fonts.CHERY_LINEY, assets);
		easyButton.setTypeface(buttonTypeface);
		normalButton.setTypeface(buttonTypeface);
		settings.setTypeface(buttonTypeface);
		hardButton.setTypeface(buttonTypeface);
	}

	@Override
	public void onClick(View arg0) {
		Intent intentScore = new Intent(MainActivity.this,
				StartGame.class);
		switch (arg0.getId()) {
			case R.id.easy:	
				intentScore.putExtra("type", 1);
				startActivity(intentScore);
			break;
			case R.id.normal:
				intentScore.putExtra("type", 2);
				startActivity(intentScore);
			break;
			case R.id.hard:
				intentScore.putExtra("type", 3);
				startActivity(intentScore);
			break;
			case R.id.settings:
				showSettingsMenuDialog();
				break;
			default:
				break;
		}
	}
	private void showSettingsMenuDialog() {
		final Dialog dialog = new Dialog(this);
		Util.disableDialogBackground(dialog);
		//Setting custom view for settings dialog
		dialog.setContentView(R.layout.dialog_settings_menu);
		Button closeSettingsDialogButton = (Button) dialog.findViewById(R.id.closeSettingsDialogButton);
		ToggleButton soundSwitch = (ToggleButton) dialog.findViewById(R.id.soundSwitcher);
		if(mSharedPreferences.getBoolean(Util.Prefs.SOUND_ENABLED, true)) {
			soundSwitch.setChecked(true);
		}
		soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
					editor.putBoolean(Util.Prefs.SOUND_ENABLED, true);
				} else {
					editor.putBoolean(Util.Prefs.SOUND_ENABLED, false);					
				}
				editor.commit();
			}
		});
		TextView soundStatusTextView = (TextView) dialog.findViewById(R.id.soundTitle);
		setDialogDismissListener(closeSettingsDialogButton, dialog);
				
		setDialogTextFont(soundStatusTextView);
		dialog.show();
	}
	

	
	//Setting dialog's text's font
	private void setDialogTextFont(TextView textView) {
		Typeface textTypeFace = Util.getTypeFaceByName(Util.Fonts.CHERY_LINEY, getAssets());
				 textView.setTypeface(textTypeFace);
	}
	
	private void setDialogDismissListener(Button closeDialogButton ,final Dialog dialog) {
		//Dismiss listener for dialog close button
		closeDialogButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}
	
	private void configureLaunchCounter() {	
		launchCounter = mSharedPreferences.getInt(Util.Prefs.LAUNCH_COUNTER, 0);
		if(launchCounter != -1) {
			if(launchCounter == 3 ) {
				showRateDialog  = true;
			} else {
				launchCounter ++;
				editor.putInt(Util.Prefs.LAUNCH_COUNTER, launchCounter).commit();
			}
		}		
	}
	
	 private void showDialogIfNecessary() {
	    	if(showRateDialog) {
	    		showRateAppDialog();
	    	}
	}
	 
	  private void showRateAppDialog() {
			final Dialog dialog = new Dialog(this);
			Util.disableDialogBackground(dialog);
			dialog.setContentView(R.layout.dialog_rate_application);
			dialog.setCanceledOnTouchOutside(false);
		    TextView rateDialogTitle =(TextView) dialog.findViewById(R.id.pleaseRateOurAppTitle);
		   			rateDialogTitle.setTypeface(Util.getTypeFaceByName(Util.Fonts.CHERY_LINEY, getAssets()));
		    final Button rateNowButton = (Button) dialog.findViewById(R.id.rateNowButton);
		   				rateNowButton.setTypeface(Util.getTypeFaceByName(Util.Fonts.CHERY_LINEY, getAssets()));
		    
		   	final Button thanksButton = (Button) dialog.findViewById(R.id.thanksButton);
		   	thanksButton.setTypeface(Util.getTypeFaceByName(Util.Fonts.CHERY_LINEY, getAssets()));
		   
		   	final Button rateLaterButton = (Button) dialog.findViewById(R.id.rateLaterButton);
		   				rateLaterButton.setTypeface(Util.getTypeFaceByName(Util.Fonts.CHERY_LINEY, getAssets()));
		    final Button closeDialogButton = (Button) dialog.findViewById(R.id.closeRateDialogButton);
		   				closeDialogButton.setTypeface(Util.getTypeFaceByName(Util.Fonts.CHERY_LINEY, getAssets()));
			final TextView textview = (TextView)dialog.findViewById(R.id.textMessage);
			textview.setText(getString(R.string.rate1) + " "
					+ getString(R.string.app_name) + ", "
					+ getString(R.string.rate2));
			textview.setTypeface(Util.getTypeFaceByName(Util.Fonts.CHERY_LINEY, getAssets()));
		   	OnClickListener rateClickLIstener = new OnClickListener() {				
				@Override
				public void onClick(View v) {
					int id = v.getId();
					if(id == rateNowButton.getId()) {
						Util.goToMarket(MainActivity.this);
						launchCounter = -1;						
					} else if(id == thanksButton.getId() || id == rateLaterButton.getId() || id == closeDialogButton.getId()) {
						launchCounter = 0;
					}
					dialog.dismiss();
					editor.putInt(Util.Prefs.LAUNCH_COUNTER,launchCounter).commit();
				}
			};
			rateNowButton.setOnClickListener(rateClickLIstener);
			rateLaterButton.setOnClickListener(rateClickLIstener);
			closeDialogButton.setOnClickListener(rateClickLIstener);
			dialog.show();				   
		}
	  
	
}
