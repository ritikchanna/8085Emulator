package ritik.e8085;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import static ritik.e8085.SQLiteHelper.KEY_ADDRESS;
import static ritik.e8085.SQLiteHelper.KEY_CONTENT;
import static ritik.e8085.SQLiteHelper.TABLE_NAME;

public class Devices extends Activity {


    SQLiteHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    private DevicesAdapter ListAdapter;

    ArrayList<String> ADDRESS_ArrayList = new ArrayList<String>();
    ArrayList<String> CONTENT_ArrayList = new ArrayList<String>();
    ListView LISTVIEW;
    //private TypeHelper typehelper;
    //ShowcaseView sv;
    //int counter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        LISTVIEW = findViewById(R.id.listview_devices);
        ListAdapter = new DevicesAdapter(getApplicationContext(), ADDRESS_ArrayList, CONTENT_ArrayList);
        LISTVIEW.setAdapter(ListAdapter);
        SQLITEHELPER = new SQLiteHelper(this);
        Button addmoredevices = new Button(this);
        addmoredevices.setText("Add more devices");
        addmoredevices.setBackgroundResource(R.drawable.btn_frame_trans);
        addmoredevices.setTextColor(Color.parseColor("#f2f5f7"));
        LISTVIEW.addFooterView(addmoredevices);
        addmoredevices.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ADDRESS_ArrayList.add("DI00H");
                CONTENT_ArrayList.add("00H");
                ListAdapter.notifyDataSetChanged();
            }
        });


        ShowSQLiteDBdata();

//        if(new PrefsHelper().isFirstrun(getApplicationContext())) {
//            sv = new ShowcaseView.Builder(this)
//                    .setTarget(Target.NONE)
//                    .setContentTitle("One go execution")
//                    .setContentText("Instructions can be executed in one go also in this mode")
//                    .setStyle(R.style.CustomShowcaseTheme3)
//                    .setOnClickListener(
//                            new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    nextshowcase();
//                                }
//                            }
//
//                    )
//                    .build();
//            sv.show();
//        }


    }

//    public void nextshowcase(){
//        Log.d("Ritik", "nextshowcase: ");
//        switch (counter) {
//            case 0:
//                sv.setShowcase(new ViewTarget(LISTVIEW.getChildAt(0).findViewById(R.id.device_address_listview)),true);
//                sv.setContentTitle("Program counter");
//                sv.setContentText("shows the program counter at every instruction");
//                break;
//            case 1:
//                sv.setShowcase(new ViewTarget(LISTVIEW.getChildAt(0).findViewById(R.id.device_content_listview)), true);
//                sv.setContentTitle("Instruction field");
//                sv.setContentText("You can type the instruction , Watch out for syntax");
//                break;
//            case 2:
//                sv.setShowcase(new ViewTarget(LISTVIEW.getChildAt(0).findViewById(R.id.device_delete_listview)), true);
//                sv.setContentTitle("Assemble");
//                sv.setContentText("Press the button to assemble the instruction");
//                break;
//            case 3:
//                new PrefsHelper().firstrun(getApplicationContext());
//                finish();
//                break;
//
//
//        }
//        counter++;
//
//    }

//    @Override
//    protected void onResume() {
//
//        //ShowSQLiteDBdata("0000G") ;
//        LISTVIEW.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView absListView, int i) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem,
//                                 int visibleItemCount, int totalItemCount) {
//                int lastInScreen = firstVisibleItem + visibleItemCount;
//                if((lastInScreen == totalItemCount)){
//
//
//                    //isLoadMore = true;
//                    // Add footer to ListView
//                    //list.addFooterView(loadingFooter);
//                    last_memory=new String(new char[4-typehelper.Dec2Hex(Integer.toString(totalItemCount-1)).length()]).replace('\0', '0')+typehelper.Dec2Hex(Integer.toString(totalItemCount-1))+"H";
//                    ShowSQLiteDBdata(last_memory);
//
//
//                }
//            }
//        });
//        super.onResume();
//    }

    private void ShowSQLiteDBdata() {

        SQLITEDATABASE = SQLITEHELPER.getWritableDatabase();

        cursor = SQLITEDATABASE.query(true, TABLE_NAME, new String[]{KEY_ADDRESS, KEY_CONTENT},
                KEY_ADDRESS + " LIKE 'S%'", null, null, null, null, null);
        if (cursor.getCount() < 1) {
            Devices.initiate_devices task = new Devices.initiate_devices(Devices.this);
            task.execute();
        }
        //cursor = SQLITEDATABASE.rawQuery("SELECT * FROM "+ TABLE_NAME+" WHERE "+SQLITEHELPER.KEY_ADDRESS+" > '"+last_address+"' LIMIT 100", null);

        //ADDRESS_ArrayList.clear();
        //CONTENT_ArrayList.clear();

        //final int MainActivity =LISTVIEW.getFirstVisiblePosition();
        //View v = LISTVIEW.getChildAt(0);
        //final int top = (v == null) ? 0 : v.getTop();


        if (cursor.moveToFirst()) {
            Log.d("Ritik", "Cursor firsst address: " + cursor.getString((cursor.getColumnIndex(KEY_ADDRESS))));
            do {
                ADDRESS_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_ADDRESS)));

                CONTENT_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_CONTENT)));

            } while (cursor.moveToNext());
        }
        cursor = SQLITEDATABASE.query(true, TABLE_NAME, new String[]{KEY_ADDRESS, KEY_CONTENT},
                KEY_ADDRESS + " LIKE 'DI%'", null, null, null, null, null);
        if (cursor.moveToFirst()) {
            Log.d("Ritik", "Cursor firsst address: " + cursor.getString((cursor.getColumnIndex(KEY_ADDRESS))));
            do {
                ADDRESS_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_ADDRESS)));

                CONTENT_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_CONTENT)));

            } while (cursor.moveToNext());
        }
        cursor = SQLITEDATABASE.query(true, TABLE_NAME, new String[]{KEY_ADDRESS, KEY_CONTENT},
                KEY_ADDRESS + " LIKE 'DO%'", null, null, null, null, null);
        if (cursor.moveToFirst()) {
            Log.d("Ritik", "Cursor firsst address: " + cursor.getString((cursor.getColumnIndex(KEY_ADDRESS))));
            do {
                ADDRESS_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_ADDRESS)));

                CONTENT_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_CONTENT)));

            } while (cursor.moveToNext());
        }

        ListAdapter.notifyDataSetChanged();
        cursor.close();
    }

    private class initiate_devices extends AsyncTask<Void, Void, Void> {
        private ProgressDialog dialog;

        public initiate_devices(Devices activity) {
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Initiating Devices for first use ...");
            dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Do in Background", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    startActivity(new Intent(Devices.this, MainActivity2.class));
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
                SQLITEHELPER.initiate_devices(SQLITEDATABASE);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

    }
}
