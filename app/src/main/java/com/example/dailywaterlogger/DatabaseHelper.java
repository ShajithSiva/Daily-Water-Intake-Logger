package com.example.dailywaterlogger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "WaterLogger.db";
    private static final int DATABASE_VERSION = 1;

    // Users table
    private static final String TABLE_USERS = "users";
    private static final String COL_USER_ID = "user_id";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";

    // Water intake table
    private static final String TABLE_WATER = "water_intake";
    private static final String COL_INTAKE_ID = "intake_id";
    private static final String COL_DATE = "date";
    private static final String COL_GLASSES = "glasses";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USERS_TABLE =
                "CREATE TABLE " + TABLE_USERS + " (" +
                        COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_USERNAME + " TEXT UNIQUE, " +
                        COL_PASSWORD + " TEXT)";

        String CREATE_WATER_TABLE =
                "CREATE TABLE " + TABLE_WATER + " (" +
                        COL_INTAKE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_USER_ID + " INTEGER, " +
                        COL_DATE + " TEXT, " +
                        COL_GLASSES + " INTEGER, " +
                        "FOREIGN KEY (" + COL_USER_ID + ") REFERENCES " +
                        TABLE_USERS + "(" + COL_USER_ID + "))";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_WATER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WATER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // ================= USERS =================

    // Register user
    public boolean registerUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    // Login user (returns user_id or -1)
    public int loginUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT " + COL_USER_ID +
                        " FROM " + TABLE_USERS +
                        " WHERE " + COL_USERNAME + "=? AND " + COL_PASSWORD + "=?",
                new String[]{username, password}
        );

        if (cursor.moveToFirst()) {
            int userId = cursor.getInt(0);
            cursor.close();
            return userId;
        }

        cursor.close();
        return -1;
    }

    // ================= WATER INTAKE =================

    // Add water intake for today
    public void addWater(int userId, int glasses) {
        SQLiteDatabase db = this.getWritableDatabase();

        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(new Date());

        ContentValues values = new ContentValues();
        values.put(COL_USER_ID, userId);
        values.put(COL_DATE, today);
        values.put(COL_GLASSES, glasses);

        db.insert(TABLE_WATER, null, values);
    }

    // Get today’s total intake
    public int getTodayIntake(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(new Date());

        Cursor cursor = db.rawQuery(
                "SELECT SUM(" + COL_GLASSES + ") FROM " + TABLE_WATER +
                        " WHERE " + COL_USER_ID + "=? AND " + COL_DATE + "=?",
                new String[]{String.valueOf(userId), today}
        );

        int total = 0;
        if (cursor.moveToFirst()) {
            total = cursor.getInt(0);
        }

        cursor.close();
        return total;
    }

    // Get history
    public Cursor getWaterHistory(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT " + COL_DATE + ", " + COL_GLASSES +
                        " FROM " + TABLE_WATER +
                        " WHERE " + COL_USER_ID + "=? ORDER BY " + COL_DATE + " DESC",
                new String[]{String.valueOf(userId)}
        );
    }
}
