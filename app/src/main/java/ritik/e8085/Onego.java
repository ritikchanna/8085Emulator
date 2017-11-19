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

import java.util.ArrayList;

public class Onego extends Activity {
    ListView listview_onego;
    Button button_onego;
    TextView textview_onego;
    PrefsHelper prefsHelper =new PrefsHelper();
    int tstates;
    ArrayAdapter<String> adapter;
    ArrayList instruction_list;
    SQLiteHelper sqlitehelper;
    ShowcaseView sv;
    Opcode opcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onego);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        opcode=new Opcode(getApplicationContext());
        sqlitehelper=new SQLiteHelper(getApplicationContext());





        listview_onego=(ListView)findViewById(R.id.listview_onego);
        button_onego=(Button) findViewById(R.id.button_onego);
        textview_onego=(TextView)findViewById(R.id.textview_onego);
        instruction_list=new ArrayList();

        adapter = new ArrayAdapter<>(this,R.layout.activity_instruction_history,R.id.textview_instruction_listview,instruction_list);
        listview_onego.setAdapter(adapter);

        String address=getIntent().getStringExtra("address");
        try {
        if(address.length()<5)
            address = prefsHelper.getregister(getApplicationContext(), "pc");
        }catch (Exception e){
            address = prefsHelper.getregister(getApplicationContext(), "pc");
        }
        prefsHelper.saveregister(getApplicationContext(),"pc",address);
        tstates=0;
        button_onego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addallinstructions();

            }
        });
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
//                                gotodevices();
//                                }
//                            }
//
//                    )
//                    .build();
//            sv.show();
//        }
//
    }
//    public void gotodevices(){
//
//        startActivity(new Intent(this,Devices.class));
//        finish();
//    }

    public void addallinstructions(){
        String opcode_s="00H";
        button_onego.setEnabled(false);
        if(prefsHelper.getregister(getApplicationContext(),"hlt").substring(prefsHelper.getregister(getApplicationContext(),"hlt").length()-2,prefsHelper.getregister(getApplicationContext(),"hlt").length()-1).equals("0")) {
            do {

                opcode_s = sqlitehelper.read_memory(prefsHelper.getregister(getApplicationContext(), "pc"));
                String address = prefsHelper.getregister(getApplicationContext(), "pc");
                String instruction = opcode.getinstruction(address);
                instruction_list.add(address + " : " + opcode_s + "   " + instruction);
                textview_onego.setText("T-states: " + tstates);
                textview_onego.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
                listview_onego.setSelection(adapter.getCount() - 1);
                tstates = tstates + opcode.execute();
                Log.d("Ritik", "addallinstructions: opcode is " + opcode_s);


            } while (!(opcode_s.equals("EFH")||prefsHelper.getregister(getApplicationContext(),"hlt").equals("0001H")));
        }
        button_onego.setEnabled(false);


    }

}
