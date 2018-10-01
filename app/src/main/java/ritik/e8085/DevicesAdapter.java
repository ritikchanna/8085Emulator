package ritik.e8085;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SuperUser on 22-03-2017.
 */

public class DevicesAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> address_lv;
    ArrayList<String> content_lv;
    TypeHelper typeHelper;
    SQLiteHelper SQLITEHELPER;


    public DevicesAdapter(
            Context context2,
            ArrayList<String> address,
            ArrayList<String> content

    ) {

        this.context = context2;
        this.address_lv = address;
        this.content_lv = content;
        typeHelper = new TypeHelper();
        SQLITEHELPER = new SQLiteHelper(context2);


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

        final DevicesAdapter.Holder holder;
        List<String> device_type = new ArrayList<String>();

        device_type.add("IN");
        device_type.add("OUT");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.device_spinner, device_type);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.device_listview_element, null);

            holder = new DevicesAdapter.Holder();


            holder.device_address = child.findViewById(R.id.device_address_listview);
            holder.device_content = child.findViewById(R.id.device_content_listview);
            holder.device_delete = child.findViewById(R.id.device_delete_listview);
            holder.device_spinner = child.findViewById(R.id.device_type_spinner);
            holder.device_spinner.setAdapter(dataAdapter);

            holder.device_save = child.findViewById(R.id.device_save_listview);
//            holder.device_delete.setImageResource();

            child.setTag(holder);

        } else {

            holder = (DevicesAdapter.Holder) child.getTag();
        }


        try {
            Log.d("Ritik", "getView: setting element " + address_lv.get(position) + " " + content_lv.get(position));

            if (address_lv.get(position).substring(0, 1).equals("S"))
                holder.device_address.setText(address_lv.get(position));
            else {
                holder.device_address.setText(address_lv.get(position).substring((2)));
            }
            holder.device_content.setText(content_lv.get(position));
            holder.device_spinner.setSelection((address_lv.get(position).substring(1, 2).equals("I")) ? 0 : 1);
            holder.device_spinner.setEnabled(false);
        } catch (Exception e) {

        }
        if (address_lv.get(position).substring(0, 1).equals("S"))
            holder.device_spinner.setVisibility(View.INVISIBLE);

//        if(address_lv.get(position).equals("Add"))
//            holder.device_address.setEnabled(true);
        if (address_lv.get(position).contains("S"))
            holder.device_delete.setVisibility(View.INVISIBLE);


//        child.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("Ritik", "Devices: pressed");
//                if(!address_lv.get(position).substring(0,1).equals("S"))
//                holder.device_content.setText(typeHelper.Cycle(holder.device_content.getText().toString(),2));
//
//
//            }
//        });
        holder.device_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Ritik", "Devices: delete button pressed");

                SQLITEHELPER.delete_memory(address_lv.get(position));
                Log.d("Ritik", "Devices: delete button pressed1");
                address_lv.remove(position);
                Log.d("Ritik", "Devices: delete button pressed2");
                content_lv.remove(position);
                Log.d("Ritik", "Devices: delete button pressed3");
                notifyDataSetChanged();

            }
        });
        holder.device_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.device_content.isEnabled()) {


                    if (address_lv.get(position).substring(0, 1).equals("S")) {
                        if ((holder.device_content.getText().toString().matches("[01]*"))) {
                            SQLITEHELPER.save_memory(holder.device_address.getText().toString().toString().toUpperCase(), holder.device_content.getText().toString().toUpperCase());
                            holder.device_content.setEnabled(false);
                            content_lv.set(position, holder.device_content.getText().toString().toUpperCase());
                            address_lv.set(position, holder.device_address.getText().toString().toUpperCase());
                            notifyDataSetChanged();
                            holder.device_save.setImageResource(android.R.drawable.ic_menu_edit);
                        } else
                            holder.device_content.setError("Binary Bits only");
                    }


                    if (!address_lv.get(position).substring(0, 1).equals("S")) {

                        if (holder.device_spinner.getSelectedItem().toString().substring(0, 1).equals("I") || holder.device_spinner.getSelectedItem().toString().substring(0, 1).equals("O")) {
                            Log.d("Ritik", "save: spinner value is " + holder.device_spinner.getSelectedItem().toString());
                            if (typeHelper.isHex(holder.device_content.getText().toString(), 2)) {
                                SQLITEHELPER.save_memory("D" + holder.device_spinner.getSelectedItem().toString().substring(0, 1) + holder.device_address.getText().toString(), holder.device_content.getText().toString().toUpperCase());
                                holder.device_content.setEnabled(false);
                                holder.device_address.setEnabled(false);
                                holder.device_spinner.setEnabled(false);
                                content_lv.set(position, holder.device_content.getText().toString().toUpperCase());
                                address_lv.set(position, "D" + holder.device_spinner.getSelectedItem().toString().substring(0, 1) + holder.device_address.getText().toString().toUpperCase());
                                notifyDataSetChanged();
                                holder.device_save.setImageResource(android.R.drawable.ic_menu_edit);
                            } else
                                holder.device_content.setError("Hexadecimal only");
                        } else
                            ((TextView) holder.device_spinner.getSelectedView()).setError("");

                    }


                } else {
                    holder.device_content.setEnabled(true);
                    holder.device_content.requestFocus();
                    if (!address_lv.get(position).substring(0, 1).equals("S"))
                        holder.device_content.setSelection(2);
                    if (address_lv.get(position).substring(0, 1).equals("D")) {
                        holder.device_spinner.setEnabled(true);
                        holder.device_address.setEnabled(true);
                        Log.d("Ritik", "onClick: making editable");
                    }
                    holder.device_save.setImageResource(android.R.drawable.ic_menu_save);

                }


            }
        });

        return child;
    }

    public class Holder {
        EditText device_content;
        EditText device_address;
        private ImageButton device_delete, device_save;
        Spinner device_spinner;

    }


}
