package com.example.ecse682_1.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import com.example.ecse682_1.R;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class LogView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        appendToFile("Created");
    }


    @Override
    public void onResume(){
        super.onResume();
        appendToFile("Resumed");
        TextView text = findViewById(R.id.events);
        try {
            text.setText(readLogFile().toString(4));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onStop(){
        super.onStop();
        appendToFile("Stopped");

    }


    @Override
    public void onRestart(){
        super.onRestart();
        appendToFile("Restarted");

    }


    @Override
    public void onStart(){
        super.onStart();
        appendToFile("Started");

    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        appendToFile("Destroyed");
    }

    @Override
    public void onPause(){
        super.onPause();
        appendToFile("Paused");
        TextView text = findViewById(R.id.events);
        Log.w("RESUMED", "Resumed AF");
        text.setText("Pause");


    }

    public JSONObject readLogFile() {
        String contents = "";
        try{
            File file = new File(getCacheDir(),"storage.json");
            FileInputStream fis = new FileInputStream(file);

        byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            contents = new String(data, "UTF-8");;

        } catch(FileNotFoundException ex) {}
        catch(IOException ex) {}

        Log.d("Contents", contents);

        try {
            JSONObject json = new JSONObject(contents);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            return new JSONObject().put("Error", "Error reading logfile");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    public boolean writeLogFile(JSONObject json) {
        try{

            File file = new File(getCacheDir(), "storage.json");
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

    public boolean appendToFile(String event) {
        JSONObject json = readLogFile();
        try {
            JSONArray array = json.getJSONArray("events");
            array.put(event);
            json.put("events", array);
        } catch (JSONException e) {
            try {
                json.put("events", new JSONArray().put(event));
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }

        writeLogFile(json);
        return true;
    }

    public void logout(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}