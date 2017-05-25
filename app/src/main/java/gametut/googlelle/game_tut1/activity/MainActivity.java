package gametut.googlelle.game_tut1.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import gametut.googlelle.game_tut1.R;
import gametut.googlelle.game_tut1.activity.activity.Settings;
import gametut.googlelle.game_tut1.activity.utility.CustomDialog;
import gametut.googlelle.game_tut1.activity.utility.CustomGameOverDialog;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private AdView mAdView;
    private SharedPreferences prefs;
    private String intName = "MyScore";
    private String intName1 = "MyLevel";
    private String usernameKey = "", usernameDefault = "";
    private String username = "";
    private int defaultInt = 1;

    //both activities can see this
    public static int hiScore;
    public static int hillevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: fired ");

        loadAds();
        init();

        username = prefs.getString(usernameKey, usernameDefault);
        Toast.makeText(this, "Welcome " + username, Toast.LENGTH_LONG).show();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void init() {
        Log.i(TAG, "init: fired ");
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        usernameDefault = getResources().getString(R.string.username_default);
        usernameKey = getResources().getString(R.string.username_key);

        hillevel = prefs.getInt(intName1, defaultInt);
        hiScore = prefs.getInt(intName, 0);

        AssetManager am = getApplicationContext().getAssets();
        Typeface tf = Typeface.createFromAsset(am, "fonts/satisfy.ttf");
        TextView playButton= (TextView) findViewById(R.id.appTitle);
        playButton.setTypeface(tf);

    }

    private void loadAds() {
        Log.i(TAG, "loadAds: fired ");

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void start_play(View v) {
        Log.i(TAG, "start_play: fired ");
        Intent i = new Intent(this, Main2Activity.class);
        startActivity(i);
    }

    public void showHighScores(View view) {

        Log.i(TAG, "showHighScores: fired ");
        init();
        CustomDialog customizeDialog = new CustomDialog(this);
        customizeDialog.setTitle("HighScores");
        customizeDialog.setMessage("HighScore: " + hiScore + " Level: " + hillevel);
        customizeDialog.show();
    }

    public void exit(View view) {
        Log.i(TAG, "exit: fired ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAndRemoveTask();
        } else {
            finish();
        }
    }

    public void settings(View view) {

        Log.i(TAG, "settings: launched");
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);

    }

    public void showCredits(View view) {
        Log.i(TAG, "showCredits: fired ");
        CustomDialog customizeDialog = new CustomDialog(this);
        customizeDialog.setTitle("Author:");
        customizeDialog.setMessage("Rishabh Agrawal");
        //customizeDialog.show();

        CustomGameOverDialog dialog = new CustomGameOverDialog(this);
        dialog.setHighScore("55");
        dialog.show();

    }

    @Override
    public void onBackPressed(){
        Log.i(TAG, "onBackPressed: fired ");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.app_name)+": QUIT?");
        builder.setMessage("Are you sure you wanna leave the game?");

        builder.setPositiveButton("Yes, I Quit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.setNegativeButton("No, I Wanna Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
