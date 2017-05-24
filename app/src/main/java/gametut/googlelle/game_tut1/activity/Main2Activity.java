package gametut.googlelle.game_tut1.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import gametut.googlelle.game_tut1.R;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {


    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String dataName = "MyData";
    String intName = "MyScore";
    String intName1 = "MyLevel";

    int defaultInt = 0;
    int hiScore,hiLevel;

    int currentScore = 0;
    int currentLevel = 1;
    int correctAnswer ;
    Random randInt ;

    String operandstr;

    int answerGiven = 0;
    TextView score, level;

    Button a, b, c, d;
    TextView operanda, operator, operandb;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            menu.add(0,0,0,"hello");

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        prefs=getSharedPreferences(dataName,MODE_PRIVATE);
        editor = prefs.edit();
        hiScore = prefs.getInt(intName, defaultInt);
        hiLevel = prefs.getInt(intName1, defaultInt);
        //Either load our string or
        //if not available our default string
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                operandstr="+";
                break;

            case 1:
                operandstr="*";
                break;

            case 2:
                operandstr="-";
                break;
        }
        operanda.setText(operandstr);
        setQuestion();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
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
        int numberRange = currentLevel * 8;
        int partA = randInt.nextInt(numberRange);
        partA++;//don't want a zero value
        int partB = randInt.nextInt(numberRange);
        partB++;


        int opeartion = randInt.nextInt(3);
        switch (opeartion) {
            case 0:
                operator.setText("+");
                operandstr="+";
                correctAnswer = partA + partB;
                break;

            case 1:
                operator.setText("*");
                operandstr="*";
                correctAnswer = partA * partB;
                break;

            case 2:
                operator.setText("-");
                operandstr="-";
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



    void updateScoreAndLevel(int answerGiven){
        if(isCorrect(answerGiven)){
            for(int i = 1; i <= currentLevel; i++){
                currentScore = currentScore + i;
            }
            currentLevel++;
        }
        else{

            if(currentScore > hiScore) {
                hiScore = currentScore;
                hiLevel=currentLevel;
                editor.putInt(intName, hiScore);
                editor.putInt(intName1, hiLevel);
                editor.commit();
                Toast.makeText(getApplicationContext(), "New Hiscore",
                        Toast.LENGTH_SHORT).show();
            }
            currentScore = 0;
            currentLevel = 1;
        }

        score.setText("" + currentScore);
        level.setText("" + currentLevel);
        setQuestion();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}

