package com.matcha.m18011003;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Student on 2018/1/10.
 */

public class MyHandler extends DefaultHandler {

    boolean isTitle=false;
    boolean isItem=false;
    boolean isLink=false;
    StringBuilder sb=new StringBuilder();
    public ArrayList<String> title = new ArrayList<>();
    public ArrayList<String> link = new ArrayList<>();
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        //Log.d("NET", qName);
        if (qName.equals("title"))
        {
            isTitle = true;
        }
        if (qName.equals("item"))
        {
            isItem = true;
        }
        if(qName.equals("link"))
        {
            isLink=true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (qName.equals("title"))
        {
            isTitle = false;
        }
        if (qName.equals("item"))
        {
            isItem = false;
        }
        if(qName.equals("link"))
        {
            if(isItem){
                link.add(sb.toString());
                sb=new StringBuilder();
            }
            isLink=false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if (isItem)
        {
            if(isTitle)
            {
                Log.d("title", new String(ch, start, length));
                title.add(new String(ch, start, length));
;            }
            if(isLink)
            {
                Log.d("link", new String(ch, start, length));
                sb.append(new String(ch, start, length));
            }
        }

    }

}
