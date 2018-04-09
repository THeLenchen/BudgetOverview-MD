package com.example.rauch.malena.budgetoverview.Depts;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rauch.malena.budgetoverview.R;
import com.example.rauch.malena.budgetoverview.Transaction.TransactionContract;


public class DeptAdapter extends RecyclerView.Adapter<DeptViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    //Constructor
    public DeptAdapter(Context context, Cursor cursor) {
        mCursor = cursor;
        mContext = context;
    }

    //create a viewHolder for the dept Item
    @Override
    public DeptViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dept_recyclerview_item, parent, false);
        // Return a new holder instance
        return new DeptViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DeptViewHolder holder, final int position) {
        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null
        String friend = mCursor.getString(mCursor.getColumnIndex(DeptContract.DeptEntry.COLUMN_NAME_FRIEND));
        String amount = mCursor.getString(mCursor.getColumnIndex(DeptContract.DeptEntry.COLUMN_NAME_AMAOUNT));
        int booleanSpent = mCursor.getInt(mCursor.getColumnIndex(DeptContract.DeptEntry.COLUM_NAME_BOOLEAN_GIVE));
        if (booleanSpent == 1) {
            amount = "- " + amount + " €";
            holder.mAmount.setTextColor(Color.rgb(153, 51, 255));
        } else {
            amount = "+ " + amount + " €";
            holder.mAmount.setTextColor(Color.rgb(255, 102, 0));
        }
        holder.mFriend.setText(friend);
        holder.mAmount.setText(amount);
    }

    //Counts the amount of items in the cursor
    @Override
    public int getItemCount() {
        return (mCursor == null ? 0 : mCursor.getCount());
    }

    //makes a new cursor and closes the old one
    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

}