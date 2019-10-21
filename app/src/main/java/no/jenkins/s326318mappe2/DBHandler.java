package no.jenkins.s326318mappe2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import no.jenkins.s326318mappe2.classes.Friend;
import no.jenkins.s326318mappe2.classes.FriendsInOrder;
import no.jenkins.s326318mappe2.classes.Restaurant;
import no.jenkins.s326318mappe2.classes.RestaurantOrder;

public class DBHandler extends SQLiteOpenHelper {
    // Friends table
    static String TABLE_FRIENDS = "Friends";
    static String FRIEND_ID = "_ID";
    static String FRIEND_NAME = "Name";
    static String FRIEND_PH_NO = "Phone";

    // Restaurant table
    static String TABLE_RESTAURANT = "Restaurants";
    static String RESTAURANT_ID = "_ID";
    static String RESTAURANT_NAME = "Name";
    static String RESTAURANT_ADRESS = "Adress";
    static String RESTAURANT_PH_NO = "Phone";
    static String RESTAURANT_TYPE = "Type";

    // Restaurant order table
    static String TABLE_RESTAURANT_ORDERS = "RestaurantOrders";
    static String ORDER_ID = "_ID";
    static String ORDER_DATE = "Date";
    static String ORDER_TIME = "Time";
    static String ORDER_RESTAURANT = "Restaurant";
    static String ORDER_FRIEND = "Friend";  // mulig denne kolonnen skal bort

    //  Friends in order table (Support table)
    static String TABLE_FRIENDS_IN_ORDER = "FriendsInOrder";
    static String F_ID = "FriendID";
    static String O_ID = "OrderID";


    static int DATABASE_VERSION = 3;
    static String DATABASE_NAME = "RestaurantAppDB";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_FRIENDS = "CREATE TABLE " + TABLE_FRIENDS + "(" + FRIEND_ID +
                " INTEGER PRIMARY KEY," + FRIEND_NAME + " TEXT," + FRIEND_PH_NO + " TEXT" + ")";
        Log.d("SQL", CREATE_TABLE_FRIENDS);
        db.execSQL(CREATE_TABLE_FRIENDS);

        String CREATE_TABLE_RESTAURANT = "CREATE TABLE " + TABLE_RESTAURANT + "(" + RESTAURANT_ID +
                " INTEGER PRIMARY KEY," + RESTAURANT_NAME + " TEXT," + RESTAURANT_ADRESS + " TEXT," +
                 RESTAURANT_PH_NO + " TEXT," + RESTAURANT_TYPE + " TEXT" + ")";
        Log.d("SQL", CREATE_TABLE_RESTAURANT);
        db.execSQL(CREATE_TABLE_RESTAURANT);

        String CREATE_TABLE_RESTAURANT_ORDERS = "CREATE TABLE " + TABLE_RESTAURANT_ORDERS + "(" + ORDER_ID +
                " INTEGER PRIMARY KEY," + ORDER_DATE + " TEXT," + ORDER_TIME + " TEXT," + ORDER_RESTAURANT +
                " INTEGER," + ORDER_FRIEND + " INTEGER," +
                "FOREIGN KEY ("+ORDER_RESTAURANT+") REFERENCES "+TABLE_RESTAURANT+"("+RESTAURANT_ID+")," +
                "FOREIGN KEY ("+ORDER_FRIEND+") REFERENCES "+TABLE_FRIENDS+"("+FRIEND_ID+") )";
        //  + " FOREIGN KEY ("+TASK_CAT+") REFERENCES "+CAT_TABLE+"("+CAT_ID+"));";
        Log.d("SQL", CREATE_TABLE_RESTAURANT_ORDERS);
        db.execSQL(CREATE_TABLE_RESTAURANT_ORDERS);

