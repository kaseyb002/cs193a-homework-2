package edu.stanford.kaseypb.grocerylist;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import edu.stanford.kaseypb.grocerylist.GroceryItem;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    private ArrayList<GroceryItem> groceryList = new ArrayList<GroceryItem>();
    private GroceryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setGroceryListListViewSettings();
        loadListFromInternalStorage();
    }

    public void addItem(View view) {
        GroceryItem gi = new GroceryItem(getTextFromEntryForm(), 1);
        addItemToList(gi, true);
        clearTextEntryForm();
    }

    public void removeItem(int itemIndex) {
        groceryList.remove(itemIndex);
        adapter.notifyDataSetChanged();
        saveListToInternalStorage();
    }

    private void addItemToList(GroceryItem item, boolean toTopOfList) {
        if(toTopOfList) {
            groceryList.add(0, item);
        } else {
            groceryList.add(item);
        }
        adapter.notifyDataSetChanged();
        saveListToInternalStorage();
    }

    private void setGroceryListListViewSettings() {
        adapter = new GroceryListAdapter(
                this,
                groceryList
        );
        final ListView groceryListView = (ListView) findViewById(R.id.groceryListListView);
        groceryListView.setAdapter(adapter);
        groceryListView.setOnItemLongClickListener(this);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        removeItem(position);
        Toast.makeText(MainActivity.this, "Item Removed", Toast.LENGTH_SHORT).show();
        return false;
    }

    private String getTextFromEntryForm() {
        EditText addItemEditText = (EditText) findViewById(R.id.itemEditText);
        return addItemEditText.getText().toString();
    }

    private void clearTextEntryForm() {
        EditText addItemEditText = (EditText) findViewById(R.id.itemEditText);
        addItemEditText.setText("");
    }

    private void saveListToInternalStorage() {
        PrintStream out = null;
        try {
            out = new PrintStream(openFileOutput("List.txt", MODE_PRIVATE));
            for (GroceryItem gi : groceryList) {
                out.println(gi.getItem() + "//" + gi.getQuantity());
            }
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadListFromInternalStorage() {
        Scanner scan = null;
        try {
            scan = new Scanner(openFileInput("List.txt"));
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                List<String> gList = Arrays.asList(line.split("//"));
                String item = gList.get(0);
                int quantity = Integer.parseInt(gList.get(1));
                addItemToList(new GroceryItem(item, quantity), false);
            }
        } catch (FileNotFoundException e) {
            saveListToInternalStorage();//first time opening app
        }
    }
}

