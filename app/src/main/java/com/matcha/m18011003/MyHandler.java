package com.matcha.m18011003;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by Student on 2018/1/10.
 */

public class MyHandler extends DefaultHandler {

    boolean isTitle=false;
    boolean isItem=false;
    ArrayList<String> list=new ArrayList<>();
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if(qName.equals("item")){
            isItem=true;
        }
        if(isItem && qName.equals("title")){
            isTitle=true;
            isItem=false;
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if(qName.equals("title"))
        {
            isTitle=false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if(isTitle)
        {
            Log.d("Title",new String(ch,start,length));
            list.add(new String(ch,start,length));
        }
    }

    public ArrayList<String> getData()
    {
        return list;
    }
}
