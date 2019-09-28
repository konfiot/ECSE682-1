package com.example.ecse682_1.ui.login;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.example.ecse682_1.R;

import android.widget.TextView;

import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


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
        super.onStop();
        TextView text = findViewById(R.id.events);

        text.setText("Stopped");

    }


    @Override
    public void onRestart(){
        super.onRestart();
        TextView text = findViewById(R.id.events);

        text.setText("Restarted");

    }


    @Override
    public void onStart(){
        super.onStart();
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

    public JSONObject readLogFile() {
        String contents = "";
        try{
            File file = new File(getCacheDir(), "logs.json");
            FileInputStream fis = new FileInputStream(file);

        byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            contents = new String(data, "UTF-8");;

        } catch(FileNotFoundException ex) {}
        catch(IOException ex) {}


        try {
            JSONObject json = new JSONObject(contents);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean writeLogFile(JSONObject json) {
        try{

            File file = new File(getCacheDir(), "logs.json");
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            String contents = json.toString();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            bufferedWriter.write(contents);

            bufferedWriter.flush();
            bufferedWriter.close();
            outputStreamWriter.close();

        } catch(FileNotFoundException ex) {}
        catch(IOException ex) {}
        return true;
    }
}