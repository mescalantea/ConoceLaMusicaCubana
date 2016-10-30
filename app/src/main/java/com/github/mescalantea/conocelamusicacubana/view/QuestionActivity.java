package com.github.mescalantea.conocelamusicacubana.view;

import java.util.Collections;

import com.github.mescalantea.conocelamusicacubana.R;
import com.github.mescalantea.conocelamusicacubana.constant.Constants;
import com.github.mescalantea.conocelamusicacubana.controller.GameController;
import com.github.mescalantea.conocelamusicacubana.model.Connection;
import com.github.mescalantea.conocelamusicacubana.model.Question;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class QuestionActivity extends Activity
{
	private ImageButton play,clue,menu;
	private GameController gc;
	private Question currentQuestion;
	private MediaPlayer clip;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		Typeface typeface = Typeface.createFromAsset(getAssets(),"cubano.ttf");

		gc = GameController.getInstance(this);
		currentQuestion = gc.getCurrentQuestion();

		View.OnClickListener clickListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MediaPlayer.create(QuestionActivity.this, R.raw.button_click).start();
				boolean right = false;
				String extra = getString(R.string.extra_answer).replace("%", currentQuestion.getAnswer());
				if(((Button)v).getText().equals(currentQuestion.getAnswer())){
					gc.plusRight();
					right = true;
					extra = currentQuestion.getAnswer() +": "+ currentQuestion.getClue();
				}
				//intent
				Intent i = new Intent(QuestionActivity.this, ResultActivity.class);
				i.putExtra(Constants.RIGHT_ANSWER, right);
				i.putExtra(Constants.EXTRA_TEXT,extra);

                if(clip.isPlaying()){
                    clip.stop();
                }

				startActivity(i);
				finish();
			}
		};

		Button b1 = (Button) findViewById(R.id.button1);
		b1.setTypeface(typeface);
		Button b2 = (Button) findViewById(R.id.button2);
		b2.setTypeface(typeface);
		Button b3 = (Button) findViewById(R.id.button3);
		b3.setTypeface(typeface);
		Button b4 = (Button) findViewById(R.id.button4);
		b4.setTypeface(typeface);

		b1.setOnClickListener(clickListener);
		b2.setOnClickListener(clickListener);
		b3.setOnClickListener(clickListener);
		b4.setOnClickListener(clickListener);

		Collections.shuffle(currentQuestion.getOptions());

		b1.setText(currentQuestion.getOptions().get(0));
		b2.setText(currentQuestion.getOptions().get(1));
		b3.setText(currentQuestion.getOptions().get(2));
		b4.setText(currentQuestion.getOptions().get(3));

		TextView questionNo = (TextView)findViewById(R.id.questionNoTextView);
		questionNo.setTypeface(typeface);
		questionNo.setText(getResources().getString(R.string.question_no).replace("%",String.valueOf(gc.getLastQuestion())));

		TextView question = (TextView)findViewById(R.id.questionTextView);
		question.setTypeface(typeface);
		question.setText(getResources().getString(R.string.question).replace("%", currentQuestion.getCategory().toString()));

		switch (currentQuestion.getCategory().getCategoryId()){
			case 1:
				((ImageView)findViewById(R.id.iconImageView)).setImageDrawable(QuestionActivity.this.getResources().getDrawable(R.mipmap.artist));
				break;
			case 3:
				((ImageView)findViewById(R.id.iconImageView)).setImageDrawable(QuestionActivity.this.getResources().getDrawable(R.mipmap.instrument));
				break;
			default:
				((ImageView)findViewById(R.id.iconImageView)).setImageDrawable(QuestionActivity.this.getResources().getDrawable(R.mipmap.music));
				break;
		}

		play = (ImageButton) findViewById(R.id.playImageButton);
		clue = (ImageButton) findViewById(R.id.clueImageButton);
        menu = (ImageButton) findViewById(R.id.menuImageButton);
		
		play.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				MediaPlayer.create(QuestionActivity.this, R.raw.button_click).start();

				if(clip.isPlaying()){
					clip.stop();
				}
				clip.create(QuestionActivity.this, currentQuestion.getFile()).start();
			}
		});

		clue.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MediaPlayer.create(QuestionActivity.this, R.raw.button_click).start();
                AlertDialog dialog = new AlertDialog.Builder(QuestionActivity.this).create();
                dialog.setTitle(getString(R.string.clue));
                dialog.setMessage(currentQuestion.getClue());
                dialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
			}
		});

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer.create(QuestionActivity.this, R.raw.button_click).start();
                AlertDialog dialog = new AlertDialog.Builder(QuestionActivity.this).create();
                dialog.setTitle(getString(R.string.attention));
                dialog.setMessage(getString(R.string.quit_to_menu));
                dialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(QuestionActivity.this, MenuActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
                dialog.show();
            }
        });

		clip = MediaPlayer.create(QuestionActivity.this, currentQuestion.getFile());
		clip.start();
	}
	
	@Override
	public void onDestroy()
	{
		Connection.getInstance(QuestionActivity.this).closeDB();
        if(clip.isPlaying()){
            clip.stop();
        }
        super.onDestroy();
	}
	
}