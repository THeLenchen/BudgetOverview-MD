package com.example.rauch.malena.budgetoverview.Depts;

import android.provider.BaseColumns;

/**
 * Created by Test on 27.03.2018.
 */

public class DeptContract {


    private DeptContract() {}

    /* Inner class that defines the table contents */
    public static class DeptEntry implements BaseColumns {
        public static final String TABLE_NAME = "Depts";
        public static final String COLUMN_NAME_AMAOUNT = "amount";
        public static final String COLUMN_NAME_FRIEND = "friend";
        public static final String COLUMN_NAME_REASON = "reason";
        public static final String COLUM_NAME_BOOLEAN_GIVE = "booleanGet";
    }
}