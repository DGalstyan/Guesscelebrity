package com.guess.newpuzzle.util;

import android.graphics.Bitmap;

public class ImagePiece {
	public int index = 0;

	private Bitmap bitmap = null;

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
}
