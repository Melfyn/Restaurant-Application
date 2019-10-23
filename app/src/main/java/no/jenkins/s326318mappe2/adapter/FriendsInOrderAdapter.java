package no.jenkins.s326318mappe2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import no.jenkins.s326318mappe2.R;
import no.jenkins.s326318mappe2.classes.Friend;

public class FriendsInOrderAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Friend> friends;

    public FriendsInOrderAdapter(Context context, ArrayList<Friend> list){
        this.context = context;
        this.friends = list;
    }


    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public Object getItem(int i) {
        return friends.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.adapter_friends_in_order,viewGroup,false);
        Friend friend = friends.get(i);
        ((TextView) view.findViewById(R.id.adapter_f_o_name)).setText(friend.getName());
        ((TextView) view.findViewById(R.id.adapter_f_o_phone)).setText(friend.getPhoneNumber());
        return view;
    }
}
