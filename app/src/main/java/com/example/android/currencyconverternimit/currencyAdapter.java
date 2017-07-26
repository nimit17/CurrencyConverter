package com.example.android.currencyconverternimit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nimit Arora on 11-07-2017.
 */


public class currencyAdapter extends ArrayAdapter<currency> {

    currencyAdapter(Context context, ArrayList<currency> currencyArrayListray)
    {
        super(context,0,currencyArrayListray);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        currency newCurrency=getItem(position);
        View listItemView=convertView;
        if(listItemView==null)
        {  listItemView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_layout1, parent,false);
        }


        TextView country=(TextView) listItemView.findViewById(R.id.country);
        country.setText(newCurrency.getCountry());

        TextView  currencyName=(TextView) listItemView.findViewById(R.id.code);
        currencyName.setText(newCurrency.getCurrencyName());
        ImageView flags=(ImageView) listItemView.findViewById(R.id.flag);
        flags.setImageResource(newCurrency.getImageID());
        return listItemView;

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        currency newCurrency=getItem(position);
        View listItemView=convertView;
        if(listItemView==null)
        {  listItemView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_layout1, parent,false);
        }
        LinearLayout  listViewLayout=(LinearLayout) listItemView.findViewById(R.id.layoutSpinner);
        int colorid= ContextCompat.getColor(getContext(),R.color.spinnerDropDownMenu);
        listItemView.setBackgroundColor(colorid);
        TextView country=(TextView) listItemView.findViewById(R.id.country);
        country.setText(newCurrency.getCountry());
        TextView  currencyName=(TextView) listItemView.findViewById(R.id.code);
        currencyName.setText(newCurrency.getCurrencyName());
        ImageView flags=(ImageView) listItemView.findViewById(R.id.flag);
        flags.setImageResource(newCurrency.getImageID());
        return listItemView;
    }
}
