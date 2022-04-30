package com.example.navigationdrawerexample;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class SplashScreenActivity extends AppCompatActivity {

       //Variables
        Animation topAnim,bottomAnim;
        TextView logo,slogan;
        ImageView image;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.animated_splash_screen);

            topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
            bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

            image= findViewById(R.id.image);
            logo=findViewById(R.id.logo);
            slogan=findViewById(R.id.slogan);

            image.setAnimation(topAnim);
            logo.setAnimation(bottomAnim);
            slogan.setAnimation(bottomAnim);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 6000);
        }


}


