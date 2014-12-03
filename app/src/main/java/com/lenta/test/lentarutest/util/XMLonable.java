package com.lenta.test.lentarutest.util;

import android.content.Context;


public interface XMLonable {

    public void parseData(String xmlResponse);

    public void sendDataByGet(Context context) throws NoInternetConnectionException;

    public void dataLoaded(String errorString);
}
