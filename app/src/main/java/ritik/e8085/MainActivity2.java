package ritik.e8085;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.amlcurran.showcaseview.ShowcaseView;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {
    public static boolean flag = false;
    Button btnregmode, btnmemmode, btnassemblemode, btnsinglestep, btnonegomode, btnreset, btndevices;
    EditText editText_mem_addr, editText_assemble_addr, edittext_single_addr, edittext_onego_addr;
    PrefsHelper prefsHelper;
    TypeHelper typeHelper;
    SQLiteHelper sqLiteHelper;
    ShowcaseView sv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        prefsHelper = new PrefsHelper();
        typeHelper = new TypeHelper();
        btnregmode = (Button) (findViewById(R.id.btn_toregister));
        btnregmode.setOnClickListener(this);
        btnmemmode = (Button) (findViewById(R.id.btn_tomemory));
        btnmemmode.setOnClickListener(this);
        btnassemblemode = (Button) (findViewById(R.id.btn_toassemble));
        btnassemblemode.setOnClickListener(this);
        editText_mem_addr = (EditText) (findViewById(R.id.edittext_mem_addr));
        editText_assemble_addr = (EditText) findViewById(R.id.edittext_assemble_addr);
        btnsinglestep = (Button) findViewById(R.id.btn_tosingle);
        btnsinglestep.setOnClickListener(this);
        edittext_single_addr = (EditText) findViewById(R.id.edittext_single_addr);
        edittext_onego_addr = (EditText) findViewById(R.id.edittext_onego_addr);
        btnonegomode = (Button) findViewById(R.id.btn_toonego);
        btnonegomode.setOnClickListener(this);
        btnreset = (Button) findViewById(R.id.button_reset);
        btnreset.setOnClickListener(this);
        btndevices = (Button) findViewById(R.id.btn_todevices);
        btndevices.setOnClickListener(this);
        sqLiteHelper = new SQLiteHelper(getApplicationContext());
        prefsHelper.initialize_registers(getApplicationContext());
        edittext_single_addr.setHint(prefsHelper.getregister(getApplicationContext(), "pc"));
        edittext_onego_addr.setHint(prefsHelper.getregister(getApplicationContext(), "pc"));

//        if(prefsHelper.isFirstrun(getApplicationContext())){
//            sv=new ShowcaseView.Builder(this)
//                    .setTarget(Target.NONE)
//                    .setContentTitle("Let's get started")
//                    .setContentText("We'll guide you through the various components of the app")
//                    .setStyle(R.style.CustomShowcaseTheme3)
//                    .setOnClickListener(
//                            new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    gotoregister();
//
//
//                                }
//                            }
//
//                    )
//                    .build();
//            sv.show();
//        }


    }

    public void gotoregister() {
        startActivity(new Intent(this, Register.class));
        sv.hide();
    }

    @Override
    protected void onResume() {
        edittext_single_addr.setHint(prefsHelper.getregister(getApplicationContext(), "pc"));
        edittext_onego_addr.setHint(prefsHelper.getregister(getApplicationContext(), "pc"));
        super.onResume();

    }

    @Override
    protected void onStart() {
        edittext_single_addr.setHint(prefsHelper.getregister(getApplicationContext(), "pc"));
        edittext_onego_addr.setHint(prefsHelper.getregister(getApplicationContext(), "pc"));
        super.onStart();
    }

    @Override
    protected void onPause() {
        edittext_single_addr.setHint(prefsHelper.getregister(getApplicationContext(), "pc"));
        edittext_onego_addr.setHint(prefsHelper.getregister(getApplicationContext(), "pc"));
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_toregister:
                startActivity(new Intent(this, Register.class));
                break;
            case R.id.btn_tomemory:
                if (typeHelper.isHex(editText_mem_addr.getText().toString().toUpperCase(), 4) || editText_mem_addr.getText().toString().isEmpty()) {
                    Intent i = new Intent(this, Memory.class);
                    i.putExtra("address", editText_mem_addr.getText().toString().toUpperCase());
                    startActivity(i);
                } else
                    editText_mem_addr.setError("Invalid Address");
                break;
            case R.id.btn_toassemble:
                if (typeHelper.isHex(editText_assemble_addr.getText().toString().toUpperCase(), 4) || editText_assemble_addr.getText().toString().isEmpty()) {
                    Intent ia = new Intent(this, Assemble.class);
                    ia.putExtra("address", editText_assemble_addr.getText().toString().toUpperCase());
                    startActivity(ia);
                } else
                    editText_assemble_addr.setError("Invalid Address");
                break;
            case R.id.btn_tosingle:
                if (typeHelper.isHex(edittext_single_addr.getText().toString().toUpperCase(), 4) || edittext_single_addr.getText().toString().isEmpty()) {
                    Intent is = new Intent(this, Singlestep.class);
                    is.putExtra("address", edittext_single_addr.getText().toString().toUpperCase());
                    startActivity(is);
                } else
                    edittext_single_addr.setError("Invalid Address");
                break;
            case R.id.btn_toonego:
                if (typeHelper.isHex(edittext_onego_addr.getText().toString().toUpperCase(), 4) || edittext_onego_addr.getText().toString().isEmpty()) {
                    Intent io = new Intent(this, Onego.class);
                    io.putExtra("address", edittext_onego_addr.getText().toString().toUpperCase());
                    startActivity(io);
                } else
                    edittext_onego_addr.setError("Invalid Address");
                break;
            case R.id.button_reset:
                prefsHelper.saveregister(getApplicationContext(), prefsHelper.default_values);
                break;
            case R.id.btn_todevices:
                startActivity(new Intent(this, Devices.class));
                break;

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.main_menu_instruction:
                startActivity(new Intent(this, Instructions.class));
                return true;
            case R.id.main_menu_about:
                startActivity(new Intent(this, About.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
