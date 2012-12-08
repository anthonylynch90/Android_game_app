package com.game.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputFilter;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Game extends Activity implements OnTouchListener, OnClickListener{
	private GameActivity game = null;
	private boolean isSantaHidden = false;
	private boolean sound;
	private int timesFound = 0;
	private int check = 0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		showDialog();
		game = (GameActivity) findViewById(R.id.the_canvas);
		game.setOnTouchListener(this);
		Button b = (Button) findViewById(R.id.the_button);
		b.setOnClickListener(this);
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {		
		if (v.getId() == R.id.the_canvas) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				if (isSantaHidden) {
					TextView tv = (TextView)findViewById (R.id.the_label);
					String value = game.takeAGuess(event.getX(), event.getY());
					if(value.equals("BULLSEYE")){
						if(sound){
							MediaPlayer mp = MediaPlayer.create(this, R.raw.bells);
							mp.start();
						}
						game.hideTheFlag();
						tv.setText("You found Him");
						tv.setTextColor(Color.GREEN);
						timesFound = timesFound+1;
					}
					if(value.equals("HOT")){
						tv.setText("You're hot!");
						tv.setTextColor(Color.RED);
					}
					if(value.equals("WARM")){
						tv.setText("Getting warm...");
						tv.setTextColor(Color.YELLOW);
					}
					if(value.equals("COLD")){
						tv.setText("You're cold.");
						tv.setTextColor(Color.BLUE);
					}
				}
			}
			return true;
		}
		return false;
	}
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.the_button) {
			TextView tv = (TextView)findViewById (R.id.the_label);
			tv.setText("");
			Button b = (Button) findViewById(R.id.the_button);
			isSantaHidden = !isSantaHidden;
			if (isSantaHidden) {
				b.setText("Give Up game");
				game.hideTheFlag();
				timer();
			} else {
				b.setText("Hide Santa");
				game.giveUp();
			}
		}
	}
	
	public void showDialog(){
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Do you want to enable sound");
		alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        sound = true;
		    }
		});

		alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        sound = false;
		    }
		});
		AlertDialog dialog = alert.create();
		dialog.show();
	}
	
	public void timer(){
		new CountDownTimer(30000,1000){

            @Override
            public void onTick(long miliseconds){
            	if(check != timesFound){
            		
            	}
            	check = timesFound;
            }

            @Override
            public void onFinish(){
            	Button b = (Button) findViewById(R.id.the_button);
            	TextView tv = (TextView)findViewById (R.id.the_label);
				isSantaHidden = false;
				game.giveUp();
				b.setText("Hide Santa");
				tv.setText("Time is up, You found him "+timesFound+" times");
				tv.setTextColor(Color.GREEN);
            }
        }.start();
	}
}