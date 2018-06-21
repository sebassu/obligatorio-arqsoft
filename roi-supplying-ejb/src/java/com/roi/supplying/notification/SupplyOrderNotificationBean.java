package com.roi.supplying.notification;

import com.google.gson.Gson;
import com.roi.supplying.SupplyOrder;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.annotation.PostConstruct;

@Stateless
@LocalBean
public class SupplyOrderNotificationBean {

    private static final String KREMLIN_URL = "Kremlin URL";

    private Gson gson;

    @PostConstruct
    public void init() {
        this.gson = new Gson();
    }

    private SupplyOrderNotification createNotification(SupplyOrder supplyOrder) {
        long orderNumber = supplyOrder.getOrderNumber();
        long servicePointId = supplyOrder.getServicePointId();

        return new SupplyOrderNotification(orderNumber, servicePointId);
    }

    public void notifyCreation(SupplyOrder supplyOrder) {
        SupplyOrderNotification notification = createNotification(supplyOrder);
        String jsonNotification = gson.toJson(notification, SupplyOrderNotification.class);
        String url = KREMLIN_URL;
        String s = sendRequest(url, "POST", jsonNotification);
    }

    public void notifyModification(SupplyOrder supplyOrder) {
        SupplyOrderNotification notification = createNotification(supplyOrder);
        String jsonNotification = gson.toJson(notification, SupplyOrderNotification.class);
        String url = KREMLIN_URL + "/" + supplyOrder.getOrderNumber();
        String s = sendRequest(url, "PUT", jsonNotification);
    }

    public void notifyRemoval(SupplyOrder supplyOrder) {
        SupplyOrderNotification notification = createNotification(supplyOrder);
        String url = KREMLIN_URL + "/" + supplyOrder.getOrderNumber();
        sendRequest(url, "DELETE", null);
    }

    public static String sendRequest(String url, String method, String content) {
        BufferedReader reader = null;
        try {
            URL finalUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) finalUrl.openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod(method);
            connection.setDoInput(true);
            if (content != null) {
                writeOutput(connection, content);
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
