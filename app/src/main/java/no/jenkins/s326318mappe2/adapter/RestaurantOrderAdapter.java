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
import no.jenkins.s326318mappe2.classes.RestaurantOrder;

public class RestaurantOrderAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<RestaurantOrder> items;

    public RestaurantOrderAdapter(Context context, ArrayList<RestaurantOrder> list){
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
            view = LayoutInflater.from(context).inflate(R.layout.adapter_order,viewGroup,false);
        RestaurantOrder resOrder = items.get(i);
        ((TextView) view.findViewById(R.id.adapter_o_date)).setText(resOrder.getDate());
        ((TextView) view.findViewById(R.id.adapter_o_time)).setText(resOrder.getTime());
        ((TextView) view.findViewById(R.id.adapter_o_restaurant)).setText(" " +resOrder.getRestaurant_id());
        return view;
    }
}
