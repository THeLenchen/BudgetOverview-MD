package com.example.rauch.malena.budgetoverview.Transaction;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.rauch.malena.budgetoverview.ClickListener;
import com.example.rauch.malena.budgetoverview.R;

/**
 * Created by Test on 28.03.2018.
 */

public class TransactionObjectViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

    public TextView mName;
    public TextView mAmount;
    public View mView;
    private ClickListener mClickListener;


    public TransactionObjectViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mName = mView.findViewById(R.id.transact_item_name);
        mAmount = mView.findViewById(R.id.transact_item_amount);
    }

    @Override
    public boolean onLongClick(View view) {
        int clickedPosition = getAdapterPosition();
        mClickListener.transactionOnClick(clickedPosition);
        return true;

    }
}
