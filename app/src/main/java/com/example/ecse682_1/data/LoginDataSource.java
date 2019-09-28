package com.example.ecse682_1.data;

import android.util.JsonReader;
import android.util.JsonWriter;

import com.example.ecse682_1.data.model.LoggedInUser;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private static File storage = new File( "storage.json" );

    private static JSONObject data;

    private static HashMap<String,String> userMap = new HashMap<>();

    private static final boolean fileEnabled = false;

    LoginDataSource() {

        if(fileEnabled) {
            try {
                if (!storage.exists()) {
                    System.out.println("Storage file does not exists, creating new one");

                    data = new JSONObject();
                    data.accumulate("loggingInfo", new JSONObject());
                    data.accumulate("state", new Object());
                    data.accumulate("lifeCycle", new String());

                } else {
                    System.out.println("Retrieving old data");
                    InputStream in = new FileInputStream(storage);
                    JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));

                    reader.beginObject();
                    while (reader.hasNext()) {
                        String name = reader.nextName();

                        if (name.equals("loggingInfo")) {
                            reader.beginObject();
                            while (reader.hasNext()) {
                                String userName = reader.nextName();
                                String password = reader.nextString();
                                userMap.put(userName, password);
                            }
                            reader.endObject();
                        }
                    }
                    reader.endObject();
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if(!userMap.isEmpty()) {
                userMap.clear();
            }
            userMap.put("user@domain.com","password");
        }
    }

    public Result<LoggedInUser> login(String username, String password) {

        System.out.println("Logging in with "+username +" and "+password);

        try {
            // TODO: handle loggedInUser authenticatio
            if(!userMap.containsKey(username)) {
                return new Result.Error(new Exception("Username does not exists"));
            }
            if( !userMap.get(username).equals(password) ) {
                return new Result.Error(new Exception("Invalid password passed"));
            }

            LoggedInUser activeUser =
                    new LoggedInUser(
                            ""+username.hashCode(),
                            username);
            System.out.println("Successfully connected");
            return new Result.Success<>(activeUser);
        } catch (Exception e) {
            System.out.println("Error occurred");
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void register(String username, String password) throws Exception {
        if(userMap.containsKey(username)) {
            throw new Exception("Username already exists");
        }
        userMap.put(username, password);
    }

    public void logout() {
        // TODO: revoke authentication
    }

    public static void safeState() {
            try {

                System.out.println("Saving state");
                OutputStream out = new FileOutputStream(storage);
                JsonWriter writter = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));

                writter.beginObject();
                writter.name("loggingInfo");
                writter.beginObject();
                for (String userName : userMap.keySet()) {
                    writter.name(userName).value(userMap.get(userName));
                }
                writter.endObject();
                writter.endObject();

                writter.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

    }
}
