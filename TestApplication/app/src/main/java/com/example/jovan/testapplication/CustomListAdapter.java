package com.example.jovan.testapplication;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<Blogs> {
    ArrayList<Blogs> blogs;
    Context context;
    int resource;


    public CustomListAdapter(Context context, int resource, ArrayList<Blogs> products) {
        super(context, resource, products);
        this.blogs = products;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.blogs_list, null, true);

        }
        Blogs blogs = getItem(position);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView1);
        Picasso.with(context).load(blogs.getImageUrl()).into(imageView);

        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1);
        titleTextView.setText(Html.fromHtml(blogs.getTitle()));

        TextView descriptionTextView = (TextView) convertView.findViewById(R.id.textView2);
        descriptionTextView.setText(Html.fromHtml(blogs.getDescription()));

        return convertView;

    }
}
