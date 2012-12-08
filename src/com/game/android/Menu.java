package com.game.android;


import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class Menu extends ListActivity {
	private String[] values;
	private String itemPressed;
	private Context context;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = this;
		this.values = new String[]{"Camera", "Game"};
	    
		MenuArrayAdapter adapter = new MenuArrayAdapter(this, values);
		setListAdapter(adapter);
	  }
	  
	  @Override
		protected void onListItemClick(ListView l, View v, int position, long id) {
			itemPressed = (String) getListAdapter().getItem(position);
			sort(itemPressed);
		}

	  public void sort(String item){
		  if(item.equals("Camera")){
			  Intent camera = new Intent(this, VideoCapture.class);
			  startActivity(camera);
		  }
		  if(item.equals("Game")){
			  Intent game = new Intent(this, Game.class);
			  startActivity(game);
		  }
	  }
}

