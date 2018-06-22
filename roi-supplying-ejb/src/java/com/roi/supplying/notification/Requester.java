package com.roi.supplying.notification;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.lang.reflect.Type;

public class Requester {
    
    private static Gson gson = new Gson();
   
    public static String sendRequest(String url, String method) {
        return Requester.sendRequest(url, method, null, null);
    }
    
    public static String sendRequest(String url, String method, Object content, Type contentType) {
        BufferedReader reader = null;
        try {
            URL finalUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) finalUrl.openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod(method);
            connection.setDoInput(true);
            if (content != null && contentType != null) {
                String jsonNotification = gson.toJson(content, contentType);
                writeOutput(connection, jsonNotification);
            }
            connection.connect();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String valor = reader.readLine();
            return valor;
        } catch (IOException ex) {

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {

                    return null;
                }
            }
        }
        return null;
    }

    private static void writeOutput(HttpURLConnection connection, String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        writer.write(content);
        writer.close();
    }
}
