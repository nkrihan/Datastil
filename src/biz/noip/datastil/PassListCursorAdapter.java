package biz.noip.datastil;

import java.util.Locale;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.Contacts;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class PassListCursorAdapter extends SimpleCursorAdapter implements Filterable {
	 
    private Context context;
 
    private int layout;
 
    public PassListCursorAdapter (Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
        this.context = context;
        this.layout = layout;
    }
 
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
 
        Cursor c = getCursor();
 
        final LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(layout, parent, false);
 
        int nameCol = c.getColumnIndex(Contacts.DISPLAY_NAME);
        int nameCol2 = c.getColumnIndex(Contacts.HAS_PHONE_NUMBER);
        String name = c.getString(nameCol);
        String name2 = c.getString(nameCol2);
 
        /**
         * Next set the name of the entry.
         */    
        TextView name_text = (TextView) v.findViewById(R.id.time);
        if (name_text != null) {
            name_text.setText(name);
        }
        
        TextView name_text2 = (TextView) v.findViewById(R.id.day);
        if (name_text2 != null) {
            name_text2.setText(name2);
        }
        return v;
    }
 
    @Override
    public void bindView(View v, Context context, Cursor c) {
 
        int nameCol = c.getColumnIndex(Contacts.DISPLAY_NAME);
        int nameCol2 = c.getColumnIndex(Contacts.HAS_PHONE_NUMBER);
        String name = c.getString(nameCol);
        String name2 = c.getString(nameCol2);
 
 
        /**
         * Next set the name of the entry.
         */    
        TextView name_text = (TextView) v.findViewById(R.id.day);
        if (name_text != null) {
            name_text.setText(name);
        }
        TextView name_text2 = (TextView) v.findViewById(R.id.time);
        if (name_text2 != null) {
            name_text2.setText(name2);
        }
    }
 
    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
        if (getFilterQueryProvider() != null) { return getFilterQueryProvider().runQuery(constraint); }
 
        StringBuilder buffer = null;
        String[] args = null;
        if (constraint != null) {
            buffer = new StringBuilder();
            buffer.append("UPPER(");
            buffer.append(Contacts.DISPLAY_NAME);
            buffer.append(") GLOB ?");
            args = new String[] { constraint.toString().toUpperCase(Locale.getDefault()) + "*" };
        }
 
        return context.getContentResolver().query(Contacts.CONTENT_URI, null,
                buffer == null ? null : buffer.toString(), args, Contacts.DISPLAY_NAME + " ASC");
    }
}