package no.jenkins.s326318mappe2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import no.jenkins.s326318mappe2.R;
import no.jenkins.s326318mappe2.classes.Friend;

public class FriendAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Friend> items;

    public FriendAdapter(Context context, ArrayList<Friend> list){
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
            view = LayoutInflater.from(context).inflate(R.layout.adapter_friend,viewGroup,false);
        Friend friend = items.get(i);
        ((TextView) view.findViewById(R.id.adapter_f_name)).setText(friend.getName());
        ((TextView) view.findViewById(R.id.adapter_f_phone)).setText(friend.getPhoneNumber());
        return view;
    }
}
