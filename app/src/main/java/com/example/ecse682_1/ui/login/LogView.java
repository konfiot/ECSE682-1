package com.example.ecse682_1.ui.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ecse682_1.R;
import com.example.ecse682_1.data.LoginRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class LogView extends AppCompatActivity {

    private static JSONObject status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appendToFile("Created");
    }

    private void updateField(JSONObject status, String state) {
        try {
            JSONObject events = status.getJSONObject("events");
            events.has(state);
        } catch(JSONException ex) {
            ex.printStackTrace();
        }
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

        } catch(FileNotFoundException ex) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT);
        }
        catch(IOException ex) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT);
        }

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

        } catch(FileNotFoundException ex) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT);
        }
        catch(IOException ex) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT);
        }
        return true;
    }

    public boolean appendToFile(String event) {
        JSONObject json = readLogFile();
        try{
            JSONObject events = json.getJSONObject("events");
            int value = 1;
            if(events.has(event)) {
                value += events.getInt(event);
            }
            events.put(event, value);
        } catch (JSONException e) {
            try {
                json.put( "events", new JSONObject().put(event, 1));
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }

        writeLogFile(json);
        return true;
    }

    public void logout(View view) {
        LoginRepository.logout();
        finish();
    }
}