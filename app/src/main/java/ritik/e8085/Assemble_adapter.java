package ritik.e8085;

/**
 * Created by SuperUser on 12-03-2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class Assemble_adapter extends BaseAdapter {

    public ArrayList<String> content_lv;
    Context context;
    ArrayList<String> address_lv;
    ArrayList<Integer> size_instruction;
    TypeHelper typeHelper;
    InstructionDbHelper instructionDbHelper;
    private SQLiteHelper memoryhelper;


    public Assemble_adapter(
            Context context2,
            ArrayList<String> address,
            ArrayList<String> content


    ) {

        this.context = context2;
        this.address_lv = address;
        this.content_lv = content;
        this.size_instruction = new ArrayList<>();
        instructionDbHelper = new InstructionDbHelper(context2);
        typeHelper = new TypeHelper();
        memoryhelper = new SQLiteHelper(context);
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


        Log.d("Ritik", "Assemble_adapter: ritik " + instructionDbHelper.getInstructionFull("MOV A, B").getInt(1));

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

    public View getView(final int position, View child, final ViewGroup parent) {

        final Holder holder;

        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.assemble_listview_element, null);

            holder = new Holder();

            holder.textviewaddress = child.findViewById(R.id.textview_address_lv_assemble);
            holder.edittextcontent = child.findViewById(R.id.edittext_content_lv_assemble);
            holder.buttondone = child.findViewById(R.id.button_done_lv_assemble);
            holder.buttonhelp = child.findViewById(R.id.button_help_lv_assemble);


            child.setTag(holder);

        } else {

            holder = (Holder) child.getTag();
        }
        try {
            holder.textviewaddress.setText(address_lv.get(position).toUpperCase());
            Log.d("Ritik", "getView:rit " + address_lv.size() + "  " + position);
            if (address_lv.size() - 1 == position) {
                Log.d("Ritik", "requesting focus edittext in assemble_adapter: " + address_lv.size() + " " + position);


                holder.edittextcontent.requestFocus();
            } else holder.edittextcontent.clearFocus();

            if (content_lv.size() > position)
                holder.edittextcontent.setText(content_lv.get(position).toUpperCase());
            else
                holder.edittextcontent.setText("");
        } catch (Exception e) {
        }


        holder.buttonhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, Instructions.class);
                i.putExtra("position", position);
                Log.d("Ritik", "onClick: this is cool");
                try {
                    ((Activity) context).startActivityForResult(i, 89);
                } catch (Exception e) {
                    Log.d("Ritik", "got him here: " + e.getMessage());
                }
            }
        });

        holder.buttondone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.edittextcontent.setError(null);
                if (holder.edittextcontent.getText().toString().length() != 0 && instructionDbHelper.isInstruction(holder.edittextcontent.getText().toString().toUpperCase())) {
                    holder.edittextcontent.clearFocus();
                    if (content_lv.size() != position)
                        content_lv.remove(position);
                    content_lv.add(position, holder.edittextcontent.getText().toString());
                    Log.d("Ritik", "onClick: a");
                    int size = instructionDbHelper.getsize(holder.edittextcontent.getText().toString());
                    if (size_instruction.size() != position)
                        size_instruction.remove(position);
                    size_instruction.add(position, size);
                    Log.d("Ritik", "onClick: c");
                    int current_addr = Integer.parseInt(typeHelper.Hex2Dec(address_lv.get(position).substring(0, address_lv.get(position).length() - 1)));
                    int next_addr = current_addr + size_instruction.get(position);
                    if (address_lv.size() != position + 1)
                        address_lv.remove(position + 1);
                    Log.d("Ritik", "onClick: before " + address_lv.size() + " " + position);
                    address_lv.add(position + 1, new String(new char[4 - typeHelper.Dec2Hex(Integer.toString(next_addr)).length()]).replace('\0', '0') + typeHelper.Dec2Hex(Integer.toString(next_addr)) + "H");
                    Log.d("Ritik", "onClick: b");
                    String instruction = holder.edittextcontent.getText().toString();
                    if (size_instruction.get(position) == 1) {
                        Cursor cursor = instructionDbHelper.getInstruction(instruction);
                        memoryhelper.save_memory(address_lv.get(position), cursor.getString(0) + "H");
                    } else if (size_instruction.get(position) == 2) {
                        String data_byte = instruction.substring(instruction.lastIndexOf(" ") + 1);
                        instruction = instruction.substring(0, instruction.length() - 4);
                        Cursor cursor = instructionDbHelper.getInstruction(instruction);
                        memoryhelper.save_memory(address_lv.get(position), cursor.getString(0) + "H");
                        int current_address = Integer.parseInt(typeHelper.Hex2Dec(address_lv.get(position).substring(0, address_lv.get(position).length() - 1)));
                        String next_address = typeHelper.Dec2Hex(Integer.toString(current_address + 1)) + "H";
                        memoryhelper.save_memory(new String(new char[5 - next_address.length()]).replace('\0', '0') + next_address, data_byte.toUpperCase());
                    } else if (size_instruction.get(position) == 3) {
                        String data_byte = instruction.substring(instruction.lastIndexOf(" ") + 1);
                        instruction = instruction.substring(0, instruction.length() - 6);
                        Cursor cursor = instructionDbHelper.getInstruction(instruction);
                        memoryhelper.save_memory(address_lv.get(position), cursor.getString(0) + "H");
                        String data_byte_1 = data_byte.substring(0, data_byte.length() - 3) + "H";
                        String data_byte_2 = data_byte.substring(2, data_byte.length());
                        int current_address = Integer.parseInt(typeHelper.Hex2Dec(address_lv.get(position).substring(0, address_lv.get(position).length() - 1)));
                        String next_address = typeHelper.Dec2Hex(Integer.toString(current_address + 1)) + "H";
                        memoryhelper.save_memory(new String(new char[5 - next_address.length()]).replace('\0', '0') + next_address, data_byte_2.toUpperCase()); //due to little endian style
                        next_address = typeHelper.Dec2Hex(Integer.toString(current_address + 2)) + "H";
                        memoryhelper.save_memory(new String(new char[5 - next_address.length()]).replace('\0', '0') + next_address, data_byte_1.toUpperCase());
                    }
                    notifyDataSetChanged();


                } else
                    holder.edittextcontent.setError("Invalid Instruction");
            }
        });


        return child;
    }

    public void setContent(int position, String Content) {
        Log.d("Ritik", "setContent: " + position + " " + Content);
        if (position < content_lv.size()) {
            content_lv.set(position, Content);
        } else {
            content_lv.add(Content);
        }
        notifyDataSetChanged();
    }

    public class Holder {
        TextView textviewaddress;
        EditText edittextcontent;
        ImageButton buttondone, buttonhelp;

    }


}