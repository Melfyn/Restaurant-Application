package no.jenkins.s326318mappe2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import no.jenkins.s326318mappe2.R;
import no.jenkins.s326318mappe2.classes.Restaurant;

public class RestaurantAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<Restaurant> items;

    public RestaurantAdapter(Context context, ArrayList<Restaurant> list){
        this.context = context;
        this.items = list;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.adapter_restaurant,viewGroup,false);
        Restaurant res = items.get(i);
        ((TextView) view.findViewById(R.id.adapter_r_name)).setText(res.getName());
        ((TextView) view.findViewById(R.id.adapter_r_adress)).setText(res.getAdress());
        ((TextView) view.findViewById(R.id.adapter_r_phone)).setText(res.getPhoneNumber());
        ((TextView) view.findViewById(R.id.adapter_r_type)).setText(res.getType());
        return view;
    }
}
