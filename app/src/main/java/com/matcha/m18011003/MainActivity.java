package com.matcha.m18011003;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click1(View v)
    {
       new Thread(){
           @Override
           public void run() {
               super.run();
               String str_url = "https://www.esunbank.com.tw/bank/personal/deposit/rate/forex/foreign-exchange-rates";
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
                   Log.d("NET", str1);
                   int index1 = str1.indexOf("人民幣(CNY)");
                   int index2 = str1.indexOf("現金買入匯率", index1);
                   int index3 = str1.indexOf(">", index2);
                   int index4 = str1.indexOf("<", index3);
                   Log.d("NET", "index1:" + index1 + "index2:" + index2 + "index3:" + index3);
                   String data1 = str1.substring(index3+1, index4);
                   Log.d("NET", data1);
                   br.close();
                   isr.close();
                   is.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }

           }
       }.start();
    }
}
