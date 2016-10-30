package com.github.mescalantea.conocelamusicacubana.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mescalantea.conocelamusicacubana.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Typeface typeface = Typeface.createFromAsset(this.getApplicationContext().getAssets(),"cubano.ttf");
        ((TextView)findViewById(R.id.developersTextView)).setTypeface(typeface);

        Thread logo = new Thread(){
            public void run() {
                try {

                    sleep(3000);

                    Intent i=new Intent(SplashScreenActivity.this, MenuActivity.class);
                    startActivity(i);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                finally{
                    finish();
                }
            }
        };

        logo.start();
    }
}
