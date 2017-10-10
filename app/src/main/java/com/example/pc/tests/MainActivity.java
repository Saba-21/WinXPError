package com.example.pc.tests;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private ImageView clone;
    private ImageView xpError;
    private RelativeLayout myLayout;
    private RelativeLayout.LayoutParams layoutParams;
    private DisplayMetrics displayMetrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int X = displayMetrics.widthPixels;

        xpError.setOnTouchListener(new View.OnTouchListener() {
            float dx, dy, frameX, frameLeftX;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        frameX = event.getX();
                        frameLeftX = v.getWidth() - frameX;
                        dx = event.getRawX() - v.getX();
                        dy = event.getRawY() - v.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if((event.getRawX() + frameLeftX) < X && (event.getRawX() - frameX) > 0){
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
                        break;
                }
                return true;
            }
        });
    }
    private void initView() {
        myLayout = (RelativeLayout) findViewById(R.id.main);
        xpError = (ImageView) findViewById(R.id.xpError);
        displayMetrics = new DisplayMetrics();
    }
}
