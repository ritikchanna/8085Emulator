package ritik.e8085;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.ArrayList;

public class Singlestep extends Activity implements View.OnClickListener{
    Opcode opcode;
    TextView textview_address,textview_opcode,textview_instruction,textview_tstates;
    ListView listview_history;
    Button button_exceute;
    PrefsHelper prefsHelper =new PrefsHelper();
   SQLiteHelper sqlitehelper;
    ArrayAdapter<String> adapter;
    ArrayList instruction_history;
    int tstates;
    ShowcaseView sv;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Ritik", "onCreate: singlestep");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlestep);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        opcode=new Opcode(getApplicationContext());
        sqlitehelper=new SQLiteHelper(getApplicationContext());
        instruction_history=new ArrayList();

        adapter = new ArrayAdapter<>(this,R.layout.activity_instruction_history,R.id.textview_instruction_listview,instruction_history);



        textview_address=(TextView)findViewById(R.id.textview_address_single);
        textview_opcode=(TextView)findViewById(R.id.textview_opcode_single);
        textview_instruction=(TextView)findViewById(R.id.textview_instruction_single);
        textview_tstates=(TextView)findViewById(R.id.textview_tstate_single);
        listview_history=(ListView)findViewById(R.id.listview_history_single);
        listview_history.setAdapter(adapter);
        button_exceute=(Button)findViewById(R.id.button_excecute_single);
        button_exceute.setOnClickListener(this);

        String address=getIntent().getStringExtra("address");
        try {
            if (address.length() < 5)
                address = prefsHelper.getregister(getApplicationContext(), "pc");
        }catch (Exception e){
            address = prefsHelper.getregister(getApplicationContext(), "pc");
        }

       prefsHelper.saveregister(getApplicationContext(),"pc",address);
       tstates=0;




        next_instruction();
//        if(new PrefsHelper().isFirstrun(getApplicationContext())) {
//            sv = new ShowcaseView.Builder(this)
//                    .setTarget(Target.NONE)
//                    .setContentTitle("Single step execution")
//                    .setContentText("Instructions will be executed one by one in this mode")
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
//        switch (counter) {
//            case 0:
//                sv.setShowcase(new ViewTarget(findViewById(R.id.textview_address_single)),true);
//                sv.setContentTitle("Program counter");
//                sv.setContentText("Shows the program counter of the instruction that is to be executed");
//                break;
//            case 1:
//                sv.setShowcase(new ViewTarget(findViewById(R.id.textview_opcode_single)), true);
//                sv.setContentTitle("Op Code");
//                sv.setContentText("Shows the op code of the instruction that is to be executed");
//                break;
//            case 2:
//                sv.setShowcase(new ViewTarget(findViewById(R.id.textview_instruction_single)), true);
//                sv.setContentTitle("Instruction");
//                sv.setContentText("Shows the instruction to be executed");
//                break;
//            case 3:
//                sv.setShowcase(new ViewTarget(findViewById(R.id.listview_history_single)), true);
//                sv.setContentTitle("History");
//                sv.setContentText("Shows the instructions that were executed");
//                break;
//
//            case 4:
//                startActivity(new Intent(this,Onego.class));
//                finish();
//                break;
//
//
//        }
//        counter++;
//
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_excecute_single:
                Log.d("Ritik", "onClick: execute singlestep "+prefsHelper.getregister(getApplicationContext(),"hlt"));
                if(prefsHelper.getregister(getApplicationContext(),"hlt").substring(prefsHelper.getregister(getApplicationContext(),"hlt").length()-2,prefsHelper.getregister(getApplicationContext(),"hlt").length()-1).equals("0")) {
                    tstates = tstates + opcode.execute();
                    instruction_history.add(textview_address.getText().toString() + " : " + textview_opcode.getText().toString() + " " + textview_instruction.getText().toString());
                    adapter.notifyDataSetChanged();
                    listview_history.setSelection(adapter.getCount() - 1);
                    next_instruction();
                }
                break;
        }
    }

    public void next_instruction(){
        Log.d("Ritik", "next_instruction: at "+prefsHelper.getregister(getApplicationContext(),"pc"));
        textview_address.setText(prefsHelper.getregister(getApplicationContext(),"pc"));
        textview_opcode.setText(sqlitehelper.read_memory(prefsHelper.getregister(getApplicationContext(),"pc")));
        textview_instruction.setText(opcode.getinstruction(prefsHelper.getregister(getApplicationContext(),"pc")));
        textview_tstates.setText("T-States used: "+tstates);
            }


}
