package com.example.rauch.malena.budgetoverview.Friends;

import android.provider.BaseColumns;

public class FriendContract {


    private FriendContract() {}

    /* Inner class that defines the table contents */
    public static class FriendEntry implements BaseColumns {
        public static final String TABLE_NAME = "Friends";
        public static final String COLUMN_NAME_FRIEND = "name";
    }
}
