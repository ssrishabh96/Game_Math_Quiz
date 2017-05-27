package gametut.googlelle.game_tut1.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import gametut.googlelle.game_tut1.R;

public class AboutUsActivity extends AppCompatActivity {

    private static final String TAG=AboutUsActivity.class.getSimpleName();
    private TextView appTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        Log.i(TAG, "onCreate: fired ");
        init();
    }

    private void init() {

        Log.i(TAG, "Initializing");
        appTitle=(TextView) findViewById(R.id.aboutUsAppTitle);
        AssetManager am = getApplicationContext().getAssets();
        Typeface tf = Typeface.createFromAsset(am, "fonts/satisfy.ttf");
        appTitle.setTypeface(tf);

    }

    public void email(View view) {
        Log.i(TAG, "email: fired ");

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{ "rishabh0148@gmail.com"});
        email.putExtra(Intent.EXTRA_SUBJECT, "Math Quiz App On Android");
        email.setType("message/rfc822");

        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }

    public void twitter(View view) {
        Log.i(TAG, "twitter: fired ");
        Intent intent = null;
        try {
            // get the Twitter app if possible
            this.getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=ssrishabh96"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/ssrishabh96"));
        }
        this.startActivity(intent);

    }

    public void linkedin(View view) {
        Log.i(TAG, "linkedin: fired ");
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/rishabh-agrawal-085541113"));
        startActivity(browserIntent);

    }

    public void mediaByte(View view) {
        Log.i(TAG, "mediaByte: fired ");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=imageopen.rishabh.andimage"));
        startActivity(intent);

    }

    public void copySave(View view) {
        Log.i(TAG, "copySave: fired ");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=dynamic.movies.rishabh.sliderwelcome"));
        startActivity(intent);

    }

    public void moreApps(View view) {
        Log.i(TAG, "moreApps: fired ");
        Log.i(TAG, "copySave: fired ");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://play.google.com/store/dev?id=Rishabh+Agrawal"));
        startActivity(intent);
    }
}
