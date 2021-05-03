package com.company;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    private static HttpURLConnection connection;

    public static void main(String[] args) {
        Gson gson = new Gson();



        BufferedReader reader;
        String line;
        StringBuffer responseConnect = new StringBuffer();

        try {
            URL url = new URL("https://jsonplaceholder.typicode.com/posts");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int status = connection.getResponseCode();
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseConnect.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseConnect.append(line);
                }
                reader.close();
            }
            System.out.println(responseConnect.toString());
            //parse(responseConnect.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
    }

    public static String parse(String responseBody) {
        JSONArray albums = new JSONArray(responseBody);
        for (int i = 0; i < albums.length(); i++) {
            JSONObject album = albums.getJSONObject(i);
            int userId = album.getInt("userId");
            int id = album.getInt("id");
            String title = album.getString("title");
            String body = album.getString("body");
            System.out.println("User id = " + userId + "\nid = " + id + "\nTitle: " + title + "\nBody: " + body + "\n");
        }
        return null;
    }
}
