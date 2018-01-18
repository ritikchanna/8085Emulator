package ritik.e8085;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
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
    String[] names = {

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
        gridView = (GridView) findViewById(R.id.gridview);
        View headerView = layoutInflater.inflate(R.layout.app_header, null);
        //gridView.addHeaderView(headerView);


        gridView.setAdapter(adapter);


        prefsHelper.initialize_registers(getApplicationContext());

    }

    @Override
    protected void onResume() {
        adapter.notifyDataSetChanged();
        super.onResume();
    }
}