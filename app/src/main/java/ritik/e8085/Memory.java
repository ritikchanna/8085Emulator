package ritik.e8085;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;

public class Memory extends Activity implements AbsListView.OnScrollListener {

    SQLiteHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    SQLiteListAdapter ListAdapter;

    ArrayList<String> ADDRESS_ArrayList = new ArrayList<String>();
    ArrayList<String> CONTENT_ArrayList = new ArrayList<String>();
    ListView LISTVIEW;
    TypeHelper typehelper;
    String last_memory = "0000G";
    private boolean isLoadMore = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        LISTVIEW = (ListView) findViewById(R.id.listview_memory_locations);
        ListAdapter = new SQLiteListAdapter(getApplicationContext(), ADDRESS_ArrayList, CONTENT_ArrayList);
        LISTVIEW.setAdapter(ListAdapter);
        SQLITEHELPER = new SQLiteHelper(this);

        typehelper = new TypeHelper();
        String starting_address = getIntent().getStringExtra("address");
        Log.d("Ritik", "onCreate: " + starting_address);
        if (starting_address.length() > 4) {
            starting_address = typehelper.Dec2Hex(Integer.toString(Integer.parseInt(typehelper.Hex2Dec(starting_address.substring(0, starting_address.length() - 1))) - 1));
            starting_address = new String(new char[4 - starting_address.length()]).replace('\0', '0') + starting_address + "H";
        } else
            starting_address = "0000G";

        ShowSQLiteDBdata(starting_address);


    }

    //TODO bug fixed here
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // do nothing
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (SCROLL_STATE_TOUCH_SCROLL == scrollState) {
            View currentFocus = getCurrentFocus();
            if (currentFocus != null) {
                currentFocus.clearFocus();
            }
        }
    }

    @Override
    public void onBackPressed() {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            currentFocus.clearFocus();
        }
        super.onBackPressed();
    }

    @Override
    protected void onResume() {

        //ShowSQLiteDBdata("0000G") ;
        LISTVIEW.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                int lastInScreen = firstVisibleItem + visibleItemCount;
                if ((lastInScreen == totalItemCount)) {


                    //isLoadMore = true;
                    // Add footer to ListView
                    //list.addFooterView(loadingFooter);
                    last_memory = new String(new char[4 - typehelper.Dec2Hex(Integer.toString(totalItemCount - 1)).length()]).replace('\0', '0') + typehelper.Dec2Hex(Integer.toString(totalItemCount - 1)) + "H";
                    ShowSQLiteDBdata(last_memory);


                }
            }
        });
        super.onResume();
    }

    private void ShowSQLiteDBdata(String last_address) {

        SQLITEDATABASE = SQLITEHELPER.getWritableDatabase();

        cursor = SQLITEDATABASE.rawQuery("SELECT * FROM " + SQLiteHelper.TABLE_NAME + " LIMIT 40", null);
        if (cursor.getCount() < 10) {
            initiate_memory task = new initiate_memory(Memory.this);
            task.execute();
        }


        cursor = SQLITEDATABASE.rawQuery("SELECT * FROM " + SQLiteHelper.TABLE_NAME + " WHERE " + SQLiteHelper.KEY_ADDRESS + " > '" + last_address + "' LIMIT 100", null);

        //ADDRESS_ArrayList.clear();
        //CONTENT_ArrayList.clear();

        final int index = LISTVIEW.getFirstVisiblePosition();
        View v = LISTVIEW.getChildAt(0);
        final int top = (v == null) ? 0 : v.getTop();


        if (cursor.moveToFirst()) {
            Log.d("Ritik", "Cursor firsst address: " + cursor.getString((cursor.getColumnIndex(SQLiteHelper.KEY_ADDRESS))));
            do {
                ADDRESS_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_ADDRESS)));

                CONTENT_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_CONTENT)));

            } while (cursor.moveToNext());
        }


        ListAdapter.notifyDataSetChanged();
//
//        LISTVIEW.post(new Runnable() {
//            @Override
//            public void run() {
//                LISTVIEW.setSelectionFromTop(MainActivity, top);
//            }
//        });


        cursor.close();
    }

    public void reset(int index, String Content) {
        Log.d("Ritik", "reset: " + index + " " + Content);
        CONTENT_ArrayList.set(index, Content);
        Log.d("Ritik", "reset: 1");
        ListAdapter.notifyDataSetChanged();
        Log.d("Ritik", "reset: 2");
    }


    private class initiate_memory extends AsyncTask<Void, Void, Void> {
        private ProgressDialog dialog;

        public initiate_memory(Memory activity) {
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Initiating Memory for first use ...");
            dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Do in Background", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    startActivity(new Intent(Memory.this, MainActivity2.class));
                }
            });

            dialog.show();
        }

        @Override
        protected void onPostExecute(Void result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                SQLITEHELPER.initiate_memory(SQLITEDATABASE);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

    }

}