package com.example.rauch.malena.budgetoverview.Transaction;

import android.provider.BaseColumns;

/**
 * Created by Test on 27.03.2018.
 */

public class TransactionContract {


    private TransactionContract() {}

    /* Inner class that defines the table contents */
    public static class TransactionEntry implements BaseColumns {
        public static final String TABLE_NAME = "Transactions";
        public static final String COLUMN_NAME_TRANSACTION = "transactionName";
        public static final String COLUMN_AMOUNT_TRANSACTION = "transactionAmount";
        public static final String COLUMN_SPENT_TRANSACTION = "transactionSpent";

    }
}
