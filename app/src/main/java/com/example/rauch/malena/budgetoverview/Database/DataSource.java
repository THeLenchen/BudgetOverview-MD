package com.example.rauch.malena.budgetoverview.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.rauch.malena.budgetoverview.Depts.DeptContract;
import com.example.rauch.malena.budgetoverview.Friends.FriendContract;
import com.example.rauch.malena.budgetoverview.Transaction.TransactionContract;

import java.util.ArrayList;

/**
 * Created by Test on 15.03.2018.
 */

public class DataSource {

    private Context mContext;
    private SQLiteDatabase mDatabase;
    private DBHelper mDBHelper;
    private String[] DEPTS_ALL_COLUMNS = {DeptContract.DeptEntry._ID,
            DeptContract.DeptEntry.COLUM_NAME_BOOLEAN_GIVE,
            DeptContract.DeptEntry.COLUMN_NAME_FRIEND,
            DeptContract.DeptEntry.COLUMN_NAME_REASON,
            DeptContract.DeptEntry.COLUMN_NAME_AMAOUNT};
    private String[] TRANSACTIONS_ALL_COLUMNS = {TransactionContract.TransactionEntry._ID,
            TransactionContract.TransactionEntry.COLUMN_NAME_TRANSACTION,
            TransactionContract.TransactionEntry.COLUMN_SPENT_TRANSACTION,
            TransactionContract.TransactionEntry.COLUMN_AMOUNT_TRANSACTION};
    private String[] FRIENDS_ALL_COLUMNS = {FriendContract.FriendEntry._ID,
            FriendContract.FriendEntry.COLUMN_NAME_FRIEND};

    public DataSource(Context context) {
        mContext = context;
        mDBHelper = new DBHelper(mContext);
    }

    // Opens the database to use it
    public void open() {
        mDatabase = mDBHelper.getWritableDatabase();
    }

    // Closes the database when you no longer need it
    public void close() {
        mDBHelper.close();
    }


    /**
     * DEPTS
     */
    //add Dept to the Database
    public void createDept(String friend, double amount, int booleanGive, String reason) {
        ContentValues values = new ContentValues();
        values.put(DeptContract.DeptEntry.COLUMN_NAME_FRIEND, friend);
        values.put(DeptContract.DeptEntry.COLUMN_NAME_REASON, reason);
        values.put(DeptContract.DeptEntry.COLUMN_NAME_AMAOUNT, amount);
        values.put(DeptContract.DeptEntry.COLUM_NAME_BOOLEAN_GIVE, booleanGive);
        mDatabase.insert(DeptContract.DeptEntry.TABLE_NAME, null, values);
    }

    //retruns a Cursor which contains all Depts, last Dept first
    public Cursor getAllDepts() {
        return mDatabase.query(DeptContract.DeptEntry.TABLE_NAME,
                DEPTS_ALL_COLUMNS, null, null, null, null, DeptContract.DeptEntry._ID + " DESC");
    }

    //delete a Dept
    public double deleteDept(long id) {
        Cursor cursor = mDatabase.query(DeptContract.DeptEntry.TABLE_NAME, DEPTS_ALL_COLUMNS, DeptContract.DeptEntry._ID + " =?", new String[]{Long.toString(id)}, null, null, null);
        int columIndexAmount = cursor.getColumnIndex(DeptContract.DeptEntry.COLUMN_NAME_AMAOUNT);
        double amount = cursor.getDouble(columIndexAmount);
        int columIndexBoolean = cursor.getColumnIndex(DeptContract.DeptEntry.COLUM_NAME_BOOLEAN_GIVE);
        if (cursor.getInt(columIndexBoolean) == 1) {
            amount = -amount;
        }
        mDatabase.delete(TransactionContract.TransactionEntry.TABLE_NAME, TransactionContract.TransactionEntry._ID + " =?",
                new String[]{Long.toString(id)});
        return amount;
    }

    //Update a det
    public void updateDept(long id, String friend, double amount, boolean booleanGive, String reason) {
        ContentValues args = new ContentValues();
        args.put(DeptContract.DeptEntry.COLUMN_NAME_FRIEND, friend);
        args.put(DeptContract.DeptEntry.COLUMN_NAME_REASON, reason);
        args.put(DeptContract.DeptEntry.COLUMN_NAME_AMAOUNT, amount);
        args.put(DeptContract.DeptEntry.COLUM_NAME_BOOLEAN_GIVE, booleanGive);

        mDatabase.update(DeptContract.DeptEntry.TABLE_NAME, args, DeptContract.DeptEntry._ID + "=?",
                new String[]{Long.toString(id)});
    }

    //returns the dept with the given id
    public Cursor getOneDept(long id) {
        return mDatabase.query(DeptContract.DeptEntry.TABLE_NAME, DEPTS_ALL_COLUMNS, DeptContract.DeptEntry._ID + " =?", new String[]{Long.toString(id)}, null, null, null);
    }

