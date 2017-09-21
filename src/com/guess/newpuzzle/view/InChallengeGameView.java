package com.guess.newpuzzle.view;

import java.util.Arrays;

import android.app.Activity;

import com.guess.newpuzzle.data.Contant;

public class InChallengeGameView extends GameView {

	public InChallengeGameView(Activity context, int NPerPow, String image, int type, int id) {
		super(context, image, type,id);

		Contant.GameStart = true;

		initial();
	}

	public void destroy() {
		timeThread.isRun = false;
	}

	private void initial() {

		dividePicture();

		timeThread = new TimeCaculateThread();

		setupMap(Contant.Level);

	}

	public static boolean arrayEqual(int[][] a, int b[][]) {

		for (int i = 0; i < a.length; i++) {

			if (!Arrays.equals(a[i], b[i])) {
				return false;
			}
		}
		return true;
	}
	
}
