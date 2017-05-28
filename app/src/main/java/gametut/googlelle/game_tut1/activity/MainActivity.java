package gametut.googlelle.game_tut1.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Arrays;

import gametut.googlelle.game_tut1.R;
import gametut.googlelle.game_tut1.activity.activity.Settings;
import gametut.googlelle.game_tut1.activity.activity.UserDashboard;
import gametut.googlelle.game_tut1.activity.utility.CustomDialog;
import gametut.googlelle.game_tut1.activity.utils.IabHelper;
import gametut.googlelle.game_tut1.activity.utils.IabResult;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private SharedPreferences prefs;
    private String intName = "MyScore";
    private String intName1 = "MyLevel";
    private String usernameKey = "", usernameDefault = "";
    private String username = "";
    private int defaultInt = 1;
    private CustomDialog customizeDialog;
    private TextView signInStatusTextView;
    RelativeLayout container1;
    AnimationDrawable anim;
    private static final int RC_SIGN_IN = 123;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    private IabHelper mHelper;
    //both activities can see this
    public static int hiScore;
    public static int hillevel;
    static final String ITEM_SKU = "android.test.purchased";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: fired ");

        FirebaseCrash.log("Game Started");
        Log.d("FCM", "Instance ID: " + FirebaseInstanceId.getInstance().getToken());

        FirebaseCrash.report(new Exception("My first Android non-fatal error"));


        loadAds();
        loadBackground();
        init();
        username = prefs.getString(usernameKey, usernameDefault);
        Toast.makeText(this, "Welcome " + username, Toast.LENGTH_LONG).show();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void loadBackground() {
        Log.i(TAG, "loadBackground: fired ");
        container1 = (RelativeLayout) findViewById(R.id.showBackround);
        anim = (AnimationDrawable) container1.getBackground();

        if (anim != null) {
            anim.setEnterFadeDuration(3000);
            anim.setExitFadeDuration(1500);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: starting the gradient animation");
        if (anim != null && !anim.isRunning())
            anim.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: stopping the gradient animation");
        if (anim != null && anim.isRunning())
            anim.stop();

    }

    private void init() {
        Log.i(TAG, "init: fired ");
        customizeDialog = new CustomDialog(this);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        usernameDefault = getResources().getString(R.string.username_default);
        usernameKey = getResources().getString(R.string.username_key);
        hillevel = prefs.getInt(intName1, defaultInt);
        hiScore = prefs.getInt(intName, 0);
        signInStatusTextView = (TextView) findViewById(R.id.signInstatus);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/satisfy.ttf");
        TextView playButton = (TextView) findViewById(R.id.appTitle);
        playButton.setTypeface(tf);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            // already signed in
            signInStatusTextView.setText("Hey " + auth.getCurrentUser().getDisplayName());
        } else {
            // not signed in
            signInStatusTextView.setText("Not Signed In");
        }

    }

    private void handleUserSignInStatus() {
        Log.i(TAG, "handleUserSignInStatus: fired ");

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // already signed in
            startActivity(new Intent(this, UserDashboard.class));
        } else {
            // not signed in
            Toast.makeText(this, "Sign in First", Toast.LENGTH_SHORT).show();
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build())).setTheme(R.style.GreenTheme).build(), RC_SIGN_IN);
        }


    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == ResultCodes.OK) {
                Toast.makeText(this, "Signed In", Toast.LENGTH_SHORT).show();
                signInStatusTextView.setText("Signed In");


            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    FirebaseCrash.log(getResources().getString(R.string.sign_in_cancelled));
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    FirebaseCrash.log(getResources().getString(R.string.no_internet_connection));
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    FirebaseCrash.log(getResources().getString(R.string.unknown_error));
                    return;
                }
            }

            FirebaseCrash.log(getResources().getString(R.string.unknown_error));
        }
    }


    private void loadAds() {
        Log.i(TAG, "loadAds: fired ");
        MobileAds.initialize(this,"ca-app-pub-9816038748687358~8649148824");
        AdView mAdView;
        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId("ca-app-pub-9816038748687358/5416480822");

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void start_play(View v) {
        Log.i(TAG, "start_play: fired ");
        Intent i = new Intent(this, Main2Activity.class);
        startActivity(i);
        finish();
    }

    public void showHighScores(View view) {

        Log.i(TAG, "showHighScores: fired ");
        init();
        customizeDialog.setTitle("HighScores");
        customizeDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //style id

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
        startActivity(new Intent(this, AboutUsActivity.class));

    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed: fired ");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.app_name) + ": QUIT?");
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

    public void authenticateUser(View view) {

        Log.i(TAG, "authenticateUser: fired ");
        handleUserSignInStatus();

    }

    void checkForInAppBilling() {
        String base64EncodedPublicKey =
                "<your license key here>";

        mHelper = new IabHelper(this, base64EncodedPublicKey);

        mHelper.startSetup(new
                                   IabHelper.OnIabSetupFinishedListener() {
                                       public void onIabSetupFinished(IabResult result) {
                                           if (!result.isSuccess()) {
                                               Log.d(TAG, "In-app Billing setup failed: " +
                                                       result);
                                           } else {
                                               Log.d(TAG, "In-app Billing is set up OK");
                                           }
                                       }
                                   });

    }
}
