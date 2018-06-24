package com.roi.http;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

@Stateless
public class RequesterBean implements RequesterBeanLocal {

    private static Gson gson;

    @PostConstruct
    private void init() {
        gson = new Gson();
    }

    public Object sendRequest(Request request) {
        BufferedReader reader = null;
        try {
            URL finalUrl = new URL(request.url);
            HttpURLConnection connection = (HttpURLConnection) finalUrl.openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod(request.method);
            connection.setDoInput(true);
            if (request.content != null && request.contentType != null) {
                String jsonNotification = gson.toJson(request.content, request.contentType);
                writeOutput(connection, jsonNotification);
            }
            if (request.token != null) {
                String bearer = "Bearer " + request.token;
                connection.addRequestProperty("Authentication", bearer);
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
