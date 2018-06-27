package com.roi.http;

import com.google.gson.Gson;
import com.roi.logger.LoggerBean;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.EJB;

@Stateless
public class RequesterBean implements RequesterBeanLocal {

    private Gson gson;

    @EJB
    LoggerBean loggerBean;

    @PostConstruct
    private void init() {
        gson = new Gson();
    }

    @Override
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
                connection.addRequestProperty("Authentication", request.token);
            }
            connection.connect();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String valor = reader.readLine();
            return gson.fromJson(valor, request.responseType);
        } catch (IOException ex) {
            loggerBean.logFatalErrorFromMessageClass("Failed to read connection stream.",
                    RequesterBean.class.toString(), ex);
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
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), ("UTF-8")))) {
            writer.write(content);
        }
    }

    @Override
    public Object sendPureJson(Request request) {
        BufferedReader reader = null;
        try {
            URL finalUrl = new URL(request.url);
            HttpURLConnection connection = (HttpURLConnection) finalUrl.openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod(request.method);
            connection.setDoInput(true);
            if (request.content != null) {
                writeOutput(connection, request.content.toString());
            }
            if (request.token != null) {
                connection.addRequestProperty("Authentication", request.token);
            }
            connection.connect();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String valor = reader.readLine();
            return gson.fromJson(valor, request.responseType);
        } catch (IOException ex) {
            loggerBean.logFatalErrorFromMessageClass("Failed to read connection stream.",
                    RequesterBean.class.toString(), ex);
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
}
