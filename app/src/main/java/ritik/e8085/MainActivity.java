package ritik.e8085;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.GridView;


public class MainActivity extends AppCompatActivity {

    GridView gridView;
    Grid adapter;
    PrefsHelper prefsHelper = new PrefsHelper();


    String[] gridColor = {

            "#2d2f30",
            "#4f5151",
            "#4f5151",
            "#2d2f30",
            "#2d2f30",
            "#4f5151",
            "#4f5151",
            "#2d2f30"
    };
    private String[] names = {

            "Register Mode",
            "Memory Mode",
            "Assemble Mode",
            "Single Step Execution",
            "One Go Execution",
            "Devices (Beta)",
            "Reset",
            "About"
    };
    int[] edit = {

            0,
            1,
            1,
            1,
            1,
            0,
            0,
            0
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.app_header);
        adapter = new Grid(MainActivity.this, gridColor, edit, names);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        gridView = findViewById(R.id.gridview);
        //View headerView = layoutInflater.inflate(R.layout.app_header, null);
        //gridView.addHeaderView(headerView);


        gridView.setAdapter(adapter);
        gridView.setRecyclerListener(new AbsListView.RecyclerListener() {
            @Override
            public void onMovedToScrapHeap(View view) {
                if ( view.hasFocus()){
                    view.clearFocus(); //we can put it inside the second if as well, but it makes sense to do it to all scraped views
                    //Optional: also hide keyboard in that case
                    if ( view instanceof EditText) {
                        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            }
        });


        prefsHelper.initialize_registers(getApplicationContext());

    }

    @Override
    protected void onResume() {
        adapter.notifyDataSetChanged();
        super.onResume();
    }
}