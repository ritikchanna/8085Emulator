package ritik.e8085;

/**
 * Created by SuperUser on 10-03-2017.
 */

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class SQLiteListAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> address_lv;
    ArrayList<String> content_lv;
    TypeHelper typeHelper;
   SQLiteHelper SQLITEHELPER;

    Memory memory;



    public SQLiteListAdapter(
            Context context2,
            ArrayList<String> address,
            ArrayList<String> content

    )
    {

        this.context = context2;
        this.address_lv = address;
        this.content_lv = content;
        typeHelper=new TypeHelper();
        SQLITEHELPER=new SQLiteHelper(context2);
        memory=new Memory();




    }

    public int getCount() {
        // TODO Auto-generated method stub
        return address_lv.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(final int position, View child, ViewGroup parent) {

        final Holder holder;

        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.memory_listview_element, null);

            holder = new Holder();

            holder.textviewaddress = (TextView) child.findViewById(R.id.textview_address_lv);
            holder.edittextcontent = (EditText) child.findViewById(R.id.edittext_content_lv);
            holder.imagebutton=(ImageButton)child.findViewById(R.id.imagebuuton_edit);


            child.setTag(holder);

        } else {

            holder = (Holder) child.getTag();
        }
        holder.textviewaddress.setText(new String(new char[5-address_lv.get(position).length()]).replace('\0', '0')+address_lv.get(position));
        holder.edittextcontent.setText(new String(new char[3-content_lv.get(position).length()]).replace('\0', '0')+content_lv.get(position));
        child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content=holder.edittextcontent.getText().toString();
                int length=content.length();
                if(content.charAt(length-1)=='H')
                    if(typeHelper.isHex(content,2))
                holder.edittextcontent.setText(typeHelper.Cycle(holder.edittextcontent.getText().toString(),2));
                else
                    holder.edittextcontent.setError("Invalid HexaDecimal");
                else if(content.charAt(length-1)=='D')
                    if(typeHelper.isDec(content,255))
                        holder.edittextcontent.setText(typeHelper.Cycle(holder.edittextcontent.getText().toString(),2));
                    else
                        holder.edittextcontent.setError("Invalid Decimal");
                else if(content.charAt(length-1)=='B')
                    if(typeHelper.isBin(content,8))
                        holder.edittextcontent.setText(typeHelper.Cycle(holder.edittextcontent.getText().toString(),2));
                    else
                        holder.edittextcontent.setError("Invalid Binary");

            }
        });

        holder.imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.edittextcontent.isEnabled()){
                    if(holder.edittextcontent.getText().toString().length()<2)
                        holder.edittextcontent.setError("Invalid Value");
                    else if(typeHelper.isHex(holder.edittextcontent.getText().toString(),2)) {
                        SQLITEHELPER.save_memory(holder.textviewaddress.getText().toString(), holder.edittextcontent.getText().toString());
                        holder.edittextcontent.setEnabled(false);
                        content_lv.set(position, holder.edittextcontent.getText().toString());
                        notifyDataSetChanged();
//                        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.hideSoftInputFromWindow(
//                                holder.edittextcontent.getWindowToken(), 0);
                        //holder.edittextcontent.requestFocus();
                        //holder.edittextcontent.setSelection(holder.edittextcontent.length()-1);
                        holder.imagebutton.setImageResource(android.R.drawable.ic_menu_edit);
                    }
                    else if(typeHelper.isDec(holder.edittextcontent.getText().toString().toUpperCase(),255)){
                        holder.edittextcontent.setEnabled(false);
                        if(holder.edittextcontent.getText().toString().length()<4){
                            holder.edittextcontent.setText(new String(new char[4 - holder.edittextcontent.getText().toString().length()]).replace('\0', '0')+holder.edittextcontent.getText().toString());
                        }
                        SQLITEHELPER.save_memory(holder.textviewaddress.getText().toString(), typeHelper.Dec2Hex(holder.edittextcontent.getText().toString().substring(0,holder.edittextcontent.getText().toString().length()-1))+"H");
                        content_lv.set(position,typeHelper.Dec2Hex(holder.edittextcontent.getText().toString().substring(0,holder.edittextcontent.getText().toString().length()-1))+"H");
                        notifyDataSetChanged();
                        holder.imagebutton.setImageResource(android.R.drawable.ic_menu_edit);


                    }
                    else if(typeHelper.isBin(holder.edittextcontent.getText().toString().toUpperCase(),8)){
                        holder.edittextcontent.setEnabled(false);

                        SQLITEHELPER.save_memory(holder.textviewaddress.getText().toString(), typeHelper.Bin2Hex(holder.edittextcontent.getText().toString().substring(0,holder.edittextcontent.getText().toString().length()-1))+"H");
                        content_lv.set(position,typeHelper.Bin2Hex(holder.edittextcontent.getText().toString().substring(0,holder.edittextcontent.getText().toString().length()-1))+"H");
                        notifyDataSetChanged();
                        holder.imagebutton.setImageResource(android.R.drawable.ic_menu_edit);


                    }
                    else
                        holder.edittextcontent.setError("Invalid Contents");
                }
                else {
                    holder.edittextcontent.setEnabled(true);
                    holder.edittextcontent.requestFocus();

                    InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(holder.edittextcontent ,
                            InputMethodManager.SHOW_IMPLICIT);

                    holder.imagebutton.setImageResource(android.R.drawable.ic_menu_save);

                }



            }
        });

        return child;
    }

    public class Holder {
        TextView textviewaddress;
        EditText edittextcontent;
        ImageButton imagebutton;

    }



}