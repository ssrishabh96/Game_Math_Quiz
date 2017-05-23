package gametut.googlelle.game_tut1.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import gametut.googlelle.game_tut1.R;
import gametut.googlelle.game_tut1.activity.Main2Activity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private AdView mAdView;

    private EditText et;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private RelativeLayout showBackground_rl;
    private String dataName = "MyData";
    private String intName = "MyScore";
    private String intName1 = "MyLevel";
    private String unamedefault="username";
    private String uname;
    private Button save;
    private TextView tv;

    private int defaultInt = 0;


    //both activities can see this
    public static int hiScore;
    public static int hillevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadAds();

        tv=(TextView)findViewById(R.id.textView4);
        et=(EditText) findViewById(R.id.username);
        save=(Button) findViewById(R.id.savebtn);

        showBackground_rl=(RelativeLayout) findViewById(R.id.showBackround);

        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.numbers);

        BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            showBackground_rl.setBackground(ob);
        }


        prefs=getSharedPreferences(dataName,MODE_PRIVATE);
        et.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);

        editor = prefs.edit();


        prefs = getSharedPreferences(dataName, MODE_PRIVATE);

        hillevel= prefs.getInt(intName1, defaultInt);
        hiScore = prefs.getInt(intName, defaultInt);
        uname=prefs.getString(unamedefault, unamedefault);
        Toast.makeText(this,"Welcome "+uname,Toast.LENGTH_LONG).show();

        tv.setText("HighScore: "+hiScore+" Level: "+hillevel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et.getVisibility()!=View.VISIBLE)
                {
                et.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);}
                else
                {
                        et.setVisibility(View.INVISIBLE);
                        save.setVisibility(View.INVISIBLE);
                }
            }
        });


    }

    private void loadAds() {
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this,"Welcome "+uname,Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    public void start_play(View v)
    {
        Intent i=new Intent(this,Main2Activity.class);
        startActivity(i);

    }
    public void save(View v)
    {
        uname=et.getText().toString();
        if(uname.length()>1)
        {  editor.putString(unamedefault,uname);
            editor.commit();
        }

        et.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
        Toast.makeText(this,"Name Changed successfully.",Toast.LENGTH_SHORT).show();

    }

}
