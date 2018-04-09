package com.example.rauch.malena.budgetoverview;

/**
 * Created by Test on 28.03.2018.
 */

public interface ClickListener {
    void reminderonClick(long id);

    void reminderonLongClick(long id);

    /*
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        CursorLoader cursorLoader = new CursorLoader(this.getContext(), TransactionContract.CONTENT_URI, null, null, null, null);
        return cursorLoader;
    }
*/
    void onLoadFinished(android.support.v4.content.Loader<Object> loader, Object data);

    void onLoaderReset(android.support.v4.content.Loader<Object> loader);

    void transactionOnLongClick(long id);
    void transactionOnClick(long id);
}
