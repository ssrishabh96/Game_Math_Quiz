package gametut.googlelle.game_tut1.activity.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;

import gametut.googlelle.game_tut1.R;
import gametut.googlelle.game_tut1.activity.MainActivity;

public class UserDashboard extends AppCompatActivity {

    private TextView mName,mEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdashboard);

        mName=(TextView) findViewById(R.id.dashboardName);
        mEmail=(TextView) findViewById(R.id.dashboardEmail);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            mName.setText(user.getDisplayName());
            mEmail.setText(user.getEmail());
        }



    }

    public void signout(View view) {
        FirebaseCrash.log("signout: Signing Out");

        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // user is now signed out
                        startActivity(new Intent(UserDashboard.this, MainActivity.class));
                        finish();
                    }
                });
    }
}
