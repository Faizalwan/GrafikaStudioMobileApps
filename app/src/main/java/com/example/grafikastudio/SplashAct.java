package com.example.grafikastudio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.grafikastudio.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.github.ybq.android.spinkit.style.ThreeBounce;

public class SplashAct extends AppCompatActivity {

    Animation app_splash;
    ImageView app_logo;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getUsernameLocal();

        ProgressBar progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite doubleBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);


        //load animation
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);


        //load element
        app_logo = findViewById(R.id.app_logo);

        //run animation
        app_logo.startAnimation(app_splash);


    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
        if(username_key_new.isEmpty()){
            //setting timer 2 detik - jika username tidak kosong ke login
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent gogethome = new Intent(SplashAct.this,LoginAct.class);
                    startActivity(gogethome);
                    finish();
                }
            }, 2000);
        }
        else{
            //setting timer 2 detik - jika tidak kosong ke home
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent gogetstarted = new Intent(SplashAct.this,MainActivity.class);
                    startActivity(gogetstarted);
                    finish();
                }
            }, 2000);
        }
    }
}
