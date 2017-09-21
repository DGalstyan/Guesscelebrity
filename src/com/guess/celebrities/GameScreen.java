package com.guess.celebrities;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.guess.db.Tag;
import com.guess.db.Util;

public class GameScreen extends Activity implements OnClickListener{
	private AdView mAdView = null;
	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;
	private Tag tag =  null;
	private boolean SOUND_ENABLED;
	private int type =1;
	private Vibrator vibe = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_screen);
		vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE) ;
		SOUND_ENABLED = MainActivity.mSharedPreferences.getBoolean(Util.Prefs.SOUND_ENABLED, true);
		Intent intent = getIntent();
		int id = intent.getIntExtra("id", 1);
		type = intent.getIntExtra("type", 1);
		tag = Util.db.getTag(id);
		vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE) ;
		addImage(tag);
		addAds();
		initializeButtons();
		setListeners();
		setFonts();		
		setQuestions();
	}
	
	private void addImage(Tag tag){
		ImageView iw = (ImageView) findViewById(R.id.imageView);		
		String region = "celebrities"; 		
		try {
			Drawable d = Drawable.createFromStream(getAssets().open(region + "/" + tag.getImage()), null);
			iw.setBackgroundDrawable(d);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void addAds(){
		mAdView = new AdView(this);
		mAdView.setAdSize(AdSize.BANNER);
		mAdView.setAdUnitId(getString(R.string.banner_ad_unit_id));
		LinearLayout layout = (LinearLayout) findViewById(R.id.advertisementLayout);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		layout.addView(mAdView, layoutParams);
		AdRequest adRequest = new AdRequest.Builder().addTestDevice(
				AdRequest.DEVICE_ID_EMULATOR).build();
		mAdView.loadAd(adRequest);
	}
	private void initializeButtons() {
		button1 = (Button) findViewById(R.id.answer1);
		button2 = (Button) findViewById(R.id.answer2);
		button3 = (Button) findViewById(R.id.answer3);
		button4 = (Button) findViewById(R.id.answer4);
	}
	private void setListeners() {
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
	}
	
	private void setFonts() {
		AssetManager assets = getAssets();
		Typeface buttonTypeface = Util.getTypeFaceByName(
				Util.Fonts.CHERY_LINEY, assets);
		button1.setTypeface(buttonTypeface);
		button2.setTypeface(buttonTypeface);
		button3.setTypeface(buttonTypeface);
		button4.setTypeface(buttonTypeface);
	}
	
	private void setQuestions() {
		List<String> list = tag.getQuestions();
		button1.setText(list.get(0));
		button2.setText(list.get(1));
		button3.setText(list.get(2));
		button4.setText(list.get(3));
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		checkAnswer(arg0);
	}
	
	private void checkAnswer(View v) {
		String answer = getSelectedAnswer(v);
		if (tag.getName().equalsIgnoreCase(answer)) {
			if(SOUND_ENABLED) {
				playSound(R.raw.correct_word); 
				vibe.vibrate(200);
			}
			v.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.button_right));
			Util.db.updateStatus(""+tag.getId(), 3);
			showWinAppDialog(R.layout.dialog_win_game); 
		}else{
			if(SOUND_ENABLED) {
				playSound(R.raw.wrong_word); 
				vibe.vibrate(200);
			}		
			
			v.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.button_answare_fail));
			Util.db.updateStatus(""+tag.getId(), 2);
			showWinAppDialog(R.layout.dialog_lose_game); 
		}	
	}
	
	private String getSelectedAnswer(View v) {
		switch (v.getId()) {
		case R.id.answer1:
			return button1.getText().toString();
		case R.id.answer2:
			return button2.getText().toString();
		case R.id.answer3:
			return button3.getText().toString();
		case R.id.answer4:
			return button4.getText().toString();
		}
		return null;
	}
	
	private void playSound(final int id) {
		Thread thread = new Thread(new Runnable() {			
			@Override
			public void run() {
				MediaPlayer.create(GameScreen.this,id).start();
			}
		});
		thread.start();
	}
	
	  private void showWinAppDialog(int dialog_leout) {
			final Dialog dialog = new Dialog(this);
			Util.disableDialogBackground(dialog);
			dialog.setContentView(dialog_leout);
			dialog.setCanceledOnTouchOutside(false);
		    final Button okButton = (Button) dialog.findViewById(R.id.okButton);
		    okButton.setTypeface(Util.getTypeFaceByName(Util.Fonts.CHERY_LINEY, getAssets()));
		      
		 
		    final Button closeDialogButton = (Button) dialog.findViewById(R.id.closeButton);
		   				closeDialogButton.setTypeface(Util.getTypeFaceByName(Util.Fonts.CHERY_LINEY, getAssets()));
		   	OnClickListener rateClickLIstener = new OnClickListener() {				
				@Override
				public void onClick(View v) {
					finish();
					Intent intentScore = new Intent(GameScreen.this,
							StartGame.class);
					intentScore.putExtra("type", type);
					startActivity(intentScore);
				}
			};
			okButton.setOnClickListener(rateClickLIstener);
			closeDialogButton.setOnClickListener(rateClickLIstener);
			dialog.show();				   
		}
	
}
