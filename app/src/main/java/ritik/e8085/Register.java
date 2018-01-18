package ritik.e8085;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;


public class Register extends AppCompatActivity implements View.OnClickListener {
    public static boolean flag = false;
    PrefsHelper prefsHelper = new PrefsHelper();
    TypeHelper typeHelper = new TypeHelper();
    TextView textView_reg_a, textView_reg_f, textView_reg_b, textView_reg_c, textView_reg_d, textView_reg_e, textView_reg_h, textView_reg_l, textView_reg_pc, textView_reg_sp;
    EditText editText_reg_a_data, editText_reg_f_data, editText_reg_b_data, editText_reg_c_data, editText_reg_d_data, editText_reg_e_data, editText_reg_h_data, editText_reg_l_data, editText_reg_pc_data, editText_reg_sp_data;
    ImageButton imageButton_reg_a, imageButton_reg_b, imageButton_reg_c, imageButton_reg_d, imageButton_reg_e, imageButton_reg_f, imageButton_reg_h, imageButton_reg_l, imageButton_reg_pc, imageButton_reg_sp;
    ShowcaseView sv;
    Target viewTarget;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        counter = 0;
        setContentView(R.layout.activity_register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        textView_reg_a = (TextView) (findViewById(R.id.textview_reg_a));
        textView_reg_f = (TextView) (findViewById(R.id.textview_reg_f));
        textView_reg_b = (TextView) (findViewById(R.id.textview_reg_b));
        textView_reg_c = (TextView) (findViewById(R.id.textview_reg_c));
        textView_reg_d = (TextView) (findViewById(R.id.textview_reg_d));
        textView_reg_e = (TextView) (findViewById(R.id.textview_reg_e));
        textView_reg_h = (TextView) (findViewById(R.id.textview_reg_h));
        textView_reg_l = (TextView) (findViewById(R.id.textview_reg_l));
        textView_reg_pc = (TextView) (findViewById(R.id.textview_reg_pc));
        textView_reg_sp = (TextView) (findViewById(R.id.textview_reg_sp));

        editText_reg_a_data = (EditText) (findViewById(R.id.edittext_reg_a_data));
        editText_reg_f_data = (EditText) (findViewById(R.id.edittext_reg_f_data));
        editText_reg_b_data = (EditText) (findViewById(R.id.edittext_reg_b_data));
        editText_reg_c_data = (EditText) (findViewById(R.id.edittext_reg_c_data));
        editText_reg_d_data = (EditText) (findViewById(R.id.edittext_reg_d_data));
        editText_reg_e_data = (EditText) (findViewById(R.id.edittext_reg_e_data));
        editText_reg_h_data = (EditText) (findViewById(R.id.edittext_reg_h_data));
        editText_reg_l_data = (EditText) (findViewById(R.id.edittext_reg_l_data));
        editText_reg_pc_data = (EditText) (findViewById(R.id.edittext_reg_pc_data));
        editText_reg_sp_data = (EditText) (findViewById(R.id.edittext_reg_sp_data));


        editText_reg_a_data.setText(prefsHelper.getregister(getApplicationContext(), "a"));
        editText_reg_f_data.setText(prefsHelper.getregister(getApplicationContext(), "f"));
        editText_reg_b_data.setText(prefsHelper.getregister(getApplicationContext(), "b"));
        editText_reg_c_data.setText(prefsHelper.getregister(getApplicationContext(), "c"));
        editText_reg_d_data.setText(prefsHelper.getregister(getApplicationContext(), "d"));
        editText_reg_e_data.setText(prefsHelper.getregister(getApplicationContext(), "e"));
        editText_reg_h_data.setText(prefsHelper.getregister(getApplicationContext(), "h"));
        editText_reg_l_data.setText(prefsHelper.getregister(getApplicationContext(), "l"));
        editText_reg_pc_data.setText(prefsHelper.getregister(getApplicationContext(), "pc"));
        editText_reg_sp_data.setText(prefsHelper.getregister(getApplicationContext(), "sp"));


        imageButton_reg_a = (ImageButton) findViewById(R.id.imagebutton_reg_a);
        imageButton_reg_b = (ImageButton) findViewById(R.id.imagebutton_reg_b);
        imageButton_reg_c = (ImageButton) findViewById(R.id.imagebutton_reg_c);
        imageButton_reg_d = (ImageButton) findViewById(R.id.imagebutton_reg_d);
        imageButton_reg_e = (ImageButton) findViewById(R.id.imagebutton_reg_e);
        imageButton_reg_f = (ImageButton) findViewById(R.id.imagebutton_reg_f);
        imageButton_reg_h = (ImageButton) findViewById(R.id.imagebutton_reg_h);
        imageButton_reg_l = (ImageButton) findViewById(R.id.imagebutton_reg_l);
        imageButton_reg_pc = (ImageButton) findViewById(R.id.imagebutton_reg_pc);
        imageButton_reg_sp = (ImageButton) findViewById(R.id.imagebutton_reg_sp);


        imageButton_reg_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText_reg_a_data.isEnabled()) {
                    if (editText_reg_a_data.getText().toString().length() < 2)
                        editText_reg_a_data.setError("Invalid Value");
                    else if (typeHelper.isHex(editText_reg_a_data.getText().toString().toUpperCase(), 2)) {
                        editText_reg_a_data.setEnabled(false);
                        Log.d("Ritik", "Saved value of A is: " + editText_reg_a_data.getText().toString().toUpperCase());
                        editText_reg_a_data.setText(editText_reg_a_data.getText().toString().toUpperCase());
                        imageButton_reg_a.setImageResource(android.R.drawable.ic_menu_edit);
                        prefsHelper.saveregister(Register.this, "a", editText_reg_a_data.getText().toString());
                    } else if (typeHelper.isDec(editText_reg_a_data.getText().toString().toUpperCase(), 255)) {
                        editText_reg_a_data.setEnabled(false);
                        if (editText_reg_a_data.getText().toString().length() < 4) {
                            editText_reg_a_data.setText(new String(new char[4 - editText_reg_a_data.getText().toString().length()]).replace('\0', '0') + editText_reg_a_data.getText().toString());
                        }
                        Log.d("Ritik", "Saved value of A is: " + editText_reg_a_data.getText().toString().toUpperCase());
                        editText_reg_a_data.setText(editText_reg_a_data.getText().toString().toUpperCase());
                        imageButton_reg_a.setImageResource(android.R.drawable.ic_menu_edit);
                        String temp = typeHelper.Dec2Hex(editText_reg_a_data.getText().toString().substring(0, editText_reg_a_data.getText().toString().length() - 1)) + "H";
                        temp = new String(new char[3 - temp.length()]).replace('\0', '0') + temp;
                        prefsHelper.saveregister(Register.this, "a", temp);
                    } else if (typeHelper.isBin(editText_reg_a_data.getText().toString().toUpperCase(), 8)) {
                        editText_reg_a_data.setEnabled(false);
                        Log.d("Ritik", "Saved value of A is: " + editText_reg_a_data.getText().toString().toUpperCase());
                        editText_reg_a_data.setText(editText_reg_a_data.getText().toString().toUpperCase());
                        imageButton_reg_a.setImageResource(android.R.drawable.ic_menu_edit);
                        String temp = typeHelper.Bin2Hex(editText_reg_a_data.getText().toString().substring(0, editText_reg_a_data.getText().toString().length() - 1)) + "H";
                        temp = new String(new char[3 - temp.length()]).replace('\0', '0') + temp;
                        prefsHelper.saveregister(Register.this, "a", temp);
                    } else
                        editText_reg_a_data.setError("Invalid Value");
                } else {
                    imageButton_reg_a.setImageResource(android.R.drawable.ic_menu_save);

                    editText_reg_a_data.setEnabled(true);
                    editText_reg_a_data.requestFocus();
                    editText_reg_a_data.setSelection(editText_reg_a_data.length() - 1);


//                    editText_reg_f_data.setEnabled(false);
//                    editText_reg_b_data.setEnabled(false);
//                    editText_reg_c_data.setEnabled(false);
//                    editText_reg_d_data.setEnabled(false);
//                    editText_reg_e_data.setEnabled(false);
//                    editText_reg_h_data.setEnabled(false);
//                    editText_reg_l_data.setEnabled(false);
//                    editText_reg_sp_data.setEnabled(false);
//                    editText_reg_pc_data.setEnabled(false);
                }


            }
        });
        imageButton_reg_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText_reg_f_data.isEnabled()) {
                    if (editText_reg_f_data.getText().toString().length() < 2)
                        editText_reg_f_data.setError("Invalid Value");
                    else if (typeHelper.isHex(editText_reg_f_data.getText().toString().toUpperCase(), 2)) {
                        editText_reg_f_data.setEnabled(false);
                        editText_reg_f_data.setText(editText_reg_f_data.getText().toString().toUpperCase());
                        imageButton_reg_f.setImageResource(android.R.drawable.ic_menu_edit);
                        prefsHelper.saveregister(Register.this, "f", editText_reg_f_data.getText().toString());
                    } else if (typeHelper.isDec(editText_reg_f_data.getText().toString().toUpperCase(), 255)) {
                        editText_reg_f_data.setEnabled(false);
                        if (editText_reg_f_data.getText().toString().length() < 4) {
                            editText_reg_f_data.setText(new String(new char[4 - editText_reg_f_data.getText().toString().length()]).replace('\0', '0') + editText_reg_f_data.getText().toString());
                        }
                        Log.d("Ritik", "Saved value of F is: " + editText_reg_f_data.getText().toString().toUpperCase());
                        editText_reg_f_data.setText(editText_reg_f_data.getText().toString().toUpperCase());
                        imageButton_reg_f.setImageResource(android.R.drawable.ic_menu_edit);
                        String temp = typeHelper.Dec2Hex(editText_reg_f_data.getText().toString().substring(0, editText_reg_f_data.getText().toString().length() - 1)) + "H";
                        temp = new String(new char[3 - temp.length()]).replace('\0', '0') + temp;
                        prefsHelper.saveregister(Register.this, "f", temp);
                    } else if (typeHelper.isBin(editText_reg_f_data.getText().toString().toUpperCase(), 8)) {
                        editText_reg_f_data.setEnabled(false);
                        Log.d("Ritik", "Saved value of f is: " + editText_reg_f_data.getText().toString().toUpperCase());
                        editText_reg_f_data.setText(editText_reg_f_data.getText().toString().toUpperCase());
                        imageButton_reg_f.setImageResource(android.R.drawable.ic_menu_edit);
                        String temp = typeHelper.Bin2Hex(editText_reg_f_data.getText().toString().substring(0, editText_reg_f_data.getText().toString().length() - 1)) + "H";
                        temp = new String(new char[3 - temp.length()]).replace('\0', '0') + temp;
                        prefsHelper.saveregister(Register.this, "f", temp);
                    } else
                        editText_reg_f_data.setError("Invalid Value");
                } else {
                    imageButton_reg_f.setImageResource(android.R.drawable.ic_menu_save);
                    editText_reg_f_data.setEnabled(true);
                    editText_reg_f_data.requestFocus();
                    editText_reg_f_data.setSelection(editText_reg_f_data.length() - 1);

//                    editText_reg_a_data.setEnabled(false);
//                    editText_reg_b_data.setEnabled(false);
//                    editText_reg_c_data.setEnabled(false);
//                    editText_reg_d_data.setEnabled(false);
//                    editText_reg_e_data.setEnabled(false);
//                    editText_reg_h_data.setEnabled(false);
//                    editText_reg_l_data.setEnabled(false);
//                    editText_reg_sp_data.setEnabled(false);
//                    editText_reg_pc_data.setEnabled(false);
                }


            }
        });
        imageButton_reg_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText_reg_b_data.isEnabled()) {
                    if (editText_reg_b_data.getText().toString().length() < 2)
                        editText_reg_b_data.setError("Invalid Value");
                    else if (typeHelper.isHex(editText_reg_b_data.getText().toString().toUpperCase(), 2)) {
                        editText_reg_b_data.setEnabled(false);
                        editText_reg_b_data.setText(editText_reg_b_data.getText().toString().toUpperCase());
                        imageButton_reg_b.setImageResource(android.R.drawable.ic_menu_edit);
                        prefsHelper.saveregister(Register.this, "b", editText_reg_b_data.getText().toString());
                    } else if (typeHelper.isDec(editText_reg_b_data.getText().toString().toUpperCase(), 255)) {
                        editText_reg_b_data.setEnabled(false);
                        if (editText_reg_b_data.getText().toString().length() < 4) {
                            editText_reg_b_data.setText(new String(new char[4 - editText_reg_b_data.getText().toString().length()]).replace('\0', '0') + editText_reg_b_data.getText().toString());
                        }
                        Log.d("Ritik", "Saved value of b is: " + editText_reg_b_data.getText().toString().toUpperCase());
                        editText_reg_b_data.setText(editText_reg_b_data.getText().toString().toUpperCase());
                        imageButton_reg_b.setImageResource(android.R.drawable.ic_menu_edit);
                        String temp = typeHelper.Dec2Hex(editText_reg_b_data.getText().toString().substring(0, editText_reg_b_data.getText().toString().length() - 1)) + "H";
                        temp = new String(new char[3 - temp.length()]).replace('\0', '0') + temp;
                        prefsHelper.saveregister(Register.this, "b", temp);
                    } else if (typeHelper.isBin(editText_reg_b_data.getText().toString().toUpperCase(), 8)) {
                        editText_reg_b_data.setEnabled(false);
                        Log.d("Ritik", "Saved value of b is: " + editText_reg_b_data.getText().toString().toUpperCase());
                        editText_reg_b_data.setText(editText_reg_b_data.getText().toString().toUpperCase());
                        imageButton_reg_b.setImageResource(android.R.drawable.ic_menu_edit);
                        String temp = typeHelper.Bin2Hex(editText_reg_b_data.getText().toString().substring(0, editText_reg_b_data.getText().toString().length() - 1)) + "H";
                        temp = new String(new char[3 - temp.length()]).replace('\0', '0') + temp;
                        prefsHelper.saveregister(Register.this, "b", temp);
                    } else
                        editText_reg_b_data.setError("Invalid Value");
                } else {
                    imageButton_reg_b.setImageResource(android.R.drawable.ic_menu_save);
                    editText_reg_b_data.setEnabled(true);
                    editText_reg_b_data.requestFocus();
                    editText_reg_b_data.setSelection(editText_reg_b_data.length() - 1);

//                    editText_reg_a_data.setEnabled(false);
//                    editText_reg_f_data.setEnabled(false);
//                    editText_reg_c_data.setEnabled(false);
//                    editText_reg_d_data.setEnabled(false);
//                    editText_reg_e_data.setEnabled(false);
//                    editText_reg_h_data.setEnabled(false);
//                    editText_reg_l_data.setEnabled(false);
//                    editText_reg_sp_data.setEnabled(false);
//                    editText_reg_pc_data.setEnabled(false);
                }


            }
        });
        imageButton_reg_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText_reg_c_data.isEnabled()) {
                    if (editText_reg_c_data.getText().toString().length() < 2)
                        editText_reg_c_data.setError("Invalid Value");
                    else if (typeHelper.isHex(editText_reg_c_data.getText().toString().toUpperCase(), 2)) {
                        editText_reg_c_data.setEnabled(false);
                        editText_reg_c_data.setText(editText_reg_c_data.getText().toString().toUpperCase());
                        imageButton_reg_c.setImageResource(android.R.drawable.ic_menu_edit);
                        prefsHelper.saveregister(Register.this, "c", editText_reg_c_data.getText().toString());
                    } else if (typeHelper.isDec(editText_reg_c_data.getText().toString().toUpperCase(), 255)) {
                        editText_reg_c_data.setEnabled(false);
                        if (editText_reg_c_data.getText().toString().length() < 4) {
                            editText_reg_c_data.setText(new String(new char[4 - editText_reg_c_data.getText().toString().length()]).replace('\0', '0') + editText_reg_c_data.getText().toString());
                        }
                        Log.d("Ritik", "Saved value of c is: " + editText_reg_c_data.getText().toString().toUpperCase());
                        editText_reg_c_data.setText(editText_reg_c_data.getText().toString().toUpperCase());
                        imageButton_reg_c.setImageResource(android.R.drawable.ic_menu_edit);
                        String temp = typeHelper.Dec2Hex(editText_reg_c_data.getText().toString().substring(0, editText_reg_c_data.getText().toString().length() - 1)) + "H";
                        temp = new String(new char[3 - temp.length()]).replace('\0', '0') + temp;
                        prefsHelper.saveregister(Register.this, "c", temp);
                    } else if (typeHelper.isBin(editText_reg_c_data.getText().toString().toUpperCase(), 8)) {
                        editText_reg_c_data.setEnabled(false);
                        Log.d("Ritik", "Saved value of c is: " + editText_reg_c_data.getText().toString().toUpperCase());
                        editText_reg_c_data.setText(editText_reg_c_data.getText().toString().toUpperCase());
                        imageButton_reg_c.setImageResource(android.R.drawable.ic_menu_edit);
                        String temp = typeHelper.Bin2Hex(editText_reg_c_data.getText().toString().substring(0, editText_reg_c_data.getText().toString().length() - 1)) + "H";
                        temp = new String(new char[3 - temp.length()]).replace('\0', '0') + temp;
                        prefsHelper.saveregister(Register.this, "c", temp);
                    } else
                        editText_reg_c_data.setError("Invalid Value");
                } else {
                    imageButton_reg_c.setImageResource(android.R.drawable.ic_menu_save);
                    editText_reg_c_data.setEnabled(true);
                    editText_reg_c_data.requestFocus();
                    editText_reg_c_data.setSelection(editText_reg_c_data.length() - 1);
//                    editText_reg_a_data.setEnabled(false);
//                    editText_reg_f_data.setEnabled(false);
//                    editText_reg_b_data.setEnabled(false);
//                    editText_reg_d_data.setEnabled(false);
//                    editText_reg_e_data.setEnabled(false);
//                    editText_reg_h_data.setEnabled(false);
//                    editText_reg_l_data.setEnabled(false);
//                    editText_reg_sp_data.setEnabled(false);
//                    editText_reg_pc_data.setEnabled(false);
                }


            }
        });
        imageButton_reg_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText_reg_d_data.isEnabled()) {
                    if (editText_reg_d_data.getText().toString().length() < 2)
                        editText_reg_d_data.setError("Invalid Value");
                    else if (typeHelper.isHex(editText_reg_d_data.getText().toString().toUpperCase(), 2)) {
                        editText_reg_d_data.setEnabled(false);
                        editText_reg_d_data.setText(editText_reg_d_data.getText().toString().toUpperCase());
                        imageButton_reg_d.setImageResource(android.R.drawable.ic_menu_edit);
                        prefsHelper.saveregister(Register.this, "d", editText_reg_d_data.getText().toString());
                    } else if (typeHelper.isDec(editText_reg_d_data.getText().toString().toUpperCase(), 255)) {
                        editText_reg_d_data.setEnabled(false);
                        if (editText_reg_d_data.getText().toString().length() < 4) {
                            editText_reg_d_data.setText(new String(new char[4 - editText_reg_d_data.getText().toString().length()]).replace('\0', '0') + editText_reg_d_data.getText().toString());
                        }
                        Log.d("Ritik", "Saved value of D is: " + editText_reg_d_data.getText().toString().toUpperCase());
                        editText_reg_d_data.setText(editText_reg_d_data.getText().toString().toUpperCase());
                        imageButton_reg_d.setImageResource(android.R.drawable.ic_menu_edit);
                        String temp = typeHelper.Dec2Hex(editText_reg_d_data.getText().toString().substring(0, editText_reg_d_data.getText().toString().length() - 1)) + "H";
                        temp = new String(new char[3 - temp.length()]).replace('\0', '0') + temp;
                        prefsHelper.saveregister(Register.this, "d", temp);
                    } else if (typeHelper.isBin(editText_reg_d_data.getText().toString().toUpperCase(), 8)) {
                        editText_reg_d_data.setEnabled(false);
                        Log.d("Ritik", "Saved value of D is: " + editText_reg_d_data.getText().toString().toUpperCase());
                        editText_reg_d_data.setText(editText_reg_d_data.getText().toString().toUpperCase());
                        imageButton_reg_d.setImageResource(android.R.drawable.ic_menu_edit);
                        String temp = typeHelper.Bin2Hex(editText_reg_d_data.getText().toString().substring(0, editText_reg_d_data.getText().toString().length() - 1)) + "H";
                        temp = new String(new char[3 - temp.length()]).replace('\0', '0') + temp;
                        prefsHelper.saveregister(Register.this, "d", temp);
                    } else
                        editText_reg_d_data.setError("Invalid Value");
                } else {
                    imageButton_reg_d.setImageResource(android.R.drawable.ic_menu_save);
                    editText_reg_d_data.setEnabled(true);
                    editText_reg_d_data.requestFocus();
                    editText_reg_d_data.setSelection(editText_reg_d_data.length() - 1);
//                editText_reg_a_data.setEnabled(false);
//                editText_reg_f_data.setEnabled(false);
//                editText_reg_b_data.setEnabled(false);
//                editText_reg_c_data.setEnabled(false);
//                editText_reg_e_data.setEnabled(false);
//                editText_reg_h_data.setEnabled(false);
//                editText_reg_l_data.setEnabled(false);
//                editText_reg_sp_data.setEnabled(false);
//                editText_reg_pc_data.setEnabled(false);
                }


            }
        });
        imageButton_reg_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText_reg_e_data.isEnabled()) {
                    if (editText_reg_e_data.getText().toString().length() < 2)
                        editText_reg_e_data.setError("Invalid Value");
                    else if (typeHelper.isHex(editText_reg_e_data.getText().toString().toUpperCase(), 2)) {
                        editText_reg_e_data.setEnabled(false);
                        editText_reg_e_data.setText(editText_reg_e_data.getText().toString().toUpperCase());
                        imageButton_reg_e.setImageResource(android.R.drawable.ic_menu_edit);
                        prefsHelper.saveregister(Register.this, "e", editText_reg_e_data.getText().toString());
                    } else if (typeHelper.isDec(editText_reg_e_data.getText().toString().toUpperCase(), 255)) {
                        editText_reg_e_data.setEnabled(false);
                        if (editText_reg_e_data.getText().toString().length() < 4) {
                            editText_reg_e_data.setText(new String(new char[4 - editText_reg_e_data.getText().toString().length()]).replace('\0', '0') + editText_reg_e_data.getText().toString());
                        }
                        Log.d("Ritik", "Saved value of E is: " + editText_reg_e_data.getText().toString().toUpperCase());
                        editText_reg_e_data.setText(editText_reg_e_data.getText().toString().toUpperCase());
                        imageButton_reg_e.setImageResource(android.R.drawable.ic_menu_edit);
                        String temp = typeHelper.Dec2Hex(editText_reg_e_data.getText().toString().substring(0, editText_reg_e_data.getText().toString().length() - 1)) + "H";
                        temp = new String(new char[3 - temp.length()]).replace('\0', '0') + temp;
                        prefsHelper.saveregister(Register.this, "e", temp);
                    } else if (typeHelper.isBin(editText_reg_e_data.getText().toString().toUpperCase(), 8)) {
                        editText_reg_e_data.setEnabled(false);
                        Log.d("Ritik", "Saved value of e is: " + editText_reg_e_data.getText().toString().toUpperCase());
                        editText_reg_e_data.setText(editText_reg_e_data.getText().toString().toUpperCase());
                        imageButton_reg_e.setImageResource(android.R.drawable.ic_menu_edit);
                        String temp = typeHelper.Bin2Hex(editText_reg_e_data.getText().toString().substring(0, editText_reg_e_data.getText().toString().length() - 1)) + "H";
                        temp = new String(new char[3 - temp.length()]).replace('\0', '0') + temp;
                        prefsHelper.saveregister(Register.this, "e", temp);
                    } else
                        editText_reg_e_data.setError("Invalid Value");
                } else {
                    imageButton_reg_e.setImageResource(android.R.drawable.ic_menu_save);
                    editText_reg_e_data.setEnabled(true);
                    editText_reg_e_data.requestFocus();
                    editText_reg_e_data.setSelection(editText_reg_e_data.length() - 1);
//                editText_reg_a_data.setEnabled(false);
//                editText_reg_f_data.setEnabled(false);
//                editText_reg_b_data.setEnabled(false);
//                editText_reg_c_data.setEnabled(false);
//                editText_reg_d_data.setEnabled(false);
//                editText_reg_h_data.setEnabled(false);
//                editText_reg_l_data.setEnabled(false);
//                editText_reg_sp_data.setEnabled(false);
//                editText_reg_pc_data.setEnabled(false);
                }


            }
        });
        imageButton_reg_h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText_reg_h_data.isEnabled()) {
                    if (editText_reg_h_data.getText().toString().length() < 2)
                        editText_reg_h_data.setError("Invalid Value");
                    else if (typeHelper.isHex(editText_reg_h_data.getText().toString().toUpperCase(), 2)) {
                        editText_reg_h_data.setEnabled(false);
                        editText_reg_h_data.setText(editText_reg_h_data.getText().toString().toUpperCase());
                        imageButton_reg_h.setImageResource(android.R.drawable.ic_menu_edit);
                        prefsHelper.saveregister(Register.this, "h", editText_reg_h_data.getText().toString());
                    } else if (typeHelper.isDec(editText_reg_h_data.getText().toString().toUpperCase(), 255)) {
                        editText_reg_h_data.setEnabled(false);
                        if (editText_reg_h_data.getText().toString().length() < 4) {
                            editText_reg_h_data.setText(new String(new char[4 - editText_reg_h_data.getText().toString().length()]).replace('\0', '0') + editText_reg_h_data.getText().toString());
                        }
                        Log.d("Ritik", "Saved value of F is: " + editText_reg_h_data.getText().toString().toUpperCase());
                        editText_reg_h_data.setText(editText_reg_h_data.getText().toString().toUpperCase());
                        imageButton_reg_h.setImageResource(android.R.drawable.ic_menu_edit);
                        String temp = typeHelper.Dec2Hex(editText_reg_h_data.getText().toString().substring(0, editText_reg_h_data.getText().toString().length() - 1)) + "H";
                        temp = new String(new char[3 - temp.length()]).replace('\0', '0') + temp;
                        prefsHelper.saveregister(Register.this, "h", temp);

                    } else if (typeHelper.isBin(editText_reg_h_data.getText().toString().toUpperCase(), 8)) {
                        editText_reg_h_data.setEnabled(false);
                        Log.d("Ritik", "Saved value of f is: " + editText_reg_h_data.getText().toString().toUpperCase());
                        editText_reg_h_data.setText(editText_reg_h_data.getText().toString().toUpperCase());
                        imageButton_reg_h.setImageResource(android.R.drawable.ic_menu_edit);
                        String temp = typeHelper.Bin2Hex(editText_reg_h_data.getText().toString().substring(0, editText_reg_h_data.getText().toString().length() - 1)) + "H";
                        temp = new String(new char[3 - temp.length()]).replace('\0', '0') + temp;
                        prefsHelper.saveregister(Register.this, "h", temp);
                    } else
                        editText_reg_h_data.setError("Invalid Value");
                } else {
                    imageButton_reg_h.setImageResource(android.R.drawable.ic_menu_save);
                    editText_reg_h_data.setEnabled(true);
                    editText_reg_h_data.requestFocus();
                    editText_reg_h_data.setSelection(editText_reg_h_data.length() - 1);
//                editText_reg_a_data.setEnabled(false);
//                editText_reg_f_data.setEnabled(false);
//                editText_reg_b_data.setEnabled(false);
//                editText_reg_c_data.setEnabled(false);
//                editText_reg_d_data.setEnabled(false);
//                editText_reg_e_data.setEnabled(false);
//                editText_reg_l_data.setEnabled(false);
//                editText_reg_sp_data.setEnabled(false);
//                editText_reg_pc_data.setEnabled(false);
                }


            }
        });
        imageButton_reg_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText_reg_l_data.isEnabled()) {
                    if (editText_reg_l_data.getText().toString().length() < 2)
                        editText_reg_l_data.setError("Invalid Value");
                    else if (typeHelper.isHex(editText_reg_l_data.getText().toString().toUpperCase(), 2)) {
                        editText_reg_l_data.setEnabled(false);
                        editText_reg_l_data.setText(editText_reg_l_data.getText().toString().toUpperCase());
                        imageButton_reg_l.setImageResource(android.R.drawable.ic_menu_edit);
                        prefsHelper.saveregister(Register.this, "l", editText_reg_l_data.getText().toString());
                    } else if (typeHelper.isDec(editText_reg_l_data.getText().toString().toUpperCase(), 255)) {
                        editText_reg_l_data.setEnabled(false);
                        if (editText_reg_l_data.getText().toString().length() < 4) {
                            editText_reg_l_data.setText(new String(new char[4 - editText_reg_l_data.getText().toString().length()]).replace('\0', '0') + editText_reg_l_data.getText().toString());
                        }
                        Log.d("Ritik", "Saved value of l is: " + editText_reg_l_data.getText().toString().toUpperCase());
                        editText_reg_l_data.setText(editText_reg_l_data.getText().toString().toUpperCase());
                        imageButton_reg_l.setImageResource(android.R.drawable.ic_menu_edit);
                        String temp = typeHelper.Dec2Hex(editText_reg_l_data.getText().toString().substring(0, editText_reg_l_data.getText().toString().length() - 1)) + "H";
                        temp = new String(new char[3 - temp.length()]).replace('\0', '0') + temp;
                        prefsHelper.saveregister(Register.this, "l", temp);

                    } else if (typeHelper.isBin(editText_reg_l_data.getText().toString().toUpperCase(), 8)) {
                        editText_reg_l_data.setEnabled(false);
                        Log.d("Ritik", "Saved value of l is: " + editText_reg_l_data.getText().toString().toUpperCase());
                        editText_reg_l_data.setText(editText_reg_l_data.getText().toString().toUpperCase());
                        imageButton_reg_l.setImageResource(android.R.drawable.ic_menu_edit);
                        String temp = typeHelper.Bin2Hex(editText_reg_l_data.getText().toString().substring(0, editText_reg_l_data.getText().toString().length() - 1)) + "H";
                        temp = new String(new char[3 - temp.length()]).replace('\0', '0') + temp;
                        prefsHelper.saveregister(Register.this, "l", temp);
                    } else
                        editText_reg_l_data.setError("Invalid Value");
                } else {

                    imageButton_reg_l.setImageResource(android.R.drawable.ic_menu_save);
                    editText_reg_l_data.setEnabled(true);
                    editText_reg_l_data.requestFocus();
                    editText_reg_l_data.setSelection(editText_reg_l_data.length() - 1);
//                    editText_reg_a_data.setEnabled(false);
//                    editText_reg_f_data.setEnabled(false);
//                    editText_reg_b_data.setEnabled(false);
//                    editText_reg_c_data.setEnabled(false);
//                    editText_reg_d_data.setEnabled(false);
//                    editText_reg_e_data.setEnabled(false);
//                    editText_reg_h_data.setEnabled(false);
//                    editText_reg_sp_data.setEnabled(false);
//                    editText_reg_pc_data.setEnabled(false);
                }

            }
        });
        imageButton_reg_pc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText_reg_pc_data.isEnabled()) {
                    if (editText_reg_pc_data.getText().toString().length() < 2)
                        editText_reg_pc_data.setError("Invalid Value");
                    else if (typeHelper.isHex(editText_reg_pc_data.getText().toString().toUpperCase(), 4)) {
                        editText_reg_pc_data.setEnabled(false);
                        editText_reg_pc_data.setText(editText_reg_pc_data.getText().toString().toUpperCase());
                        imageButton_reg_pc.setImageResource(android.R.drawable.ic_menu_edit);
                        prefsHelper.saveregister(getApplicationContext(), "pc", editText_reg_pc_data.getText().toString().toUpperCase());
                    } else if (typeHelper.isDec(editText_reg_pc_data.getText().toString().toUpperCase(), 65535)) {
                        editText_reg_pc_data.setEnabled(false);
                        if (editText_reg_pc_data.getText().toString().length() < 6) {
                            editText_reg_pc_data.setText(new String(new char[6 - editText_reg_pc_data.getText().toString().length()]).replace('\0', '0') + editText_reg_pc_data.getText().toString());
                        }
                        Log.d("Ritik", "Saved value of pc is: " + editText_reg_pc_data.getText().toString().toUpperCase());
                        editText_reg_pc_data.setText(editText_reg_pc_data.getText().toString().toUpperCase());
                        imageButton_reg_pc.setImageResource(android.R.drawable.ic_menu_edit);
                        String temp = typeHelper.Dec2Hex(editText_reg_pc_data.getText().toString().substring(0, editText_reg_pc_data.getText().toString().length() - 1)) + "H";
                        temp = new String(new char[5 - temp.length()]).replace('\0', '0') + temp;
                        prefsHelper.saveregister(Register.this, "pc", temp);
                    } else if (typeHelper.isBin(editText_reg_pc_data.getText().toString().toUpperCase(), 16)) {
                        editText_reg_pc_data.setEnabled(false);
                        Log.d("Ritik", "Saved value of f is: " + editText_reg_pc_data.getText().toString().toUpperCase());
                        editText_reg_pc_data.setText(editText_reg_pc_data.getText().toString().toUpperCase());
                        imageButton_reg_pc.setImageResource(android.R.drawable.ic_menu_edit);
                        String temp = typeHelper.Bin2Hex(editText_reg_pc_data.getText().toString().substring(0, editText_reg_pc_data.getText().toString().length() - 1)) + "H";
                        temp = new String(new char[5 - temp.length()]).replace('\0', '0') + temp;
                        prefsHelper.saveregister(Register.this, "pc", temp);
                    } else
                        editText_reg_pc_data.setError("Invalid Value");
                } else {
                    imageButton_reg_pc.setImageResource(android.R.drawable.ic_menu_save);
                    editText_reg_pc_data.setEnabled(true);
                    editText_reg_pc_data.requestFocus();
                    editText_reg_pc_data.setSelection(editText_reg_pc_data.length() - 1);
//                editText_reg_a_data.setEnabled(false);
//                editText_reg_f_data.setEnabled(false);
//                editText_reg_b_data.setEnabled(false);
//                editText_reg_c_data.setEnabled(false);
//                editText_reg_d_data.setEnabled(false);
//                editText_reg_e_data.setEnabled(false);
//                editText_reg_h_data.setEnabled(false);
//                editText_reg_l_data.setEnabled(false);
//                editText_reg_sp_data.setEnabled(false);
                }

            }
        });
        imageButton_reg_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText_reg_sp_data.isEnabled()) {
                    if (editText_reg_sp_data.getText().toString().length() < 2)
                        editText_reg_sp_data.setError("Invalid Value");
                    else if (typeHelper.isHex(editText_reg_sp_data.getText().toString().toUpperCase(), 4)) {
                        editText_reg_sp_data.setEnabled(false);
                        editText_reg_sp_data.setText(editText_reg_sp_data.getText().toString().toUpperCase());
                        imageButton_reg_sp.setImageResource(android.R.drawable.ic_menu_edit);
                        prefsHelper.saveregister(Register.this, "sp", editText_reg_sp_data.getText().toString());
                    } else if (typeHelper.isDec(editText_reg_sp_data.getText().toString().toUpperCase(), 65535)) {
                        editText_reg_sp_data.setEnabled(false);
                        if (editText_reg_sp_data.getText().toString().length() < 6) {
                            editText_reg_sp_data.setText(new String(new char[6 - editText_reg_sp_data.getText().toString().length()]).replace('\0', '0') + editText_reg_sp_data.getText().toString());
                        }
                        Log.d("Ritik", "Saved value of sp is: " + editText_reg_sp_data.getText().toString().toUpperCase());
                        editText_reg_sp_data.setText(editText_reg_sp_data.getText().toString().toUpperCase());
                        imageButton_reg_sp.setImageResource(android.R.drawable.ic_menu_edit);
                        Log.d("Ritik", "onClick: " + editText_reg_sp_data.getText().toString());
                        String temp = typeHelper.Dec2Hex(editText_reg_sp_data.getText().toString().substring(0, editText_reg_sp_data.getText().toString().length() - 1)) + "H";
                        temp = new String(new char[5 - temp.length()]).replace('\0', '0') + temp;
                        prefsHelper.saveregister(Register.this, "sp", temp);

                    } else if (typeHelper.isBin(editText_reg_sp_data.getText().toString().toUpperCase(), 16)) {
                        editText_reg_sp_data.setEnabled(false);
                        Log.d("Ritik", "Saved value of f is: " + editText_reg_sp_data.getText().toString().toUpperCase());
                        editText_reg_sp_data.setText(editText_reg_sp_data.getText().toString().toUpperCase());
                        imageButton_reg_sp.setImageResource(android.R.drawable.ic_menu_edit);
                        String temp = typeHelper.Bin2Hex(editText_reg_sp_data.getText().toString().substring(0, editText_reg_sp_data.getText().toString().length() - 1)) + "H";
                        temp = new String(new char[5 - temp.length()]).replace('\0', '0') + temp;
                        prefsHelper.saveregister(Register.this, "sp", temp);
                    } else
                        editText_reg_sp_data.setError("Invalid Value");
                } else {
                    imageButton_reg_sp.setImageResource(android.R.drawable.ic_menu_save);
                    editText_reg_sp_data.setEnabled(true);
                    editText_reg_sp_data.requestFocus();
                    editText_reg_sp_data.setSelection(editText_reg_sp_data.length() - 1);
//                editText_reg_a_data.setEnabled(false);
//                editText_reg_f_data.setEnabled(false);
//                editText_reg_b_data.setEnabled(false);
//                editText_reg_c_data.setEnabled(false);
//                editText_reg_d_data.setEnabled(false);
//                editText_reg_e_data.setEnabled(false);
//                editText_reg_h_data.setEnabled(false);
//                editText_reg_l_data.setEnabled(false);
//                editText_reg_pc_data.setEnabled(false);
                }


            }
        });
        textView_reg_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText_reg_a_data.setText(typeHelper.Cycle(editText_reg_a_data.getText().toString(), 2));
            }
        });
        textView_reg_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText_reg_f_data.setText(typeHelper.Cycle(editText_reg_f_data.getText().toString(), 2));
            }
        });
        textView_reg_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText_reg_b_data.setText(typeHelper.Cycle(editText_reg_b_data.getText().toString(), 2));
            }
        });
        textView_reg_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText_reg_c_data.setText(typeHelper.Cycle(editText_reg_c_data.getText().toString(), 2));
            }
        });
        textView_reg_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText_reg_d_data.setText(typeHelper.Cycle(editText_reg_d_data.getText().toString(), 2));
            }
        });
        textView_reg_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText_reg_e_data.setText(typeHelper.Cycle(editText_reg_e_data.getText().toString(), 2));
            }
        });
        textView_reg_h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText_reg_h_data.setText(typeHelper.Cycle(editText_reg_h_data.getText().toString(), 2));
            }
        });
        textView_reg_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText_reg_l_data.setText(typeHelper.Cycle(editText_reg_l_data.getText().toString(), 2));
            }
        });
        textView_reg_pc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText_reg_pc_data.setText(typeHelper.Cycle(editText_reg_pc_data.getText().toString(), 4));
            }
        });
        textView_reg_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText_reg_sp_data.setText(typeHelper.Cycle(editText_reg_sp_data.getText().toString(), 4));
            }
        });
        counter = 0;
        viewTarget = new ViewTarget(R.id.textview_reg_c, this);

