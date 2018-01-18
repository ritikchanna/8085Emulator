package ritik.e8085;

/**
 * Created by SuperUser on 12-03-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Grid extends BaseAdapter {

    Context context;
    String[] colors;
    int[] edittext_enable;
    String[] names;
    TypeHelper typeHelper;
    InstructionDbHelper instructionDbHelper;
    SQLiteHelper memoryhelper;
    PrefsHelper prefsHelper;


    public Grid(
            Context context,
            String[] colors,
            int[] edittext_enable,
            String[] names


    ) {

        this.context = context;
        this.colors = colors;
        this.edittext_enable = edittext_enable;
        this.names = names;
        prefsHelper = new PrefsHelper();
        typeHelper = new TypeHelper();

    }

    public int getCount() {
        // TODO Auto-generated method stub
        return names.length;
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

//        if (child == null) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        child = layoutInflater.inflate(R.layout.grid_layout, null);

        holder = new Holder();

        holder.name = (TextView) child.findViewById(R.id.textView5);
        holder.data = (EditText) child.findViewById(R.id.editText);


        child.setTag(holder);

//        } else {
//
//            holder = (Holder) child.getTag();
//        }
        try {
            holder.name.setText(names[position].toUpperCase());
            if (edittext_enable[position] == 0)
                holder.data.setVisibility(View.INVISIBLE);


        } catch (Exception e) {
        }
        child.setBackgroundColor(Color.parseColor(colors[position]));
        if (names[position].equals("Memory Mode")) {
            holder.data.setHint("0000H");
        } else if (names[position].equals("Assemble Mode")) {
            holder.data.setHint("2000H");
        } else {
            holder.data.setHint(prefsHelper.getregister(context, "pc"));
        }


        child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (names[position]) {
                    case "Register Mode":
                        context.startActivity(new Intent(context, Register.class));
                        break;
                    case "Memory Mode":
                        if (typeHelper.isHex(holder.data.getText().toString().toUpperCase(), 4) || holder.data.getText().toString().isEmpty()) {
                            Intent i = new Intent(context, Memory.class);
                            i.putExtra("address", holder.data.getText().toString().toUpperCase());
                            context.startActivity(i);
                        } else
                            holder.data.setError("Invalid Address");
                        break;
                    case "Assemble Mode":
                        if (typeHelper.isHex(holder.data.getText().toString().toUpperCase(), 4) || holder.data.getText().toString().isEmpty()) {
                            Intent ia = new Intent(context, Assemble.class);
                            ia.putExtra("address", holder.data.getText().toString().toUpperCase());
                            context.startActivity(ia);
                        } else
                            holder.data.setError("Invalid Address");
                        break;
                    case "Single Step Execution":
                        if (typeHelper.isHex(holder.data.getText().toString().toUpperCase(), 4) || holder.data.getText().toString().isEmpty()) {
                            Intent is = new Intent(context, Singlestep.class);
                            is.putExtra("address", holder.data.getText().toString().toUpperCase());
                            context.startActivity(is);
                        } else
                            holder.data.setError("Invalid Address");
                        break;
                    case "One Go Execution":
                        if (typeHelper.isHex(holder.data.getText().toString().toUpperCase(), 4) || holder.data.getText().toString().isEmpty()) {
                            Intent io = new Intent(context, Onego.class);
                            io.putExtra("address", holder.data.getText().toString().toUpperCase());
                            context.startActivity(io);
                        } else
                            holder.data.setError("Invalid Address");
                        break;
                    case "Devices (Beta)":
                        context.startActivity(new Intent(context, Devices.class));
                        break;
                    case "Reset":
                        prefsHelper.saveregister(context, prefsHelper.default_values);
                        Toast.makeText(context, "Device Reset Done", Toast.LENGTH_SHORT).show();
                        break;

                    case "About":
                        context.startActivity(new Intent(context, About.class));
                        break;

                }
            }
        });


//        holder.buttonhelp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(context,Instructions.class);
//                i.putExtra("position",position);
//                Log.d("Ritik", "onClick: this is cool");
//                try{((Activity) context).startActivityForResult(i,89);}
//                catch (Exception e){
//                    Log.d("Ritik", "got him here: "+e.getMessage());
//                }
//            }
//        });

//        holder.buttondone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                holder.edittextcontent.setError(null);
//                if(holder.edittextcontent.getText().toString().length()!=0&&instructionDbHelper.isInstruction(holder.edittextcontent.getText().toString().toUpperCase())){
//                    if(content_lv.size()!=position)
//                        content_lv.remove(position);
//                    content_lv.add(position, holder.edittextcontent.getText().toString());
//                    Log.d("Ritik", "onClick: a");
//                    int size = instructionDbHelper.getsize(holder.edittextcontent.getText().toString());
//                    if(size_instruction.size()!=position)
//                        size_instruction.remove(position);
//                    size_instruction.add(position, size);
//                    Log.d("Ritik", "onClick: c");
//                    int current_addr = Integer.parseInt(typeHelper.Hex2Dec(address_lv.get(position).substring(0, address_lv.get(position).length() - 1)));
//                    int next_addr = current_addr + size_instruction.get(position);
//                    if(address_lv.size()!=position+1)
//                        address_lv.remove(position+1);
//                    Log.d("Ritik", "onClick: before "+address_lv.size()+" "+position);
//                    address_lv.add(position + 1, new String(new char[4 - typeHelper.Dec2Hex(Integer.toString(next_addr)).length()]).replace('\0', '0') + typeHelper.Dec2Hex(Integer.toString(next_addr)) + "H");
//                    Log.d("Ritik", "onClick: b");
//                    String instruction = holder.edittextcontent.getText().toString();
//                    if (size_instruction.get(position) == 1) {
//                        Cursor cursor = instructionDbHelper.getInstruction(instruction);
//                        memoryhelper.save_memory(address_lv.get(position), cursor.getString(0) + "H");
//                    } else if (size_instruction.get(position) == 2) {
//                        String data_byte = instruction.substring(instruction.lastIndexOf(" ") + 1);
//                        instruction = instruction.substring(0, instruction.length() - 4);
//                        Cursor cursor = instructionDbHelper.getInstruction(instruction);
//                        memoryhelper.save_memory(address_lv.get(position), cursor.getString(0) + "H");
//                        int current_address = Integer.parseInt(typeHelper.Hex2Dec(address_lv.get(position).substring(0, address_lv.get(position).length() - 1)));
//                        String next_address = typeHelper.Dec2Hex(Integer.toString(current_address + 1)) + "H";
//                        memoryhelper.save_memory(new String(new char[5 - next_address.length()]).replace('\0', '0')+next_address, data_byte.toUpperCase());
//                    } else if (size_instruction.get(position) == 3) {
//                        String data_byte = instruction.substring(instruction.lastIndexOf(" ") + 1);
//                        instruction = instruction.substring(0, instruction.length() - 6);
//                        Cursor cursor = instructionDbHelper.getInstruction(instruction);
//                        memoryhelper.save_memory(address_lv.get(position), cursor.getString(0) + "H");
//                        String data_byte_1 = data_byte.substring(0, data_byte.length() - 3) + "H";
//                        String data_byte_2 = data_byte.substring(2, data_byte.length());
//                        int current_address = Integer.parseInt(typeHelper.Hex2Dec(address_lv.get(position).substring(0, address_lv.get(position).length() - 1)));
//                        String next_address = typeHelper.Dec2Hex(Integer.toString(current_address + 1)) + "H";
//                        memoryhelper.save_memory(new String(new char[5 - next_address.length()]).replace('\0', '0') + next_address, data_byte_2.toUpperCase()); //due to little endian style
//                        next_address = typeHelper.Dec2Hex(Integer.toString(current_address + 2)) + "H";
//                        memoryhelper.save_memory(new String(new char[5 - next_address.length()]).replace('\0', '0') + next_address, data_byte_1.toUpperCase());
//                    }
//
//
//                    notifyDataSetChanged();
//
//
//
//                }
//                else
//                    holder.edittextcontent.setError("Invalid Instruction");
//            }
//        });


        return child;
    }
//    public void setContent(int position,String Content){
//        Log.d("Ritik", "setContent: "+position+" "+Content);
//        if(position<content_lv.size()){
//            content_lv.set(position,Content);
//        }else {
//            content_lv.add(Content);
//        }
//        notifyDataSetChanged();
//    }

    public class Holder {
        TextView name;
        EditText data;


    }


}