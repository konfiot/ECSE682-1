package com.example.ecse682_1.ui.login;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.example.ecse682_1.R;

import android.widget.TextView;


public class LogView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    public void onResume(){
        super.onResume();
        TextView text = findViewById(R.id.events);

        text.setText("Resumed");

    }


    @Override
    public void onStop(){
        super.onResume();
        TextView text = findViewById(R.id.events);

        text.setText("Stopped");

    }


    @Override
    public void onRestart(){
        super.onResume();
        TextView text = findViewById(R.id.events);

        text.setText("Restarted");

    }


    @Override
    public void onStart(){
        super.onResume();
        TextView text = findViewById(R.id.events);

        text.setText("Started");

    }


    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onPause(){
        super.onPause();
        TextView text = findViewById(R.id.events);

        text.setText("Paused");

    }
}