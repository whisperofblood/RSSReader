package com.lenta.test.lentarutest.model;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class NewsInstances extends XMLInstance {

    static final String KEY_ITEM = "item";
    static final String KEY_TITLE = "title";
    static final String KEY_CATEGORY = "category";
    static final String KEY_IMG = "enclosure";
    static final String KEY_DESC = "description";

    private ArrayList<NewsInstance> newsInstanceSet;
    private NewsInstance newsInstance;

    @Override
    public void parseData(String xmlResponse) {
        NewsInstances.super.parseData(xmlResponse);
        try {
            xmlParse(response);
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

    public void clearListData(){
        if (newsInstanceSet != null){
            newsInstanceSet.clear();
        }
    }

    public NewsInstance getNewsInst(int position) {
        return newsInstanceSet.get(position);
    }

    public ArrayList<NewsInstance> getNewsInstanceSet() {
        return newsInstanceSet;
    }

    public void xmlParse(String file) throws XmlPullParserException, IOException {
        newsInstanceSet = new ArrayList<>();

        XmlPullParser xpp = Xml.newPullParser();
        xpp.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        xpp.setInput(new StringReader(file));

        String text = null;
        boolean itemBol = false;
        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                String name = xpp.getName();
                switch (xpp.getEventType()) {
                    case XmlPullParser.START_TAG:

                        if (name.equals(KEY_ITEM)) {
                            newsInstance = new NewsInstance();
                            itemBol = true;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = xpp.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if(itemBol) {
                            switch (name) {
                                case KEY_TITLE:
                                    newsInstance.setTitle(text);
                                    break;
                                case KEY_CATEGORY:
                                    newsInstance.setCategory(text);
                                    break;
                                case KEY_IMG:
                                    newsInstance.setImg_url(xpp.getAttributeValue(null, "url"));
                                    break;
                                case KEY_DESC:
                                    newsInstance.setDescription(text);
                                    break;
                            }

                            if (xpp.getName().equals(KEY_ITEM)) {
                                newsInstanceSet.add(newsInstance);
                                itemBol = false;
                            }
                        }
                        break;
                }
                xpp.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }
}
