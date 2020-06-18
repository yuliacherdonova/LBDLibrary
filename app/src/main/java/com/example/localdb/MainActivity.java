package com.example.localdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    DBHelper DB;
    EditText placeBox;
    EditText addrBox;
    ListView geoList;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        placeBox = findViewById(R.id.editText);
        addrBox = findViewById(R.id.editText2);
        geoList = findViewById(R.id.geoList);
        DB = new DBHelper(this);
        loadData();
    }

    public void clickBtn(View view) {
        if (!placeBox.getText().toString().equals("") || !addrBox.getText().toString().equals("")) {
            SQLiteDatabase database = DB.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            String place = placeBox.getText().toString();
            String addr = addrBox.getText().toString();

            contentValues.put(DBHelper.KEY_PLACE, place);
            contentValues.put(DBHelper.KEY_ADDR, addr);
            database.insert(DBHelper.TABLE, null, contentValues);
            Toast.makeText(this, "ДОБАВЛЕНО", Toast.LENGTH_SHORT).show();
            loadData();
        }
    }

    public void loadData() {
        SQLiteDatabase database = DB.getWritableDatabase();
        userCursor =  database.rawQuery("SELECT * FROM "+ DBHelper.TABLE, null);
        String[] headers = new String[] {DBHelper.KEY_PLACE, DBHelper.KEY_ADDR};

        userAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_expandable_list_item_2,
                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);

        geoList.setAdapter(userAdapter);
    }

}


