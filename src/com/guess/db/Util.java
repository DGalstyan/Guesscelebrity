package com.guess.db;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.Window;

public class Util {
	public static MySQLiteHelper db = null;

	public static class Fonts {
		public static final String CHERY = "fonts/cheri_1.ttf";
		public static final String CHERY_LINEY = "fonts/cheri_2.ttf";
	}

	public static Typeface getTypeFaceByName(String fontName, AssetManager asset) {
		Typeface fontFace = Typeface.createFromAsset(asset, fontName);
		return fontFace;
	}

	public static class Prefs {
		public static final String WRONG_COUNT = "wrongCount";
		public static final String SOUND_ENABLED = "soundEnabled";
		public static final String LAUNCH_COUNTER = "launchCounter";
		public static final String IS_RATED = "isRated";
	}

	public static class SoundStatus {
		public static final int SOUND_OFF = 0;
		public static final int SOUND_ON = 1;
	}

	public static void disableDialogBackground(Dialog dialog) {
		// Disabling Title in Dialog
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Disabling black background when using custom layouts for dialog
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
	}
	  public static void goToMarket(Activity activity) {
			final String appPackageName = activity.getPackageName(); // getPackageName() from Context or Activity object
			try {
				activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
			} catch (android.content.ActivityNotFoundException anfe) {
				activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
			}
		}
}
