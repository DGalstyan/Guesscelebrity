package com.guess.celebrities;

import java.util.List;


import com.guess.celebrities.R;
import com.guess.db.Tag;
import com.guess.db.Util;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;

public class StartGame extends Activity implements OnClickListener {
	private boolean SOUND_ENABLED;
	private Button backButton;
	private MediaPlayer mediaplayer = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_game);	
		
		initializeLayout();
		initializeButtons();
		setListeners();
	}

	private void initializeButtons() {
		backButton = (Button) findViewById(R.id.backMenuButton);
		
	}
	private void initializeLayout() {
		SOUND_ENABLED = MainActivity.mSharedPreferences.getBoolean(
				Util.Prefs.SOUND_ENABLED, true);
		mediaplayer = MediaPlayer.create(getApplicationContext(), R.raw.game_started);
		playSound();
		
		Intent intent = getIntent();
		int type = intent.getIntExtra("type", 1);

		List<Tag> list = Util.db.getAllTag(type, 24);
		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new ImageAdapter(this, list, type));
		
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.setVolumeControlStream(AudioManager.STREAM_RING);  
	}

	private void setListeners() {
		backButton.setOnClickListener(this);
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	private void playSound() {
		if (SOUND_ENABLED) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					mediaplayer.start();
				}
			}).start();
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.backMenuButton:
			finish();
			break;
		default:
			break;
		}
	}
}
