package com.guess.newpuzzle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.guess.celebrities.R;
import com.guess.db.Tag;
import com.guess.db.Util;
import com.guess.newpuzzle.data.Contant;
import com.guess.newpuzzle.view.GameView;
import com.guess.newpuzzle.view.InChallengeGameView;




public class GameActivity extends Activity  {

	private RelativeLayout layout;
	private GameView inGameView = null;	
	public ImageView imageView;
	public ProgressDialog pDialog;
	private Tag tag =  null;
	private int type =1;
	private AdView mAdView = null;

	
	@SuppressLint("HandlerLeak")
	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			GameActivity.this.openOptionsMenu();
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		Contant.ScreenWidth = dm.widthPixels;
		Contant.ScreenHeight = dm.heightPixels;
		Contant.TxtAppName = getResources().getString(R.string.app_name);

		Contant.TxtMoved = getResources().getString(R.string.moved);
		Contant.TxtTime = getResources().getString(R.string.time);

		Contant.TxtTimeused = getResources().getString(R.string.timeused);


		Contant.TxtCurrentLevel = getResources().getString(R.string.levelstr);
		
		Intent intent = getIntent();
		 
		 int id = intent.getIntExtra("id", 1);
		 type = intent.getIntExtra("type", 1);
		 tag = Util.db.getTag(id);
		 Contant.PicUrl = tag.getImage();	
		 Contant.hasNum = false;
		
		
		Contant.Level = type+2;
		
		layout = new RelativeLayout(this);
		/*welcomeView = new WelcomeView(this);
		layout.addView(welcomeView);*/
		setContentView(layout);
		
		inGameView = new InChallengeGameView(GameActivity.this, Contant.Level, Contant.PicUrl, type, id);

		layout.addView((View) inGameView);
		addAds();		
	}
	

	private void addAds(){
		mAdView = new AdView(this);
		mAdView.setAdSize(AdSize.BANNER);
		mAdView.setAdUnitId(getString(R.string.banner_ad_unit_id));		
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		layout.addView(mAdView, layoutParams);
		AdRequest adRequest = new AdRequest.Builder().addTestDevice(
				AdRequest.DEVICE_ID_EMULATOR).build();
		mAdView.loadAd(adRequest);
	}

	@Override
	protected void onDestroy() {
		if(inGameView != null)
		{
			inGameView.destroy();
		}	
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(inGameView!= null){
			inGameView.start(); 
		}
	}
	@Override
	protected void onPause() {
		super.onPause();
		if(inGameView!= null){
			inGameView.pause(); 
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(1, 1, 1, getResources().getString(R.string.menu_supportus));
		//menu.add(2, 2, 1, getResources().getString(R.string.button_help));
		menu.add(2, 2, 1, getResources().getString(R.string.share));
		menu.add(3, 3, 1, getResources().getString(R.string.rate));
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch(item.getGroupId())
		{
		case 1:
			showHelpDialog(getResources().getString(R.string.thanks));
		break;
		case 2:
			shareTextUrl();
			break;
		case 3:
			Util.goToMarket(GameActivity.this);
			break;
		}	

		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}
	private void showHelpDialog(String text) {
		final Dialog dialog = new Dialog(this);
			
		disableDialogBackground(dialog);
		// Setting custom view for help dialog
		dialog.setContentView(R.layout.dialog_help);
		dialog.setCanceledOnTouchOutside(false);
		// Getting Textviews from dialog to apply fonts
		Button closeDialogButton = (Button) dialog
				.findViewById(R.id.closeHelpDialogButton);
		TextView dialogTitle = (TextView) dialog.findViewById(R.id.helpTitle);
		TextView firstRule = (TextView) dialog.findViewById(R.id.firstRule);
		firstRule.setText(text);
		setDialogTextFont(dialogTitle);
		setDialogTextFont(firstRule);
		closeDialogButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {				
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	// Setting dialog's text's font
	private void setDialogTextFont(TextView textView) {		
		textView.setTypeface(Util.getTypeFaceByName(Util.Fonts.CHERY_LINEY, getAssets()));
	}
	private void disableDialogBackground(Dialog dialog) {
		// Disabling Title in Dialog
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Disabling black background when using custom layouts for dialog
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
	}

	// Method to share either text or URL.
	private void shareTextUrl() {
		final String appPackageName = getPackageName();
		Intent share = new Intent(android.content.Intent.ACTION_SEND);
		share.setType("text/plain");
		share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

		// Add data to the intent, the receiving app will decide
		// what to do with it.
		share.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
		share.putExtra(
				Intent.EXTRA_TEXT,
				getString(R.string.share_name)
						+ "\n\n"
						+ "https://play.google.com/store/apps/details?id="+appPackageName);

		startActivity(Intent.createChooser(share,
				getString(R.string.action_share)));
	}

}
