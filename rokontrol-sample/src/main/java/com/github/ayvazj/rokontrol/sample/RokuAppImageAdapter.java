package com.github.ayvazj.rokontrol.sample;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.github.ayvazj.rokontrol.RokuAppInfo;
import com.github.ayvazj.rokontrol.RokuAppType;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RokuAppImageAdapter extends BaseAdapter {
    private final Context context;
    private final List<RokuAppInfo> apps;

    public RokuAppImageAdapter(Context context, List<RokuAppInfo> apps) {
        this.context = context;

        this.apps = new ArrayList<RokuAppInfo>();
        for(RokuAppInfo appInfo : apps) {
            if (RokuAppType.APPL.equals(appInfo.type)) {
                this.apps.add(appInfo);
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView view = (ImageView) convertView;
        if (view == null) {
            view = new ImageView(context);
            //view.setLayoutParams(new GridLayout.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT));
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }


        RokuAppInfo app = getItem(position);

        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(context) //
                .load(app.appImageUrl) //
                .placeholder(R.drawable.placeholder) //
                .error(R.drawable.error) //
                .fit() //
                .tag(context) //
                .into(view);

        return view;
    }

    @Override
    public int getCount() {
        return this.apps.size();
    }

    @Override
    public RokuAppInfo getItem(int position) {
        return this.apps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
