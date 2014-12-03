package com.lenta.test.lentarutest.util;

public interface ServerAnswerListener {

	public void onError(String value);

	public void onLoaded(NetManager caller, boolean ok);
}
