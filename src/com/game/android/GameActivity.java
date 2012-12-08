package com.game.android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class GameActivity extends View{
	private int mFlagX = -1;
	private int mFlagY = -1;
	private Bitmap mBitmap = null;
	private Paint mPaint = null;
	private boolean isSantaHidden = false;
	private int mBoundX = -1;
	private int mBoundY = -1;
	public final int CLOSER = 50;
	public final int CLOSE = 100;

	public GameActivity(Context context, AttributeSet aSet) {
		super(context, aSet);
		mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.santa);
		mPaint = new Paint();
		mPaint.setColor(Color.DKGRAY);
	}
	
	public void hideTheFlag(){
		mFlagX = (int) Math.ceil(Math.random() * mBoundX);
		mFlagY = (int) Math.ceil(Math.random() * mBoundY);
		isSantaHidden = true;
		invalidate();
	}
	
	public void giveUp(){
		isSantaHidden = false;
		invalidate();
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		if ((mFlagX < 1) || (mFlagY < 1)) {
			mFlagX = (int) (getWidth() / 2) - mBitmap.getWidth() / 2;
			mFlagY = (int) (getHeight() / 2) - mBitmap.getHeight() / 2;
			mBoundX = (int)getWidth() - mBitmap.getWidth();
			mBoundY = (int)getHeight() - mBitmap.getHeight();
		}
		canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
		if (!isSantaHidden) {
			canvas.drawBitmap(mBitmap, mFlagX, mFlagY, null);
		}
	}
	public String takeAGuess(float x, float y) {
		//this is our "warm" area
		Rect prettyClose = new Rect(mFlagX - CLOSE, mFlagY - CLOSE, mFlagX+mBitmap.getWidth() + CLOSE, mFlagY+mBitmap.getHeight() + CLOSE);
		//normalize
		if (prettyClose.left < 0) prettyClose.left = 0;
		if (prettyClose.top < 0) prettyClose.top = 0;
		if (prettyClose.right > mBoundX) prettyClose.right = mBoundX;
		if (prettyClose.bottom > mBoundY) prettyClose.bottom = mBoundY;
		//this is our "hot" area
		Rect reallyClose = new Rect(mFlagX - CLOSER, mFlagY - CLOSER, mFlagX+mBitmap.getWidth() + CLOSER, mFlagY+mBitmap.getHeight() + CLOSER);
		//normalize
		if (reallyClose.left < 0) reallyClose.left = 0;
		if (reallyClose.top < 0) reallyClose.top = 0;
		if (reallyClose.right > mBoundX) reallyClose.right = mBoundX;
		if (reallyClose.bottom > mBoundY) reallyClose.bottom = mBoundY;
		//this is the area that contains our flag
		Rect bullsEye = new Rect(mFlagX, mFlagY, mFlagX+mBitmap.getWidth(), mFlagY+mBitmap.getHeight());
		//check to see where on the board the user pressed
		if (bullsEye.contains((int) x, (int)y)) {
			//found it
			isSantaHidden = false;
			invalidate();
			return "BULLSEYE";
		} else if (reallyClose.contains((int) x, (int)y)) {
			//hot
			return "HOT";
		} else if (prettyClose.contains((int)x, (int)y)) {
			//warm
			return "WARM";
		} else {
			//not even close
			return "COLD";
		}
	}
}