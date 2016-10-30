package com.github.mescalantea.conocelamusicacubana.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.mescalantea.conocelamusicacubana.R;
import com.github.mescalantea.conocelamusicacubana.dao.ScoreDAO;

public class RecordsActivity extends AppCompatActivity {

    private TextView nothingToShow;
    private RecyclerView recView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"cubano.ttf");

        nothingToShow = (TextView) findViewById(R.id.nothingToShowTextView);
        nothingToShow.setTypeface(typeface);

        ((TextView)findViewById(R.id.barTitleTextView)).setTypeface(typeface);

        recView = (RecyclerView) findViewById(R.id.recView);


        findViewById(R.id.menuImageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer.create(RecordsActivity.this, R.raw.button_click).start();
                startActivity(new Intent(RecordsActivity.this, MenuActivity.class));
                finish();
            }
        });

        new AsyncListLoad().execute();
    }

    private class AsyncListLoad extends AsyncTask<Void,Void,Void> {

        private ScoreDAO scoreDAO;
        private ScoreListAdapter adapter;

        @Override
        protected Void doInBackground(Void... params) {
            adapter = new ScoreListAdapter(scoreDAO.getScores());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(adapter.getItemCount() > 0){
                recView.setAdapter(adapter);
                recView.setHasFixedSize(true);
                recView.setLayoutManager(new LinearLayoutManager(RecordsActivity.this,LinearLayoutManager.VERTICAL,false));
            }
            else{
                recView.setVisibility(View.GONE);
                nothingToShow.setVisibility(View.VISIBLE);
            }
            findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
        }

        public AsyncListLoad(){
            this.scoreDAO = new ScoreDAO(RecordsActivity.this.getApplicationContext());
        }
    }
}
