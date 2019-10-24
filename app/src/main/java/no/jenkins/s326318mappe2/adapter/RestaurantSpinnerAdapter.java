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

public class RestaurantSpinnerAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Restaurant> items;

    public RestaurantSpinnerAdapter(Context context, ArrayList<Restaurant> list){
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
            view = LayoutInflater.from(context).inflate(R.layout.adapter_restaurant_spinner,viewGroup,false);
        Restaurant res = items.get(i);
        ((TextView) view.findViewById(R.id.adapter_rs_name)).setText(" "+res.getName()+" ");

        return view;
    }
}
