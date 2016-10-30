package com.github.mescalantea.conocelamusicacubana.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mescalantea.conocelamusicacubana.R;
import com.github.mescalantea.conocelamusicacubana.constant.Constants;
import com.github.mescalantea.conocelamusicacubana.controller.GameController;

public class ResultActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"cubano.ttf");

        boolean right = getIntent().getBooleanExtra(Constants.RIGHT_ANSWER,false);
        String extra = getIntent().getStringExtra(Constants.EXTRA_TEXT);

        Button next = (Button) findViewById(R.id.nextButton);
        next.setTypeface(typeface);
        TextView questionResult = (TextView)findViewById(R.id.resultTextView);
        questionResult.setTypeface(typeface);
        TextView questionExtra = (TextView)findViewById(R.id.extraTextView);
        questionExtra.setTypeface(typeface);
        questionExtra.setText(extra);

        if(right){
            questionResult.setText(getString(R.string.rightAnswer));
            questionResult.setTextColor(getResources().getColor(R.color.colorSuccess));
            mediaPlayer = MediaPlayer.create(ResultActivity.this, R.raw.aplausos);
        }
        else{
            questionResult.setText(getString(R.string.badAnswer));
            questionResult.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            mediaPlayer = MediaPlayer.create(ResultActivity.this, R.raw.wrong_crowd);
        }

        mediaPlayer.start();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                MediaPlayer.create(ResultActivity.this, R.raw.button_click).start();
                Intent i = null;
                if( GameController.getInstance(ResultActivity.this).nextQuestion() != null ){
                    i = new Intent(ResultActivity.this, QuestionActivity.class);
                }
                else{
                    GameController.getInstance(ResultActivity.this).saveScore();
                    i = new Intent(ResultActivity.this, ScoreActivity.class);
                }
                startActivity(i);
                finish();
            }
        });
    }
}
