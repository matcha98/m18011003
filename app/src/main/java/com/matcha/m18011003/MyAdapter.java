package com.matcha.m18011003;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Student on 2018/1/11.
 */

public class MyAdapter extends BaseAdapter {
    Context context;
    ArrayList<Mobile01NewsItem> newsItems;

    public MyAdapter(Context context,ArrayList<Mobile01NewsItem> newsItems)
    {
        this.context=context;
        this.newsItems= newsItems;
    }
    @Override
    public int getCount() {
        return newsItems.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

            LayoutInflater inflater=LayoutInflater.from(context);
            view=inflater.inflate(R.layout.myitem,null);
            viewHolder=new ViewHolder();
            viewHolder.tv1=view.findViewById(R.id.textView);
            viewHolder.tv2=view.findViewById(R.id.textView2);
            viewHolder.img=view.findViewById(R.id.imageView);
            view.setTag(viewHolder);

        viewHolder.tv1.setText(newsItems.get(i).title);
        viewHolder.tv2.setText(newsItems.get(i).description);

        return view;
    }

    static class ViewHolder
    {
        TextView tv1;
        TextView tv2;
        ImageView img;
    }
}