package com.example.rauch.malena.budgetoverview.Transaction;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rauch.malena.budgetoverview.R;

/**
 * Created by Test on 03.04.2018.
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionObjectViewHolder> {

    private Context context;
    private Cursor mCursor;

    public TransactionAdapter(Cursor cursor, Context context) {
        this.context = context;
        this.mCursor = cursor;
    }

    //create a viewHolder for the transaction Item
    @Override
    public TransactionObjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transact_recyclerview_item, parent, false);
        return new TransactionObjectViewHolder(view);
    }

    //fills with data
    @Override
    public void onBindViewHolder(TransactionObjectViewHolder holder, int position) {
        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null
        String name = mCursor.getString(mCursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_NAME_TRANSACTION));
        String amount = mCursor.getString(mCursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_AMOUNT_TRANSACTION));
        int booleanSpent = mCursor.getInt(mCursor.getColumnIndex(TransactionContract.TransactionEntry.COLUMN_SPENT_TRANSACTION));
        if (booleanSpent == 0) {
            amount = "- " + amount + " €";
            holder.mAmount.setTextColor(Color.RED);
        } else {
            amount = "+ " + amount + " €";
            holder.mAmount.setTextColor(Color.GREEN);
        }
        holder.mName.setText(name);
        holder.mAmount.setText(amount);

    }

    //returns amount of culloms in the cursor
    @Override
    public int getItemCount() {
        return (mCursor == null ? 0 : mCursor.getCount());
        //return mTransactions.size();
    }

    //swaps cirsors
    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

}