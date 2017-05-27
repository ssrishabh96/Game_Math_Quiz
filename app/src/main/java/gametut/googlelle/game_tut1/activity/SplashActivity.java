package gametut.googlelle.game_tut1.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;



import gametut.googlelle.game_tut1.R;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_TIME = 1000;
    public static final String TAG=SplashActivity.class.getSimpleName();


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


//        Typeface typeface = Typeface.createFromAsset(am, "fonts/Raleway-Light.ttf");
//        Typeface typeface2 = Typeface.createFromAsset(am, "fonts/Reckoner_Bold.ttf");
//
//        RotatingTextWrapper rotatingTextWrapper = (RotatingTextWrapper) findViewById(R.id.custom_switcher);
//        rotatingTextWrapper.setSize(35);
//        rotatingTextWrapper.setTypeface(typeface2);
//
//        Rotatable rotatable = new Rotatable(Color.parseColor("#FFA036"), 500, "FAST", "QUICK", "GENIUS");
//        rotatable.setSize(35);
//        rotatable.setTypeface(typeface);
//        rotatable.setInterpolator(new AccelerateInterpolator());
//        rotatable.setAnimationDuration(500);
//
//        Rotatable rotatable2 = new Rotatable(Color.parseColor("#123456"), 500, "Word03", "Word04", "Word05");
//        rotatable2.setSize(25);
//        rotatable2.setTypeface(typeface);
//        rotatable2.setInterpolator(new DecelerateInterpolator());
//        rotatable2.setAnimationDuration(500);
//
//        rotatingTextWrapper.setContent("BE ? and ?", rotatable, rotatable2);

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
            System.gc();

            finish();
        }
    }
}
