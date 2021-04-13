package com.example.nationalinformation;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class customListAdapter extends BaseAdapter {
    private List<CountryEntity> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public customListAdapter(Context aContext, List<CountryEntity> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView =  layoutInflater.inflate(R.layout.countries, null);
            holder = new ViewHolder();
            holder.ImageView = convertView.findViewById(R.id.imageCountry);
            holder.InfoView = convertView.findViewById(R.id.coutryName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CountryEntity country = this.listData.get(position);
        holder.InfoView.setText(country.getName());


        String imageUri = "https://flagpedia.net/data/flags/normal/" + country.getCountryCode().toLowerCase() + ".png";
        //ImageView ivBasicImage = findViewById(R.id.imageCountry);
        Picasso.get().load(imageUri).into((ImageView) convertView.findViewById(R.id.imageCountry));
        //int imageId = this.getMipmapResIdByName(country.getImg());

        //holder.ImageView.setImageResource(imageId);
        return convertView;
    }

    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();
        int resID = context.getResources().getIdentifier(resName , "drawable", pkgName);
        Log.i("CustomListView", "Res Name: "+ resName+"==> Res ID = "+ resID);
        return resID;
    }

    static class ViewHolder {
        ImageView ImageView;
        TextView InfoView;
    }
}