//        if(prefsHelper.isFirstrun(getApplicationContext())) {
//        sv=new ShowcaseView.Builder(this)
//                .setTarget(Target.NONE)
//                .setContentTitle("Register Mode")
//                .setContentText("This screen shows register values of 8085")
//                .setStyle(R.style.CustomShowcaseTheme3)
//                .setOnClickListener(
//                        new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                nextshowcase();
//                            }
//                        }
//
//                )
//                .build();
//        sv.show();
//
//        }


    }
//    public void nextshowcase(){
//        Log.d("Ritik", "nextshowcase: ");
//        switch (counter) {
//            case 0:
//                sv.setShowcase(new ViewTarget(textView_reg_c),true);
//                sv.setContentTitle("Change Radix of register");
//                sv.setContentText("Tap on register name to change the radix of the register");
//                break;
//            case 1:
//                sv.setShowcase(new ViewTarget(textView_reg_h), true);
//                sv.setContentTitle("Edit value of register");
//                sv.setContentText("Long press on register name to edit it value . Make sure you input a hexadecimal number");
//                break;
//            case 2:
//
//                sv.setContentTitle("Save value of register");
//                sv.setContentText("Long press again register name to save its value");
//                break;
//            case 3:
//
//                sv.hide();
//                //startActivity(new Intent(this,Assemble.class));
//                Log.d("Ritik", "nextshowcase: case 3");
//                startActivity(new Intent(this,Assemble.class));
//                Log.d("Ritik", "nextshowcase: case 3 not what you think");
//                finish();
//                //prefsHelper.firstrun(getApplicationContext());
//                break;
//
//
//        }
//        counter++;
//
//    }


//    @Override
//    protected void onDestroy() {
//        String[] reg_values={editText_reg_a_data.getText().toString(),
//                            editText_reg_f_data.getText().toString(),
//                            editText_reg_b_data.getText().toString(),
//                            editText_reg_c_data.getText().toString(),
//                            editText_reg_d_data.getText().toString(),
//                            editText_reg_e_data.getText().toString(),
//                            editText_reg_h_data.getText().toString(),
//                            editText_reg_l_data.getText().toString(),
//                            editText_reg_pc_data.getText().toString(),
//                            editText_reg_sp_data.getText().toString()};
//        reg_values=typeHelper.HexanArray(reg_values);
//        prefsHelper.saveregister(getApplicationContext(),reg_values);
//        super.onDestroy();
//    }


    @Override
    public void onClick(View view) {

    }
}
