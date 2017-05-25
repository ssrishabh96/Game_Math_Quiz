package gametut.googlelle.game_tut1.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;

import com.sdsmdg.harjot.rotatingtext.RotatingTextWrapper;
import com.sdsmdg.harjot.rotatingtext.models.Rotatable;

import gametut.googlelle.game_tut1.R;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_TIME = 2000;
    public static final String TAG=SplashActivity.class.getSimpleName();
    private RotatingTextWrapper rotatingTextWrapper;
    private Rotatable rotatable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.i(TAG, "onCreate: fired ");

        init();

        new Asynchronous().execute();
    }

    private void init() {
        Log.i(TAG, "init: fired ");

        AssetManager am = getApplicationContext().getAssets();
        Typeface tf = Typeface.createFromAsset(am, "fonts/satisfy.ttf");
        TextView playButton= (TextView) findViewById(R.id.splashTitle);
        playButton.setTypeface(tf);

        RotatingTextWrapper rotatingTextWrapper = (RotatingTextWrapper) findViewById(R.id.custom_switcher);
        rotatingTextWrapper.setSize(35);
        Rotatable rotatable = new Rotatable(Color.parseColor("#FFA036"), 1000, "Word", "Word01", "Word02");
        rotatable.setSize(35);
        rotatable.setAnimationDuration(500);
        rotatingTextWrapper.setContent("This is ?", rotatable);
    }

    private class Asynchronous extends AsyncTask{

        Intent intent;
        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute: fired ");
            intent = new Intent(SplashActivity.this, MainActivity.class);
        }
        @Override
        protected Object doInBackground(Object[] params) {
            Log.i(TAG, "doInBackground: fired ");
            /*  Use this method to load background
            * data that your app needs. */
            try {
                Thread.sleep(SPLASH_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Log.i(TAG, "onPostExecute: fired ");
//            Pass your loaded data here using Intent
//            intent.putExtra("data_key", "");
            startActivity(intent);
            finish();
        }
    }
}
