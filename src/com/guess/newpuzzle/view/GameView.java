package com.guess.newpuzzle.view;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.guess.celebrities.GameScreen;
import com.guess.celebrities.MainActivity;
import com.guess.celebrities.R;
import com.guess.celebrities.StartGame;
import com.guess.db.Tag;
import com.guess.db.Util;
import com.guess.newpuzzle.GameActivity;
import com.guess.newpuzzle.data.Contant;
import com.guess.newpuzzle.graphics.Sprite;
import com.guess.newpuzzle.util.ImagePiece;
import com.guess.newpuzzle.util.ImageSpliter;

@SuppressLint("HandlerLeak")
public class GameView extends View {
	private AssetManager assets;
	private InputStream stream; // used to read in player names
	private Activity context;
	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;
	private Tag tag =  null;
	private boolean SOUND_ENABLED;
	private Vibrator vibe = null;
	private int type =1;
	private int id;
	private Typeface buttonTypeface;
	private int wrong =0;
	private Editor editor;
	private MediaPlayer mediaplayer = null;
	
	public GameView(Activity context, String image, int type, int id) {
		super(context);
		this.context = context;
		this.type = type;
		this.id = id;
		mediaplayer = MediaPlayer.create(context, R.raw.photo_flick);
		SOUND_ENABLED = MainActivity.mSharedPreferences.getBoolean(Util.Prefs.SOUND_ENABLED, true);
		wrong = MainActivity.mSharedPreferences.getInt(Util.Prefs.WRONG_COUNT, 0);
		editor = MainActivity.mSharedPreferences.edit();
		tag = Util.db.getTag(id);
		setupTitleAndButton();
		String region = "celebrities";
		assets = context.getAssets(); // get apps Asset Manager
		buttonTypeface = Util.getTypeFaceByName(Util.Fonts.CHERY_LINEY, assets);
		vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE) ;
		try {
			// get an InputStream to the asset representing the next flag
			stream = assets.open(region + "/" + image);

		} catch (IOException e) {
		}
		
	}

	public Handler hanlder = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				dividePicture();
				reset(Contant.Level);
				break;
			case 9:
				numTimeUsed++;
				invalidate();
				break;
			case 10:
				Toast.makeText(GameView.this.getContext(),
						String.valueOf(msg.obj), Toast.LENGTH_SHORT).show();
				break;
			}
			super.handleMessage(msg);
		}

	};

	public void destroy() {
		currentMap.recycle();
		this.adBar.recycle();
		title.destroy();
		refreshBtn.destroy();
		showBtn.destroy();
		answareBtn.destroy();
		helpBtn.destroy();
	}

	public void reset(int level) {
		setupMap(level);

		timeThread.isRun = false;
		timeThread = null;
		timeThread = new TimeCaculateThread();

		this.numMoved = 0;
		this.numTimeUsed = 0;
		totalChances = Contant.Level;

		gameFlag = false;
		isStart = false;
		invalidate();
	}
	

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			moveSelected(x, y);
			onClickButtons(x, y);
			break;
		}
		finishGame();
		invalidate();
		return true;
	}

	public void dividePicture() {

		Bitmap bitmapResource = BitmapFactory.decodeStream(stream);

		if (bitmapResource.getWidth() != Contant.ScreenWidth) {
			Matrix mat = new Matrix();
			mat.setScale(
					(float) Contant.ScreenWidth / bitmapResource.getWidth(),
					(float) Contant.ScreenWidth / bitmapResource.getWidth());
			bitmapResource = Bitmap.createBitmap(bitmapResource, 0, 0,
					bitmapResource.getWidth(), bitmapResource.getHeight(), mat,
					true);
		}

		bitmapResource = Bitmap.createBitmap(bitmapResource, 0,
				bitmapResource.getHeight() - bitmapResource.getWidth(),
				Contant.ScreenWidth, Contant.ScreenWidth);

		imageList = ImageSpliter.split(bitmapResource, Contant.Level,
				Contant.Level);
		imageRects = new ArrayList<Rect>();

		for (ImagePiece image : imageList) {
			int lx = (image.index % Contant.Level)
					* image.getBitmap().getWidth();
			int ly = title.getHeight() + vgap + refreshBtn.getHeight() + vgap
					+ (int) (image.index / Contant.Level)
					* image.getBitmap().getHeight();
			imageRects.add(image.index, new Rect(lx, ly, lx
					+ image.getBitmap().getWidth(), ly
					+ image.getBitmap().getHeight()));
		}
	}



	protected void finishGame() {
		if (InChallengeGameView.arrayEqual(map, tempMap) == true
				&& gameFlag == false) {
			gameFlag = true;

			Util.db.updateStatus(""+id, 1);                    	
        	context.finish();                                 	
        	Intent intentScore = new Intent(context,
          			GameScreen.class);            	
          	intentScore.putExtra("id", id);
          	intentScore.putExtra("type", type);
          	context.startActivity(intentScore);
		}
	}


	int tmp;

	protected void setupMap(int level) {

		tempMap = new int[level][level];
		map = new int[level][level];
		for (int i = 0; i < level; i++) {
			for (int j = 0; j < level; j++) {
				tempMap[i][j] = i * level + j;
				map[i][j] = i * level + j;
			}
		}
		tempMap[level - 1][level - 1] = -1;
		map[Contant.Level - 1][Contant.Level - 1] = -1;
		int t, k;
		int m = level * level - 1;

		for (int n = 0; n < 100; n++) {
			t = (int) (Math.random() * m);

			k = (int) (Math.random() * m);
			while (k == t) {
				k = (int) (Math.random() * m);
			}

			tmp = map[t / level][t % level];
			map[t / level][t % level] = map[k / level][k % level];
			map[k / level][k % level] = tmp;

		}

	}

	@Override
	protected void onDraw(Canvas canvas) {

		canvas.drawColor(getResources().getColor(R.color.color_yellow));
		title.drawSelf(canvas, paint);

		paint.setTextSize(Contant.ScreenWidth / 25);
		paint.setTypeface(buttonTypeface);
		paint.setARGB(255, 0, 0, 0);
		paint.setTextAlign(Align.LEFT);
		canvas.drawText(getResources().getString(R.string.levelstr),
				this.getWidth() * 1 / 32, title.getHeight() / 3, paint);
		String level =getResources().getString(R.string.button_easy_text);
		switch (type) {
		case 1:
			level =getResources().getString(R.string.button_easy_text);
			break;
		case 2:
			level =getResources().getString(R.string.button_normal_text);
			break;
		case 3:
			level =getResources().getString(R.string.button_hard_text);
			break;
		default:
			break;
		}
		canvas.drawText(level,
				this.getWidth() * 1 / 32, title.getHeight() * 2 / 3, paint);
		paint.setTextAlign(Align.RIGHT);
		canvas.drawText(Contant.TxtTime, this.getWidth() * 31 / 32,
				title.getHeight() / 3, paint);
		canvas.drawText(numTimeUsed / 10 + " s", this.getWidth() * 31 / 32,
				title.getHeight() * 2 / 3, paint);
		paint.setTextAlign(Align.CENTER);
		canvas.drawText(Contant.TxtMoved, this.getWidth() * 1 / 2,
				title.getHeight() / 3, paint);
		canvas.drawText(numMoved + "", this.getWidth() * 1 / 2,
				title.getHeight() * 2 / 3, paint);
		paint.setARGB(255, 0, 0, 0);

		refreshBtn.drawSelf(canvas, paint);
		

		// backBtn.drawSelf(canvas, paint);
		canvas.drawBitmap(adBar, 0, Contant.ScreenHeight - adBar.getHeight(),
				paint);
		answareBtn.drawSelf(canvas, paint);
		showBtn.drawSelf(canvas, paint);
		helpBtn.drawSelf(canvas, paint);
		
		for (int i = 0; i < Contant.Level; i++) {
			for (int j = 0; j < Contant.Level; j++) {

				lx = ImageSpliter.WidthPer * j;

				ly = title.getHeight() + vgap + refreshBtn.getHeight() + vgap
						+ ImageSpliter.HeightPer * i;
				if (map[i][j] == -1) {
					canvas.drawRect(lx, ly, lx + ImageSpliter.WidthPer, ly
							+ ImageSpliter.HeightPer, paint);

				} else {

					ImagePiece image2 = imageList.get(map[i][j]);
					canvas.drawBitmap(image2.getBitmap(), lx, ly, paint);
					canvas.drawLine(lx, ly, lx + ImageSpliter.WidthPer, ly,
							paint);
					canvas.drawLine(lx, ly, lx, ly + ImageSpliter.HeightPer,
							paint);

					if (Contant.hasNum == true) {
						paint.setTextSize(ImageSpliter.WidthPer / 2);
						paint.setTypeface(buttonTypeface);
						paint.setARGB(230, 255, 255, 255);
						paint.setTextAlign(Align.CENTER);
						canvas.drawText(String.valueOf(map[i][j] + 1), lx
								+ ImageSpliter.WidthPer / 2, ly
								+ ImageSpliter.WidthPer / 2, paint);
						paint.setARGB(255, 0, 0, 0);
					}

				}

			}
		}

		super.onDraw(canvas);
	}

	protected void setupTitleAndButton() {
		title = new Sprite(BitmapFactory.decodeResource(this.getResources(),
				R.drawable.title));
		title.setPosition(0, 0);

		refreshBtn = new Sprite(BitmapFactory.decodeResource(this.getResources(),
				R.drawable.fresh));
		
		adBar = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.adbar);

		vgap = Contant.ScreenHeight - title.getHeight() - adBar.getHeight()
				- refreshBtn.getHeight() - Contant.ScreenWidth;
		vgap /= 3;
		int bottomMenusY = title.getHeight() + vgap;
		hgap = (Contant.ScreenWidth - refreshBtn.getWidth() * 6) / 7;
		refreshBtn.setPosition(hgap, bottomMenusY);
		if(wrong < 4){
			answareBtn = new Sprite(BitmapFactory.decodeResource(
					this.getResources(), R.drawable.answare));
		}else{
			answareBtn = new Sprite(BitmapFactory.decodeResource(
					this.getResources(), R.drawable.answaredisable));
		}		
		answareBtn.setPosition(hgap + hgap + refreshBtn.getWidth(), bottomMenusY);
		showBtn = new Sprite(BitmapFactory.decodeResource(this.getResources(),
				R.drawable.shownumber));
		showBtn.setPosition(hgap * 3 + refreshBtn.getWidth() * 2, bottomMenusY);
		helpBtn = new Sprite(BitmapFactory.decodeResource(this.getResources(),
				R.drawable.help));
		helpBtn.setPosition(hgap * 4 + refreshBtn.getWidth() * 3,
				bottomMenusY);
	}

	protected void lastMap() {

		Bitmap bitmapResource = BitmapFactory.decodeStream(stream);
		if (bitmapResource.getWidth() != Contant.ScreenWidth) {
			Matrix mat = new Matrix();
			mat.setScale(
					(float) Contant.ScreenWidth / bitmapResource.getWidth(),
					(float) Contant.ScreenWidth / bitmapResource.getWidth());
			bitmapResource = Bitmap.createBitmap(bitmapResource, 0, 0,
					bitmapResource.getWidth(), bitmapResource.getHeight(), mat,
					true);
		}

		bitmapResource = Bitmap.createBitmap(bitmapResource, 0,
				bitmapResource.getHeight() - bitmapResource.getWidth(),
				Contant.ScreenWidth, Contant.ScreenWidth);

		imageList = ImageSpliter.split(bitmapResource, Contant.Level,
				Contant.Level);
		imageRects = new ArrayList<Rect>();

		for (ImagePiece image : imageList) {
			int lx = (image.index % Contant.Level)
					* image.getBitmap().getWidth();
			int ly = title.getHeight() + refreshBtn.getHeight()
					+ (int) (image.index / Contant.Level)
					* image.getBitmap().getHeight();
			imageRects.add(image.index, new Rect(lx, ly, lx
					+ image.getBitmap().getWidth(), ly
					+ image.getBitmap().getHeight()));
		}
		this.numMoved = 0;
		this.numTimeUsed = 0;
	}

	protected void nextMap() {
		Bitmap bitmapResource = BitmapFactory.decodeStream(stream);

		if (bitmapResource.getWidth() != Contant.ScreenWidth) {
			Matrix mat = new Matrix();
			mat.setScale(
					(float) Contant.ScreenWidth / bitmapResource.getWidth(),
					(float) Contant.ScreenWidth / bitmapResource.getWidth());
			bitmapResource = Bitmap.createBitmap(bitmapResource, 0, 0,
					bitmapResource.getWidth(), bitmapResource.getHeight(), mat,
					true);
		}

		bitmapResource = Bitmap.createBitmap(bitmapResource, 0,
				bitmapResource.getHeight() - bitmapResource.getWidth(),
				Contant.ScreenWidth, Contant.ScreenWidth);

		imageList = ImageSpliter.split(bitmapResource, Contant.Level,
				Contant.Level);
		imageRects = new ArrayList<Rect>();

		for (ImagePiece image : imageList) {
			int lx = (image.index % Contant.Level)
					* image.getBitmap().getWidth();
			int ly = title.getHeight() + refreshBtn.getHeight()
					+ (int) (image.index / Contant.Level)
					* image.getBitmap().getHeight();
			imageRects.add(image.index, new Rect(lx, ly, lx
					+ image.getBitmap().getWidth(), ly
					+ image.getBitmap().getHeight()));
		}
		this.numMoved = 0;
		this.numTimeUsed = 0;
	}

	protected void moveSelected(int x, int y) {

		for (int i = 0; i < imageRects.size(); i++) {
			if (imageRects.get(i).contains(x, y)) {

				int xx = i / Contant.Level;
				int yy = i % Contant.Level;

				try {
					if (map[xx + 1][yy] == -1) {

						map[xx + 1][yy] = map[xx][yy];
						map[xx][yy] = -1;
						this.invalidate();
						numMoved++;
						playSound();
					}
				} catch (Exception e) {

				}
				try {
					if (map[xx][yy + 1] == -1) {
						map[xx][yy + 1] = map[xx][yy];
						map[xx][yy] = -1;
						this.invalidate();
						numMoved++;
						playSound();
					}
				} catch (Exception e) {
				}
				try {
					if (map[xx - 1][yy] == -1) {
						map[xx - 1][yy] = map[xx][yy];
						map[xx][yy] = -1;
						this.invalidate();
						numMoved++;
						playSound();
					}
				} catch (Exception e) {
				}
				try {
					if (map[xx][yy - 1] == -1) {
						map[xx][yy - 1] = map[xx][yy];
						map[xx][yy] = -1;
						this.invalidate();
						numMoved++;
						playSound();
					}
				} catch (Exception e) {
				}
			}
		}
		
	}

	protected void onClickButtons(int x, int y) {

		if (refreshBtn.isClicked(x, y)) {
			timeThread.isRun = false;
			reset(Contant.Level);
		} else if (answareBtn.isClicked(x, y) && wrong < 4) {
			showGuessDialog();
		} else if (showBtn.isClicked(x, y)) {
			Contant.hasNum = !Contant.hasNum;
			invalidate();
		}else if (helpBtn.isClicked(x, y)) {
			((GameActivity) getContext()).handler.sendEmptyMessage(5);
		} else {
			if (!isStart) {
				isStart = true;
				timeThread.start();
			}
		}

	} 
	
	public void pause(){
		if (timeThread.isRun) {
			timeThread.isRun = false;
			isStart = false;
		}
	}

	public void start(){
		if (!timeThread.isRun) {
			timeThread = null;
			timeThread = new TimeCaculateThread();
		}
	}
	protected class TimeCaculateThread extends Thread {
		public boolean isRun = false;

		public TimeCaculateThread() {
			isRun = true;
		}

		@Override
		public void run() {
			while (isRun) {
				try {
					hanlder.sendEmptyMessage(9);
					sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	}

	public Bitmap currentMap;

	protected Sprite title;
	protected Sprite answareBtn;
	protected Sprite refreshBtn;
	protected Sprite showBtn;
	protected Sprite helpBtn;
	
	protected Bitmap adBar;

	protected int numMoved = 0;
	protected double numTimeUsed = 0;
	protected int totalChances = 3;

	protected List<Rect> imageRects;
	protected List<ImagePiece> imageList;

	protected int[][] tempMap;
	protected int[][] map;

	protected boolean gameFlag; 
	protected boolean isStart;

	protected TimeCaculateThread timeThread;

	protected Paint paint = new Paint(); 

	protected float lx;
	protected float ly;

	protected AlertDialog windialog;
	protected AlertDialog aDialog;

	protected boolean isFreedomPic = false;

	private int vgap;

	private int hgap;
	
	
	private void initializeDialogButtons(Dialog dialog) {
		button1 = (Button) dialog.findViewById(R.id.answer1);
		button2 = (Button) dialog.findViewById(R.id.answer2);
		button3 = (Button) dialog.findViewById(R.id.answer3);
		button4 = (Button) dialog.findViewById(R.id.answer4);
	}
	private void setDialogListeners(OnClickListener clickListener) {
		button1.setOnClickListener(clickListener);
		button2.setOnClickListener(clickListener);
		button3.setOnClickListener(clickListener);
		button4.setOnClickListener(clickListener);
	}
	
	private void setDialogFonts() {		
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

	private void checkAnswer(View v) {
		String answer = getSelectedAnswer(v);
		if (tag.getName().equalsIgnoreCase(answer)) {
			if(SOUND_ENABLED) {
				playSound(R.raw.correct_word); 
				vibe.vibrate(200);
			}
			Util.db.updateStatus(""+tag.getId(), 3);
			showWinAppDialog(R.layout.dialog_win_game); 
		}else{
			if(SOUND_ENABLED) {
				playSound(R.raw.wrong_word); 
				vibe.vibrate(200);
			}
			wrong++;
			editor.putInt(Util.Prefs.WRONG_COUNT, wrong);				
			editor.commit();
			Util.db.updateStatus(""+tag.getId(), 0);
			showWinAppDialog(R.layout.dialog_lose_game); 
		}	
	}
	
	
	private void showWinAppDialog(int dialogLoseGame) {
		final Dialog dialog = new Dialog(context);
		Util.disableDialogBackground(dialog);
		dialog.setContentView(dialogLoseGame);
		dialog.setCanceledOnTouchOutside(false);
	    final Button okButton = (Button) dialog.findViewById(R.id.okButton);
	    okButton.setTypeface(Util.getTypeFaceByName(Util.Fonts.CHERY_LINEY, context.getAssets()));
	    final Button closeDialogButton = (Button) dialog.findViewById(R.id.closeButton);
	   				closeDialogButton.setTypeface(Util.getTypeFaceByName(Util.Fonts.CHERY_LINEY, context.getAssets()));
	   	OnClickListener rateClickLIstener = new OnClickListener() {				
			@Override
			public void onClick(View v) {
				context.finish();
				Intent intentScore = new Intent(context,
						StartGame.class);
				intentScore.putExtra("type", type);
				context.startActivity(intentScore);
			}
		};
		okButton.setOnClickListener(rateClickLIstener);
		closeDialogButton.setOnClickListener(rateClickLIstener);
		dialog.show();				   
	}
	private void playSound(final int id) {
		Thread thread = new Thread(new Runnable() {			
			@Override
			public void run() {
				MediaPlayer.create(context, id).start();
			}
		});
		thread.start();
	}
	private void showGuessDialog() {
		final Dialog dialog = new Dialog(context);
		Util.disableDialogBackground(dialog);
		dialog.setContentView(R.layout.dialog_guess);
		dialog.setCanceledOnTouchOutside(false);
		initializeDialogButtons(dialog);	      
	 
	   	OnClickListener clickLIstener = new OnClickListener() {				
			@Override
			public void onClick(View v) {				
				checkAnswer(v);
				dialog.dismiss();
				
			}
		};
		setDialogFonts();
		setQuestions();
		setDialogListeners(clickLIstener);
		dialog.show();				   
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
}
