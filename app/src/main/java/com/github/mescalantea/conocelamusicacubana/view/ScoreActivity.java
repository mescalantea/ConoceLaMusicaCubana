package com.github.mescalantea.conocelamusicacubana.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.github.mescalantea.conocelamusicacubana.R;
import com.github.mescalantea.conocelamusicacubana.controller.GameController;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

public class ScoreActivity extends AppCompatActivity {

    private TextView percentTextView;
    private Button menu;
    private Button newGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"cubano.ttf");

        ((TextView)findViewById(R.id.gameOverTextView)).setTypeface(typeface);
        ((TextView)findViewById(R.id.correctlyTextView)).setTypeface(typeface);

        percentTextView = (TextView)findViewById(R.id.percentTextView);
        percentTextView.setTypeface(typeface);

        menu = (Button)findViewById(R.id.mainMenuButton);
        newGame = (Button)findViewById(R.id.newRoundButton);

        menu.setTypeface(typeface);
        newGame.setTypeface(typeface);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer.create(ScoreActivity.this, R.raw.button_click).start();
                startActivity(new Intent(ScoreActivity.this, MenuActivity.class));
                finish();

            }
        });

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer.create(ScoreActivity.this, R.raw.button_click).start();
                startActivity(new Intent(ScoreActivity.this, PlayerNameActivity.class));
                finish();
            }
        });
        new AsyncScore().execute();
    }

    private class AsyncScore extends AsyncTask<Void,Integer,Void> {

        private final int SLEEP_TIME = 50;
        private MediaPlayer mp;

        @Override
        protected void onPreExecute() {
            percentTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
            mp = MediaPlayer.create(ScoreActivity.this, R.raw.score);
            mp.setLooping(true);
            mp.start();
        }

        @Override
        protected Void doInBackground(Void... params) {

            GameController gc = GameController.getInstance(ScoreActivity.this.getApplicationContext());
            int avg = gc.getAverage();

            for(int i = 0; i <= avg; ++i ){
                try {
                    Thread.sleep(SLEEP_TIME);
                    publishProgress(new Integer[]{i});
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mp.stop();
            findViewById(R.id.buttonLayout).setVisibility(View.VISIBLE);
            AnimatorSet set = new AnimatorSet();
            set.play(ObjectAnimator.ofFloat(findViewById(R.id.buttonLayout), "alpha", 1));
            set.setDuration(500).start();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int i = values[0];
            if(i < 50){
                percentTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
            else{
                if(i < 80){
                    percentTextView.setTextColor(getResources().getColor(R.color.colorMedium));
                }
                else{
                    percentTextView.setTextColor(getResources().getColor(R.color.colorSuccess));
                }
            }
            percentTextView.setText(i+"%");

        }

    }
}
