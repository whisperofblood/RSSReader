package com.lenta.test.lentarutest.model;


import com.lenta.test.lentarutest.util.NetManager;

public class XMLInstance extends NetManager {

    public String response;

    @Override
    public void parseData(String xmlResponse) {
        response = xmlResponse;
    }
}
