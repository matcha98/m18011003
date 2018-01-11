package com.matcha.m18011003;

import android.util.Log;
import android.util.Patterns;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Student on 2018/1/10.
 */

public class MyHandler extends DefaultHandler {

    boolean isTitle=false;
    boolean isItem=false;
    boolean isLink=false;
    boolean isDescription=false;
    StringBuilder sb=new StringBuilder();
    StringBuilder dessb=new StringBuilder();
    public ArrayList<Mobile01NewsItem> newsItems;
    Mobile01NewsItem item;

    public MyHandler(ArrayList<Mobile01NewsItem> newsItems)
    {
        this.newsItems=newsItems;
    }
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        //Log.d("NET", qName);
        switch(qName)
        {
            case "title":
                isTitle=true;
                break;
            case "item":
                item=new Mobile01NewsItem();
                isItem=true;
                break;
            case "link":
                isLink=true;
                break;
            case "description":
                isDescription=true;
                dessb=new StringBuilder();
                break;
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        switch (qName)
        {
            case "title":
                isTitle=false;
                break;
            case "item":
                isItem=false;
                newsItems.add(item);
                break;
            case "link":
                if(isItem)
                {
                    item.link=sb.toString();
                    sb=new StringBuilder();
                }
                isLink=false;
                break;
            case "description":
                isDescription=false;
                if(isItem)
                {
                    String str=dessb.toString();
                    Pattern pattern=Pattern.compile("http.*jpg");
                    Matcher matcher=pattern.matcher(str);
                    String imgurl="";
                    if(matcher.find())
                    {
                        imgurl=matcher.group(0);
                    }
                    str=str.replaceAll("<img.*/>","");
                    item.imgurl=imgurl;
                    item.description=str;
                }
                break;

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
                item.title=new String(ch, start, length);
;            }
            if(isLink)
            {
                Log.d("link", new String(ch, start, length));
                sb.append(new String(ch, start, length));
            }
            if(isDescription)
            {
                dessb.append(new String(ch,start,length));
            }
        }

    }

}
