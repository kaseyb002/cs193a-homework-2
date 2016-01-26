package edu.stanford.kaseypb.grocerylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by kaseybaughan on 1/25/16.
 */

//https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
//http://stackoverflow.com/questions/17525886/listview-with-add-and-delete-buttons-in-each-row-in-android
public class GroceryListAdapter extends ArrayAdapter<GroceryItem>{
        public GroceryListAdapter(Context context, ArrayList<GroceryItem> groceryItems) {
            super(context, 0, groceryItems);
        }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get data from ArrayList<GroceryItem>
        final GroceryItem gItem = getItem(position);
        //i think this has something to do with reusing a view
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grocery_list_layout, parent, false);
        }
        //find TextViews from this row to populate data with
        TextView tvItem = (TextView) convertView.findViewById(R.id.itemTextView);
        TextView tvQuantity = (TextView) convertView.findViewById(R.id.quantityTextView);
        //stick data into TextViews
        tvItem.setText(gItem.getItem());
        tvQuantity.setText(Integer.toString(gItem.getQuantity()));
        //Handle buttons and add onClickListeners
        Button increaseQuantityButton = (Button) convertView.findViewById(R.id.increaseQuantityButton);
        Button decreaseQuantityButton = (Button) convertView.findViewById(R.id.decreaseQuantityButton);

        increaseQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gItem.incrementQuantity();
                notifyDataSetChanged();
            }
        });
        decreaseQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
                gItem.decrementQuantity();
                notifyDataSetChanged();
            }
        });

        //return completed view
        return convertView;
    }
}
