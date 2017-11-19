package ritik.e8085;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.SimpleShowcaseEventListener;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

public class Assemble extends Activity {
    ListView assemble_listview;
    Assemble_adapter assembleAdapter;
    ArrayList<String> ADDRESS_ArrayList = new ArrayList<String>();
    ArrayList<String> CONTENT_ArrayList = new ArrayList<String>();
    InstructionDbHelper instructionDbHelper;
    ShowcaseView sv;

    int counter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assemble);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        assemble_listview =(ListView)(findViewById(R.id.listview_assemble));


        Log.d("Ritik", "onCreate: assemble 1");
        String starting_address = getIntent().getStringExtra("address");
        if(starting_address!=null)
        if (starting_address.length()!=5)
               starting_address="2000H";
        else
            starting_address=getIntent().getStringExtra("address");

        Log.d("Ritik", "onCreate: assemble 2");


        ADDRESS_ArrayList.add(0,starting_address);
        CONTENT_ArrayList.add(0,"");

        assembleAdapter=new Assemble_adapter(this,ADDRESS_ArrayList,CONTENT_ArrayList);

        assemble_listview.setAdapter(assembleAdapter);


        instructionDbHelper = new InstructionDbHelper(this);


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

        instructionDbHelper.close();




//
//        if(new PrefsHelper().isFirstrun(getApplicationContext())) {
//            sv = new ShowcaseView.Builder(this)
//                    .setTarget(Target.NONE)
//                    .setContentTitle("Assemble Mode")
//                    .setContentText("Assemble your instructions here")
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
//                sv.setShowcase(new ViewTarget(assemble_listview.getChildAt(0).findViewById(R.id.textview_address_lv_assemble)),true);
//                sv.setContentTitle("Program counter");
//                sv.setContentText("shows the program counter at every instruction");
//                break;
//            case 1:
//                sv.setShowcase(new ViewTarget(assemble_listview.getChildAt(0).findViewById(R.id.edittext_content_lv_assemble)), true);
//                sv.setContentTitle("Instruction field");
//                sv.setContentText("You can type the instruction , Watch out for syntax");
//                break;
//            case 2:
//                sv.setShowcase(new ViewTarget(assemble_listview.getChildAt(0).findViewById(R.id.button_done_lv_assemble)), true);
//                sv.setContentTitle("Assemble");
//                sv.setContentText("Press the button to assemble the instruction");
//                break;
//            case 3:
//
//                startActivity(new Intent(this,Singlestep.class));
//                finish();
//                break;
//
//
//        }
//        counter++;
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            assembleAdapter.setContent(data.getIntExtra("position",-1),data.getStringExtra("instruction"));
        }catch (Exception e){
            Log.d("Ritik", "onActivityResult: "+e.getMessage());
        }
    }
}