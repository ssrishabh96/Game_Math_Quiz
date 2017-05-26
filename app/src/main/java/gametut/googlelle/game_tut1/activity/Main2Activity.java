package gametut.googlelle.game_tut1.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Random;

import gametut.googlelle.game_tut1.R;
import gametut.googlelle.game_tut1.activity.utility.CustomGameOverDialog;
import gametut.googlelle.game_tut1.activity.utility.CustomPauseDialog;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "GameScreen";
    private AdView mAdView;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private String intName = "MyScore";
    private String intName1 = "MyLevel";

    private int defaultInt = 0;
    private int hiScore, hiLevel;
    private int currentScore = 0;
    private int currentLevel = 1;
    private int correctAnswer;
    private Random randInt;
    private String operandstr;
    private int answerGiven = 0;
    private TextView score, level;
    private Button a, b, c, d;
    private TextView operanda, operator, operandb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Log.i(TAG, "onCreate: fired ");
        loadAds();

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
        hiScore = prefs.getInt(intName, defaultInt);
        hiLevel = prefs.getInt(intName1, defaultInt);
        //Either load our string or
        //if not available our default string
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

    }

    private void init() {
        Log.i(TAG, "init: fired ");

        operanda = (TextView) findViewById(R.id.operanda);
        operandb = (TextView) findViewById(R.id.operandb);
        operator = (TextView) findViewById(R.id.operator);
        score = (TextView) findViewById(R.id.score);
        level = (TextView) findViewById(R.id.level);

        a = (Button) findViewById(R.id.ansa);
        b = (Button) findViewById(R.id.ansb);
        c = (Button) findViewById(R.id.ansc);
        d = (Button) findViewById(R.id.ansd);
        a.setOnClickListener(this);
        b.setOnClickListener(this);
        c.setOnClickListener(this);
        d.setOnClickListener(this);


        randInt = new Random();
        int opeartion = randInt.nextInt(3);

        switch (opeartion) {
            case 0:
                operandstr = "+";
                break;

            case 1:
                operandstr = "*";
                break;

            case 2:
                operandstr = "-";
                break;
        }
        operanda.setText(operandstr);
        setQuestion();
    }

    private void loadAds() {
        Log.i(TAG, "loadAds: fired ");

        mAdView = (AdView) findViewById(R.id.adViewGameScreen);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onClick(View view) {


        answerGiven = 0;


        switch (view.getId()) {
            case R.id.ansa:

                answerGiven = Integer.parseInt("" + a.getText());

                break;
            case R.id.ansb:

                answerGiven = Integer.parseInt("" + b.getText());

                break;

            case R.id.ansc:

                answerGiven = Integer.parseInt("" + c.getText());

                break;
            case R.id.ansd:

                answerGiven = Integer.parseInt("" + d.getText());

                break;

        }

        updateScoreAndLevel(answerGiven);

    }

    void setQuestion() {

        Log.i(TAG, "Setting the Question: "+currentLevel);
        int numberRange = currentLevel * 8;

        int partA = randInt.nextInt(numberRange);
        partA+=2;//don't want a zero value
        int partB = randInt.nextInt(numberRange);
        partB++;


        int opeartion = randInt.nextInt(3);
        switch (opeartion) {
            case 0:
                operator.setText("+");
                operandstr = "+";
                correctAnswer = partA + partB;
                break;

            case 1:
                operator.setText("*");
                operandstr = "*";
                correctAnswer = partA * partB;
                break;

            case 2:
                operator.setText("-");
                operandstr = "-";
                correctAnswer = partA - partB;
                break;
        }

        int wrongAnswer1 = correctAnswer - randInt.nextInt(35);
        int wrongAnswer2 = correctAnswer + randInt.nextInt(45);
        int wrongAnswer3 = correctAnswer + randInt.nextInt(105);

        operanda.setText("" + partA);
        operandb.setText("" + partB);

        int buttonLayout = randInt.nextInt(4);
        switch (buttonLayout) {
            case 0:
                a.setText("" + correctAnswer);
                b.setText("" + wrongAnswer1);
                c.setText("" + wrongAnswer3);
                d.setText("" + wrongAnswer2);

                break;
            case 1:
                a.setText("" + wrongAnswer1);
                b.setText("" + wrongAnswer2);
                c.setText("" + correctAnswer);
                d.setText("" + wrongAnswer3);

                break;
            case 2:
                a.setText("" + wrongAnswer3);
                b.setText("" + correctAnswer);
                c.setText("" + wrongAnswer2);
                d.setText("" + wrongAnswer1);

                break;
            case 3:
                a.setText("" + wrongAnswer1);
                b.setText("" + wrongAnswer2);
                c.setText("" + wrongAnswer3);
                d.setText("" + correctAnswer);

                break;
        }


    }

    boolean isCorrect(int answerGiven) {

        boolean correctTrueOrFalse;
        if (answerGiven == correctAnswer) {
            correctTrueOrFalse = true;
        } else {
            correctTrueOrFalse = false;

        }
        return correctTrueOrFalse;
    }


    void updateScoreAndLevel(int answerGiven) {
        if (isCorrect(answerGiven)) {
            for (int i = 1; i <= currentLevel; i++) {
                currentScore = currentScore + i;
            }
            currentLevel++;
            score.setText("" + currentScore);
            level.setText("" + currentLevel);
            setQuestion();

        } else {

            final CustomGameOverDialog customGameOverDialog=new CustomGameOverDialog(this);
            customGameOverDialog.setHighScore(currentScore+"");
            customGameOverDialog.setCanceledOnTouchOutside(true);

            customGameOverDialog.replay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "GameOver Dialog: replay Button Tapped");
                    customGameOverDialog.dismiss();
                    startover();
                }
            });

            customGameOverDialog.show();

            // Set the HighScore if acheived
            if (currentScore > hiScore) {
                hiScore = currentScore;
                hiLevel = currentLevel;
                editor.putInt(intName, hiScore);
                editor.putInt(intName1, hiLevel);
                editor.commit();
                Toast.makeText(getApplicationContext(), "New Highscore",
                        Toast.LENGTH_SHORT).show();
            }

            customGameOverDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Log.i(TAG, "GameOver dialog Dismissed: Starting Over");

                    startover();
                }
            });

        }

    }

    private void startover() {
        Log.i(TAG, "Starting over the game");

        Toast.makeText(this,":Starting Over",Toast.LENGTH_SHORT).show();
        currentScore = 0;
        currentLevel = 1;
        score.setText("" + currentScore);
        level.setText("" + currentLevel);
        setQuestion();
    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed: fired ");
        final CustomPauseDialog mCustomPauseDialog=new CustomPauseDialog(this);
        mCustomPauseDialog.setScore(""+currentScore);

        mCustomPauseDialog.resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomPauseDialog.dismiss();
            }
        });

        mCustomPauseDialog.show();
    }
}

