package no.jenkins.s326318mappe2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

    public ArrayList<Friend> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<Friend> friends) {
        this.friends = friends;
    }

    public FriendsInOrderAdapter(ArrayList<Friend> friends) {
        this.friends = friends;
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
        CheckBox check = view.findViewById(R.id.friend_checkbox);
        //action triggered when checkboxes in listview is checked / unchecked
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                friend.setAttending(b);
                Log.d("logvenner",logFriends());
            }
        });

        ((TextView) view.findViewById(R.id.adapter_f_o_name)).setText(friend.getName());
        ((TextView) view.findViewById(R.id.adapter_f_o_phone)).setText(friend.getPhoneNumber());
        return view;
    }

    public String logFriends(){
        String array = "";

        for(Friend friend : friends){
            array+=" "+friend.get_ID();
            array+=" "+friend.getName();
            array+=" "+friend.getPhoneNumber();
            array+=" "+friend.getAttending();
        }

        return array;
    }
}
