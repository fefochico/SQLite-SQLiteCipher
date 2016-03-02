package com.notes.gabriel.notes;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by fefochico on 22/02/2016.
 */
public class CustomAdapter extends BaseAdapter {
    private ArrayList<ObjectNote> list;
    private static LayoutInflater inflater= null;
    private Context context;
    private Cursor cursor;

    public CustomAdapter(Context c){
        context=c;
        list= new ArrayList<ObjectNote>();
        inflater = LayoutInflater.from(context);
    }

    public void add(ObjectNote note){
        list.add(note);
    }

    public void addNew(ObjectNote note){
        list.add(note);
        notifyDataSetChanged();
    }

    public void delete(int position){
        list.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View itemLayout;
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        itemLayout = inflater.inflate(R.layout.element_list, parent, false);

        TextView tvDate= (TextView) itemLayout.findViewById(R.id.tvDate);
        tvDate.setText(list.get(position).date);
        TextView tvName= (TextView) itemLayout.findViewById(R.id.tvName);
        tvName.setText(list.get(position).title);

        return itemLayout;
    }
}
