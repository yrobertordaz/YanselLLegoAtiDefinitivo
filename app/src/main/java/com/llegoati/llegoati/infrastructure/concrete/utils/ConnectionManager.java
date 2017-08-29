package com.llegoati.llegoati.infrastructure.concrete.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.llegoati.llegoati.infrastructure.concrete.utils.webservice._FakeX509TrustManager;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Yansel on 2/18/2017.
 */

public class ConnectionManager {
    private static ConnectivityManager connectivity_manager;
    private static HttpURLConnection http_url_connection;
    private static NetworkInfo network_info;

    private static boolean isConnectedToSomeWhere() {
        return connectivity_manager.getActiveNetworkInfo().isConnected();
    }

    public static boolean checkAccess(Context context, String url) {
        try {
            if (url.toLowerCase().startsWith("https")) _FakeX509TrustManager.allowAllSSL();

            connectivity_manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (!isConnectedToSomeWhere()) return false;

            network_info = connectivity_manager.getActiveNetworkInfo();

            if (network_info != null && network_info.isConnected()) {
                http_url_connection = (HttpURLConnection) (new URL(url).openConnection());
                http_url_connection.setRequestProperty("User-Agent", "Android Application");
                http_url_connection.setRequestProperty("Connection", "close");
                http_url_connection.setConnectTimeout(5 * 1000);
                http_url_connection.connect();
                int response_code = http_url_connection.getResponseCode();
                if (response_code == HttpURLConnection.HTTP_OK) return true;
            }
        } catch (Exception e) {
            e.getMessage();
        }

        return false;
    }


}