    //create Cursor & move it to the last added Entry to get the amount of the last added Dept
    public double getDeptAmountOfLatestEntry() {
        Cursor cursor = mDatabase.query(DeptContract.DeptEntry.TABLE_NAME,
                DEPTS_ALL_COLUMNS, null, null, null, null, null);
        cursor.moveToLast();
        int columIndexAmount = cursor.getColumnIndex(DeptContract.DeptEntry.COLUMN_NAME_AMAOUNT);
        double amount = cursor.getDouble(columIndexAmount);
        int columIndexBoolean = cursor.getColumnIndex(DeptContract.DeptEntry.COLUM_NAME_BOOLEAN_GIVE);
        if (cursor.getInt(columIndexBoolean) == 1) {
            amount = -amount;
        }
        return amount;
    }

    public double getAmountOfMoneyToGiveOrToGet(boolean give) {
        int booleanAsInt = 0;
        if (give) booleanAsInt = 1;
        Cursor cursor = mDatabase.query(DeptContract.DeptEntry.TABLE_NAME,
                DEPTS_ALL_COLUMNS, DeptContract.DeptEntry.COLUM_NAME_BOOLEAN_GIVE + " = " + booleanAsInt, null, null, null, null);
        int columIndexAmount = cursor.getColumnIndex(DeptContract.DeptEntry.COLUMN_NAME_AMAOUNT);
        double amount = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            amount = amount + cursor.getDouble(columIndexAmount);
            cursor.moveToNext();
        }
        return amount;
    }


    /**
     * TRANSACTIONS
     */
    //creates a new Transaction an adds it to the Database
    public void createTransaction(String name, double amount, int booleanSpent) {
        ContentValues values = new ContentValues();
        values.put(TransactionContract.TransactionEntry.COLUMN_NAME_TRANSACTION, name);
        values.put(TransactionContract.TransactionEntry.COLUMN_AMOUNT_TRANSACTION, amount);
        values.put(TransactionContract.TransactionEntry.COLUMN_SPENT_TRANSACTION, booleanSpent);
        mDatabase.insert(TransactionContract.TransactionEntry.TABLE_NAME, null, values);
    }

    //returns a cursor with all Transactions
    public Cursor getAllTransactions() {
        return mDatabase.query(TransactionContract.TransactionEntry.TABLE_NAME,
                TRANSACTIONS_ALL_COLUMNS, null, null, null, null, TransactionContract.TransactionEntry._ID + " DESC");
    }

    //updates a specific transaction
    public void updateTransaction(long id, String name, double amount, boolean booleanSpent) {
        ContentValues args = new ContentValues();
        args.put(TransactionContract.TransactionEntry.COLUMN_NAME_TRANSACTION, name);
        args.put(TransactionContract.TransactionEntry.COLUMN_AMOUNT_TRANSACTION, amount);
        args.put(TransactionContract.TransactionEntry.COLUMN_SPENT_TRANSACTION, booleanSpent);

        mDatabase.update(TransactionContract.TransactionEntry.TABLE_NAME, args, TransactionContract.TransactionEntry._ID + "=?",
                new String[]{Long.toString(id)});
    }

    //returns the transaction which hase the given id
    public Cursor getOneTransaction(long id) {
        return mDatabase.query(TransactionContract.TransactionEntry.TABLE_NAME, DEPTS_ALL_COLUMNS, TransactionContract.TransactionEntry._ID + " =?", new String[]{Long.toString(id)}, null, null, null);
    }

    //create Cursor & move it to the last added Entry to get the amount of the last added Transaction
    public double getTransactionAmountOfLatestEntry() {
        Cursor cursor = mDatabase.query(TransactionContract.TransactionEntry.TABLE_NAME,
                TRANSACTIONS_ALL_COLUMNS, null, null, null, null, null);
        cursor.moveToLast();
        int columIndexAmount = cursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_AMOUNT_TRANSACTION);
        double amount = cursor.getDouble(columIndexAmount);
        int columIndexBoolean = cursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_SPENT_TRANSACTION);
        if (cursor.getInt(columIndexBoolean) == 0) {
            amount = -amount;
        }
        return amount;
    }

    /**
     * Friends
     */

    //creates a new Friend an adds it to the Database
    public void createFriend(String name) {
        ContentValues values = new ContentValues();
        values.put(FriendContract.FriendEntry.COLUMN_NAME_FRIEND, name);
        mDatabase.insert(FriendContract.FriendEntry.TABLE_NAME, null, values);
    }

    //returns a list with all Names from Friends
    public ArrayList<String> getListAllFriends() {
        ArrayList<String> list = new ArrayList<String>();
        Cursor cursor = mDatabase.query(FriendContract.FriendEntry.TABLE_NAME,
                FRIENDS_ALL_COLUMNS, null, null, null, null, null);
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex(FriendContract.FriendEntry.COLUMN_NAME_FRIEND));
                list.add(name);
            }
        } else {
            list.add("Unknown");
        }
        return list;
    }

}