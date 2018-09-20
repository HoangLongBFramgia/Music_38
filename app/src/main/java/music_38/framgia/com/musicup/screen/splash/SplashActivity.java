package music_38.framgia.com.musicup.screen.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import music_38.framgia.com.musicup.R;
import music_38.framgia.com.musicup.screen.main.MainActivity;

public class SplashActivity extends AppCompatActivity {

    public static final int DELAY = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(mIntent);
            }
        }, DELAY);
    }

}
