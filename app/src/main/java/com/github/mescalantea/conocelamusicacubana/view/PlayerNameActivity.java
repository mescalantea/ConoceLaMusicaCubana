package com.github.mescalantea.conocelamusicacubana.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.github.mescalantea.conocelamusicacubana.R;
import com.github.mescalantea.conocelamusicacubana.controller.GameController;

public class PlayerNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_player_name);

        Typeface typeface = Typeface.createFromAsset(getAssets(),"cubano.ttf");

        Button start = ((Button)findViewById(R.id.startButton));
        start.setTypeface(typeface);

        ((TextView)findViewById(R.id.barTitleTextView)).setTypeface(typeface);

        findViewById(R.id.menuImageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer.create(PlayerNameActivity.this, R.raw.button_click).start();
                startActivity(new Intent(PlayerNameActivity.this, MenuActivity.class));
                finish();
            }
        });

        findViewById(R.id.menuImageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer.create(PlayerNameActivity.this, R.raw.button_click).start();
                Intent i = new Intent(PlayerNameActivity.this, MenuActivity.class);
                startActivity(i);
                finish();
            }
        });

        TextView title = ((TextView)findViewById(R.id.titleTextView));
        title.setTypeface(typeface);
        final EditText name = ((EditText)findViewById(R.id.nameEditText));
        name.setTypeface(typeface);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer.create(PlayerNameActivity.this, R.raw.button_click).start();

                if(name.getText().toString().equals("")){
                    AlertDialog dialog = new AlertDialog.Builder(PlayerNameActivity.this).create();
                    dialog.setTitle(getString(R.string.attention));
                    dialog.setMessage(getString(R.string.name_val));
                    //dialog.setCancelable(true);
                    dialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
                else{
                    v.setEnabled(false);
                    new AsyncGameInitializer().execute(new Object[]{name.getText().toString()});
                }
            }
        });
    }

    private class AsyncGameInitializer extends AsyncTask<Object,Void,Void>{
        @Override
        protected void onPreExecute() {
            findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Object[] params) {
            GameController gc = GameController.getInstance(PlayerNameActivity.this.getApplicationContext());
            gc.setPlayerName((String)params[0]);
            gc.initGame();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent i = new Intent(PlayerNameActivity.this, QuestionActivity.class);
            startActivity(i);
            finish();
        }
    }
}
