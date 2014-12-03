package com.lenta.test.lentarutest.ui;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lenta.test.lentarutest.R;
import com.lenta.test.lentarutest.model.NewsInstance;

import java.util.ArrayList;

public class ListItemAdapter extends BaseAdapter {

    ArrayList<NewsInstance> myList = new ArrayList<>();
    LayoutInflater inflater;
    private Activity activity;


    public ListItemAdapter(Activity a, ArrayList<NewsInstance> myList) {
        this.myList = myList;
        activity = a;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public NewsInstance getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MyViewHolder mViewHolder;
        if ( convertView == null || convertView.getTag() == null ){
            convertView = inflater.inflate(R.layout.layout_list_element, null);
            mViewHolder = new MyViewHolder();
            mViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.textViewList);
            mViewHolder.categoryTV = (TextView) convertView.findViewById(R.id.category_text);
           convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        mViewHolder.tvTitle.setText( myList.get(position).getTitle() );
        mViewHolder.categoryTV.setText( myList.get(position).getCategory() );

        return convertView;
    }

    private class MyViewHolder {
        TextView tvTitle;
        TextView categoryTV;
    }
}
