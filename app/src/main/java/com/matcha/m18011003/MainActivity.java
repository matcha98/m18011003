package com.matcha.m18011003;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    MyHandler dataHandler;
    MyAdapter adapter;
    ArrayList<Mobile01NewsItem> newsItems=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv=findViewById(R.id.listView);

        //按item,由連結WebView找取網頁
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it=new Intent(MainActivity.this,Main2Activity.class);
                it.putExtra("link",dataHandler.newsItems.get(i).link);
                startActivity(it);
            }
        });

    }

    //由menu main_me
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                String str_url ="https://www.mobile01.com/rss/news.xml";
                URL url=null;
                try {
                    url=new URL(str_url);
                    HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();
                    InputStream is=conn.getInputStream();
                    InputStreamReader isr=new InputStreamReader(is);
                    BufferedReader br=new BufferedReader(isr);
                    StringBuilder sb=new StringBuilder();
                    String str;
                    while ((str = br.readLine()) != null)
                    {
                        sb.append(str);
                    }
                    String str1 = sb.toString();

                    dataHandler = new MyHandler(newsItems);
                    SAXParserFactory spf = SAXParserFactory.newInstance();
                    SAXParser sp = spf.newSAXParser();
                    XMLReader xr = sp.getXMLReader();
                    xr.setContentHandler(dataHandler);
                    xr.parse(new InputSource(new StringReader(str1)));

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            adapter=new MyAdapter(MainActivity.this,newsItems);
                            lv.setAdapter(adapter);
                            /*
                            String[] data=new String[dataHandler.newsItems.size()];
                            for(int i=0;i<data.length;i++)
                            {
                                data[i]=dataHandler.newsItems.get(i).title;
                            }
                           ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                                   MainActivity.this,android.R.layout.simple_list_item_1,data);
                           lv.setAdapter(adapter);
                           */


                        }
                    });
                    br.close();
                    isr.close();
                    is.close();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        return super.onOptionsItemSelected(item);
    }

}
