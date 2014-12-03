package com.lenta.test.lentarutest.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public abstract class NetManager implements XMLonable {

    private final static String RSS_URL = "http://lenta.ru/rss/";
    private ServerAnswerListener listener;
    private String result;

    @Override
    public void sendDataByGet(Context context) throws NoInternetConnectionException {
        new LoadTask().execute(RSS_URL);
        if (ConnectionManager.isOnline(context, false)) {
            new LoadTask().execute();
        } else {
            throw new NoInternetConnectionException(ConnectionManager.getNoConnectionString());
        }
    }

    private class LoadTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... Url) {
            try {
                URL url = new URL(RSS_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(10*1000);
                connection.setConnectTimeout(10*1000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                int response = connection.getResponseCode();
                Log.d("debug", "The response is: " + response);
                InputStream is = connection.getInputStream();
                result = convertStreamToString(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            parseData(result);
            dataLoaded(result);
        }
    }

    public void setServerListener(ServerAnswerListener listener) {
        this.listener = listener;
    }

    @Override
    public void dataLoaded(String errorString) {
        listener.onLoaded(this,  true);
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
