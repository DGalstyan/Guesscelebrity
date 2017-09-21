package com.guess.celebrities;

import java.io.IOException;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.guess.celebrities.R;
import com.guess.db.Tag;
import com.guess.db.Util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FinishGame extends Activity{
	private AdView mAdView = null;
	private Tag tag =  null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.finish_game_screen);
		Intent intent = getIntent();
		int id = intent.getIntExtra("id", 1);
		tag = Util.db.getTag(id);
		TextView tw = (TextView) findViewById(R.id.nametw);	
		tw.setText(tag.getName());
		addImage(tag);
		addAds();
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
}
