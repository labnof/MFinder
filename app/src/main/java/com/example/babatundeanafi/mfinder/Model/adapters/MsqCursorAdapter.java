package com.example.babatundeanafi.mfinder.Model.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.babatundeanafi.mfinder.R;

/**
 * Created by babatundeanafi on 24/10/16.
 */

public class MsqCursorAdapter extends CursorAdapter {
    public MsqCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.



    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.listview_template, parent, false);

    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView msqName = (TextView) view.findViewById(R.id.msqName);
        TextView msqAddress = (TextView) view.findViewById(R.id.msqAddress);
        TextView msqPhone = (TextView) view.findViewById(R.id.msqPhone);
        // Extract properties from cursor
        String name = cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_MOSQUE_NAME));
        String address = cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_MOSQUE_ADDRESS));
        String phone = cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_MOSQUE_PHONE));
        // Populate fields with extracted properties
        msqName.setText(name);
        msqAddress.setText(address);
        msqPhone.setText(phone);
    }
}