        String CREATE_TABLE_FRIENDS_IN_ORDER = "CREATE TABLE " + TABLE_FRIENDS_IN_ORDER + "(" + F_ID +
                " INTEGER," + O_ID + " INTEGER," +
                "FOREIGN KEY ("+F_ID+") REFERENCES "+TABLE_FRIENDS+"("+FRIEND_ID+")," +
                "FOREIGN KEY ("+O_ID+") REFERENCES "+TABLE_RESTAURANT_ORDERS+"("+ORDER_ID+") )";
        Log.d("SQL", CREATE_TABLE_FRIENDS);
        db.execSQL(CREATE_TABLE_FRIENDS_IN_ORDER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDS_IN_ORDER);
        onCreate(db);
    }

    // Restaurant methods
    public void addRestaurant(Restaurant restaurant) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RESTAURANT_NAME, restaurant.getName());
        values.put(RESTAURANT_ADRESS, restaurant.getAdress());
        values.put(RESTAURANT_PH_NO, restaurant.getPhoneNumber());
        values.put(RESTAURANT_TYPE, restaurant.getType());
        db.insert(TABLE_RESTAURANT, null, values);
        db.close();
    }

    public ArrayList<Restaurant> getRestaurants() {
        ArrayList<Restaurant> restaurantList = new ArrayList<Restaurant>();
        String selectQuery = "SELECT * FROM " + TABLE_RESTAURANT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Restaurant restaurant = new Restaurant();
                restaurant.set_ID(cursor.getLong(0));
                restaurant.setName(cursor.getString(1));
                restaurant.setAdress(cursor.getString(2));
                restaurant.setPhoneNumber(cursor.getString(3));
                restaurant.setType(cursor.getString(4));
                restaurantList.add(restaurant);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }

        return restaurantList;
    }

    public void deleteRestaurant(Long input_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RESTAURANT, RESTAURANT_ID + " =? ",
                new String[]{Long.toString(input_id)});
        db.close();
    }

    public int updateRestaurant(Restaurant restaurant) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RESTAURANT_NAME, restaurant.getName());
        values.put(RESTAURANT_ADRESS, restaurant.getAdress());
        values.put(RESTAURANT_PH_NO, restaurant.getPhoneNumber());
        values.put(RESTAURANT_TYPE, restaurant.getType());
        int update = db.update(TABLE_RESTAURANT, values, RESTAURANT_ID + "= ?",
                new String[]{String.valueOf(restaurant.get_ID())});
        db.close();
        return update;
    }

    // Friend methods
    public void addFriend(Friend friend) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FRIEND_NAME, friend.getName());
        values.put(FRIEND_PH_NO, friend.getPhoneNumber());
        db.insert(TABLE_FRIENDS, null, values);
        db.close();
    }

    public ArrayList<Friend> getFriends() {
        ArrayList<Friend> friendsList = new ArrayList<Friend>();
        String selectQuery = "SELECT * FROM " + TABLE_FRIENDS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Friend friend = new Friend();
                friend.set_ID(cursor.getLong(0));
                friend.setName(cursor.getString(1));
                friend.setPhoneNumber(cursor.getString(2));
                friendsList.add(friend);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }

        return friendsList;
    }

    public void deleteFriend(Long input_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FRIENDS, FRIEND_ID + " =? ",
                new String[]{Long.toString(input_id)});
        db.close();
    }

    public int updateFriend(Friend friend) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FRIEND_NAME, friend.getName());
        values.put(RESTAURANT_PH_NO, friend.getPhoneNumber());
        int update = db.update(TABLE_FRIENDS, values, FRIEND_ID + "= ?",
                new String[]{String.valueOf(friend.get_ID())});
        db.close();
        return update;
    }

    // Restaurant orders methods
    public void addRestaurantOrder(RestaurantOrder restaurantOrder, FriendsInOrder friendsInOrder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ORDER_DATE, restaurantOrder.getDate());
        values.put(ORDER_TIME, restaurantOrder.getTime());
        values.put(ORDER_RESTAURANT, restaurantOrder.getRestaurant().getName()); // usikker på denne
        db.insert(TABLE_RESTAURANT, null, values);

        // add values to friendsinorder table

        db.close();
    }
}
