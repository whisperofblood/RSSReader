package com.lenta.test.lentarutest.util;

public class NoInternetConnectionException extends Exception {

    public NoInternetConnectionException(String message) {
        super(message);
    }

    public NoInternetConnectionException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
