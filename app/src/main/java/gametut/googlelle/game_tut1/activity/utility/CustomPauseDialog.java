package gametut.googlelle.game_tut1.activity.utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import gametut.googlelle.game_tut1.R;
import gametut.googlelle.game_tut1.activity.MainActivity;

/**
 * Created by RISHABH on 5/25/2017.
 */

public class CustomPauseDialog extends Dialog implements View.OnClickListener {

    private static final String TAG=CustomPauseDialog.class.getSimpleName();

    public View view;
    private Context mContext;
    private TextView textView;
    public Button resume;
    private Button mainmenu,exit;


    public CustomPauseDialog(@NonNull Context context) {
        super(context);
        this.mContext=context;
        Log.i(TAG, "CustomPauseDialog: fired ");
        setContentView(R.layout.custom_pause_dialog);
        view = getWindow().getDecorView();
        view.setBackgroundResource(android.R.color.transparent);

        resume=(Button) findViewById(R.id.pauseDialogResumeButton);
        mainmenu=(Button) findViewById(R.id.mainMenuButtonPauseDialog);
        exit=(Button) findViewById(R.id.exitButtonPauseDialog);
        textView=(TextView) findViewById(R.id.pauseScoreDialog);
        mainmenu.setOnClickListener(this);
        exit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        Log.i(TAG, "onClick: method ");

        if(view.getId()==R.id.mainMenuButtonPauseDialog){

            Log.i(TAG, "PAUSE: MainMenu Chosen");

            Intent menu = new Intent(mContext, MainActivity.class);
            mContext.startActivity(menu);
            ((Activity) mContext).finish();


        }else if(view.getId()==R.id.exitButtonPauseDialog){

            Log.i(TAG, "Game Quit from GameScreen: fired ");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ((Activity) mContext).finishAndRemoveTask();
            }else {
                ((Activity) mContext).finish();
            }
        }

    }

    public void setScore(String score){

        if(score.isEmpty() || score==null){
        score="0";
        }

        textView.setText("Score: "+score);

    }
}
