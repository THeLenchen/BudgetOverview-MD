package com.example.rauch.malena.budgetoverview.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rauch.malena.budgetoverview.Depts.DeptContract;
import com.example.rauch.malena.budgetoverview.Friends.FriendContract;
import com.example.rauch.malena.budgetoverview.Transaction.TransactionContract;

/**
 * Created by Test on 15.03.2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_FIRST_NAME = "budgetOverview";
    public static final String DATABASE_NAME_EXTENSION = ".db";
    public static final String DATABASE_NAME = DATABASE_FIRST_NAME + DATABASE_NAME_EXTENSION;
    public static final int DATABASE_VERSION = 11;

    //Create the tables
    private static final String DATABASE_CREATE_TRANSACTION =
            "CREATE TABLE " + TransactionContract.TransactionEntry.TABLE_NAME +
                    "(" +
                    TransactionContract.TransactionEntry._ID + " INTEGER PRIMARY KEY, " +
                    TransactionContract.TransactionEntry.COLUMN_NAME_TRANSACTION + " TEXT, " +
                    TransactionContract.TransactionEntry.COLUMN_SPENT_TRANSACTION + " INTEGER, " +
                    TransactionContract.TransactionEntry.COLUMN_AMOUNT_TRANSACTION + " BLOB" +
                    ");";
    private static final String DATABASE_CREATE_DEPT =
            "CREATE TABLE " + DeptContract.DeptEntry.TABLE_NAME +
                    "(" +
                    DeptContract.DeptEntry._ID + " INTEGER PRIMARY KEY, " +
                    DeptContract.DeptEntry.COLUMN_NAME_AMAOUNT + " BLOB, " +
                    DeptContract.DeptEntry.COLUMN_NAME_FRIEND + " TEXT, " +
                    DeptContract.DeptEntry.COLUMN_NAME_REASON + " TEXT, " +
                    DeptContract.DeptEntry.COLUM_NAME_BOOLEAN_GIVE + " INTEGER " +
                    ");";
    private static final String DATABASE_CREATE_FRIENDS =
            "CREATE TABLE " + FriendContract.FriendEntry.TABLE_NAME +
                    "(" +
                    FriendContract.FriendEntry._ID + " INTEGER PRIMARY KEY, " +
                    FriendContract.FriendEntry.COLUMN_NAME_FRIEND +
                    ");";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //creating the Database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE_TRANSACTION);
        sqLiteDatabase.execSQL(DATABASE_CREATE_DEPT);
        sqLiteDatabase.execSQL(DATABASE_CREATE_FRIENDS);
    }

    //upgrade the Database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +
                DeptContract.DeptEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " +
                TransactionContract.TransactionEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " +
                FriendContract.FriendEntry.TABLE_NAME);
        onCreate(db);
    }
}
