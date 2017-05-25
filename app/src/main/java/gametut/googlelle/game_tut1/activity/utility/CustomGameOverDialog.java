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

public class CustomGameOverDialog extends Dialog implements View.OnClickListener {

    private static final String TAG=CustomGameOverDialog.class.getSimpleName();
    private View v = null;
    private Context mContext;

    private Button exitButton, mainMenu, replay;

    public CustomGameOverDialog(@NonNull Context mContext) {
        super(mContext);
        this.mContext=mContext;
        setContentView(R.layout.custom_gameover_dialog);
        v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);

        exitButton=(Button) findViewById(R.id.exitButtonGameOverDialog);
        mainMenu=(Button) findViewById(R.id.mainMenuGameoverDialogButton);
        replay=(Button) findViewById(R.id.replayButtonGameOverDialog);

        exitButton.setOnClickListener(this);
        mainMenu.setOnClickListener(this);
        replay.setOnClickListener(this);

    }

    public void setHighScore(String score) {

        TextView textview = (TextView) findViewById(R.id.scoreGameOverDialog);

        if (score.isEmpty() || score == null) {
            textview.setText("SCORE: 0");
        } else {
            textview.setText("SCORE: "+score);


        }
    }

    @Override
    public void onClick(View view) {
        Log.i(TAG, "onClick: method ");

        if(view.getId()==R.id.exitButtonGameOverDialog){

            Log.i(TAG, "Exit: button on GAMEOVER");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ((Activity) mContext).finishAndRemoveTask();
            }else {
                ((Activity) mContext).finish();
           }

        }else if(view.getId()==R.id.mainMenuGameoverDialogButton){

            Log.i(TAG, "GAMEOVER: MainMenu Chosen");

            Intent menu = new Intent(mContext, MainActivity.class);
            mContext.startActivity(menu);
            ((Activity) mContext).finish();


        }else if(view.getId()==R.id.replayButtonGameOverDialog){


        }

    }
}
