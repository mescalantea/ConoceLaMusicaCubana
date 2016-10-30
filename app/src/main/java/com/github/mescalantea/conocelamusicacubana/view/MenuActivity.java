package com.github.mescalantea.conocelamusicacubana.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mescalantea.conocelamusicacubana.R;

public class MenuActivity extends Activity
{
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		setVolumeControlStream(AudioManager.STREAM_MUSIC);// Allow the user to control the media volume of their device.

		Typeface typeface = Typeface.createFromAsset(getAssets(),"cubano.ttf");

		Button play = ((Button)findViewById(R.id.playButton));
		Button stats = ((Button)findViewById(R.id.scoresButton));
		play.setTypeface(typeface);
		stats.setTypeface(typeface);

		play.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MediaPlayer.create(MenuActivity.this, R.raw.button_click).start();
				Intent i = new Intent(MenuActivity.this, PlayerNameActivity.class);
				startActivity(i);
                finish();
			}
		});

		stats.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MediaPlayer.create(MenuActivity.this, R.raw.button_click).start();
				Intent i = new Intent(MenuActivity.this, RecordsActivity.class);
				startActivity(i);
				finish();
			}
		});

	}

}