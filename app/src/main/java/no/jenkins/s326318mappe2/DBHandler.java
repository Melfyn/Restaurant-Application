package no.jenkins.s326318mappe2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    static String ORDER_FRIEND = "Friend";

    static int DATABASE_VERSION = 1;
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT_ORDERS);
        onCreate(db);
    }
}
