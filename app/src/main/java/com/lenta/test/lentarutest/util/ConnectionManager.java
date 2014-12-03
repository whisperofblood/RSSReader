package com.lenta.test.lentarutest.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionManager {

    private static String noConnectionString;

    public static boolean isOnline(Context context, boolean strongNeed) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (strongNeed) {
            return netInfo != null && netInfo.isConnected();
        } else {
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }
    }

    public static String getNoConnectionString() {
        return noConnectionString;
    }

    public static void setNoConnectionString(String noConnectionString) {
        ConnectionManager.noConnectionString = noConnectionString;
    }
}
