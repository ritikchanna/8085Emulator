package ritik.e8085;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.io.IOException;

public class Instructions extends Activity {
    ListView LISTVIEW;
    SimpleCursorAdapter cursorAdapter;
    int position_t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Ritik", "oncreate: this is cool too");
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        position_t = intent.getIntExtra("position", -1);
        setContentView(R.layout.activity_instructions);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        LISTVIEW = (ListView) findViewById(R.id.listview_instructions);
        final InstructionDbHelper instructionDbHelper = new InstructionDbHelper(this);
        try {
            // check if database exists in app path, if not copy it from assets
            instructionDbHelper.create();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        try {
            // open the database
            instructionDbHelper.open();
            instructionDbHelper.getWritableDatabase();
        } catch (SQLException sqle) {
            throw sqle;
        }

        final Cursor allinstructions = instructionDbHelper.getallInstructions();
        cursorAdapter = new SimpleCursorAdapter(this, R.layout.instruction_listview_element, allinstructions, new String[]{InstructionDbHelper.COLUMN_INSTRUCTION, InstructionDbHelper.COLUMN_OPCODE}, new int[]{android.R.id.text1, android.R.id.text2});
        LISTVIEW.setAdapter(cursorAdapter);
        LISTVIEW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position_t != -1) {
                    Log.d("Ritik", "onItemClick: position from intent " + position_t);
                    allinstructions.moveToPosition(position);
                    Log.d("Ritik", "onItemClick: " + allinstructions.getString(1));
                    Intent selectInstruction = new Intent();
                    selectInstruction.putExtra("instruction", allinstructions.getString(1));
                    selectInstruction.putExtra("position", position_t);
                    // Activity finished ok, return the data
                    setResult(RESULT_OK, selectInstruction);
                    finish();
                }
            }
        });
        instructionDbHelper.close();
    }
}
