package com.example.kb_jay.danmudemo;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.ArrayBlockingQueue;

public class MainActivity extends AppCompatActivity {

    private KJDmView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mView = (KJDmView) this.findViewById(R.id.view);

        ArrayBlockingQueue<View> views = new ArrayBlockingQueue<View>(20);
        for (int i = 0; i < 20; i++) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item, null);
            try {
                views.put(view);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mView.addCustomView(views);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
