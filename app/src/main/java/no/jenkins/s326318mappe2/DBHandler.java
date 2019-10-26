package no.jenkins.s326318mappe2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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


    static int DATABASE_VERSION = 14;
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
                " INTEGER," + "FOREIGN KEY ("+ORDER_RESTAURANT+") REFERENCES "+TABLE_RESTAURANT+"("+RESTAURANT_ID+") )";

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

    public String findFriendName(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FRIENDS, new String[]{
                        FRIEND_ID, FRIEND_NAME, FRIEND_PH_NO}, FRIEND_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        Friend friend = new
                Friend(Long.parseLong(cursor.getString(0)), cursor.getString(1),cursor.getString(2));
        cursor.close();
        db.close();
        return friend.getName();
    }

    public void addRestaurantOrder(RestaurantOrder restaurantOrder){
        SQLiteDatabase db = this.getWritableDatabase();
        // add values to RestaurantOrder table
        ContentValues values = new ContentValues();
        values.put(ORDER_DATE, restaurantOrder.getDate());
        values.put(ORDER_TIME, restaurantOrder.getTime());
        values.put(ORDER_RESTAURANT, restaurantOrder.getRestaurant_id());
        db.insert(TABLE_RESTAURANT_ORDERS, null, values);

        db.close();
    }

    public void addFriendsInOrder(FriendsInOrder friendsInOrder){
        SQLiteDatabase db = this.getWritableDatabase();
        // add values to FriendsInOrder table
        ContentValues valuesOrder = new ContentValues();
        valuesOrder.put(F_ID, friendsInOrder.getFriend_ID());
        valuesOrder.put(O_ID, friendsInOrder.getOrder_ID());
        //  valuesOrder.put(O_ID, friendsInOrder.getOrder_ID());
        db.insert(TABLE_FRIENDS_IN_ORDER,null, valuesOrder);

        db.close();
    }


    public ArrayList<RestaurantOrder> getRestaurantOrder(){
        ArrayList<RestaurantOrder> resOrderList = new ArrayList<RestaurantOrder>();
        String selectQuery = "SELECT * FROM " + TABLE_RESTAURANT_ORDERS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                RestaurantOrder resOrder = new RestaurantOrder();
                resOrder.set_ID(cursor.getLong(0));
                resOrder.setDate(cursor.getString(1));
                resOrder.setTime(cursor.getString(2));
                resOrder.setRestaurant_id(cursor.getInt(3));
                resOrderList.add(resOrder);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return resOrderList;
    }

    public Restaurant findRestaurant(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RESTAURANT, new String[]{
                        RESTAURANT_ID, RESTAURANT_NAME, RESTAURANT_ADRESS, RESTAURANT_PH_NO, RESTAURANT_TYPE}, RESTAURANT_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        Restaurant restaurant = new
                Restaurant(Long.parseLong(cursor.getString(0)), cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4));
        cursor.close();
        db.close();
        return restaurant;
    }

    public void deleteOrder(Long input_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RESTAURANT_ORDERS, ORDER_ID + " =? ",
                new String[]{Long.toString(input_id)});

        db.close();
    }

    public void deleteFriendsInOrder(Long input_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FRIENDS_IN_ORDER, O_ID + " =? ",
                new String[]{Long.toString(input_id)});

        db.close();
    }

    public ArrayList<FriendsInOrder> getFriendsInOrder() {
        ArrayList<FriendsInOrder> friendsInOrdersList = new ArrayList<FriendsInOrder>();
        String selectQuery = "SELECT * FROM " + TABLE_FRIENDS_IN_ORDER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                FriendsInOrder friend = new FriendsInOrder();
                friend.setFriend_ID(cursor.getInt(0));
                friend.setOrder_ID(cursor.getInt(1));
                friendsInOrdersList.add(friend);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return friendsInOrdersList;
    }

    public int getLastID() {
        String selecyQuery = "SELECT MAX(_id) FROM " + TABLE_RESTAURANT_ORDERS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selecyQuery, null);
        cursor.moveToFirst();
        int ID = cursor.getInt(0);
        cursor.close();
        return ID;
    }

    public ArrayList<FriendsInOrder> getFriendsInOrder(Long id) {
        ArrayList<FriendsInOrder> friendsInOrdersList = new ArrayList<FriendsInOrder>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FRIENDS_IN_ORDER, new String[]{
                        F_ID, O_ID}, O_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                FriendsInOrder friend = new FriendsInOrder();
                friend.setFriend_ID(cursor.getInt(0));
                friend.setOrder_ID(cursor.getInt(1));
                friendsInOrdersList.add(friend);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return friendsInOrdersList;
    }

}
