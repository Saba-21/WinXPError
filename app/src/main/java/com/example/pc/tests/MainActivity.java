package com.example.pc.tests;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView clone;
    private ImageView xpError;
    private RelativeLayout myLayout;
    private RelativeLayout.LayoutParams layoutParams;
    private DisplayMetrics displayMetrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initView();

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int X = displayMetrics.widthPixels;

        xpError.setOnTouchListener(new View.OnTouchListener() {
            float dx, dy, frameX, frameLeftX;
            int i = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        frameX = event.getX();
                        frameLeftX = v.getWidth() - frameX;
                        dx = event.getRawX() - v.getX();
                        dy = event.getRawY() - v.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if ((event.getRawX() + frameLeftX) < X && (event.getRawX() - frameX) > 0) {
                            v.animate()
                                    .x(event.getRawX() - dx)
                                    .y(event.getRawY() - dy)
                                    .setDuration(0)
                                    .start();
                        }
                        clone = (ImageView) LayoutInflater.from(MainActivity.this).inflate(R.layout.text, null);
                        layoutParams = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(Math.round((event.getRawX() - dx)), Math.round((event.getRawY() - dy)), 0, 0);
                        clone.setLayoutParams(layoutParams);
                        myLayout.addView(clone);
                        i++;
                        if(i==120) {
                            MediaPlayer twoPlayer = MediaPlayer.create(MainActivity.this, R.raw.two);
                            twoPlayer.start();
                            setContentView(R.layout.bdos);
                        }
                        break;
                }
                return true;
            }
        });
    }

    public void myComp(View view){
        MediaPlayer onePlayer = MediaPlayer.create(MainActivity.this, R.raw.one);
        onePlayer.start();
        xpError.setVisibility(View.VISIBLE);
    }

    private void initView() {
        myLayout = (RelativeLayout) findViewById(R.id.main);
        xpError = (ImageView) findViewById(R.id.xpError);
        displayMetrics = new DisplayMetrics();
    }
}
