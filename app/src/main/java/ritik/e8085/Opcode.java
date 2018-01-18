package ritik.e8085;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import java.io.IOException;

/**
 * Created by SuperUser on 13-03-2017.
 */

public class Opcode {
    InstructionDbHelper instructionDbHelper;
    SQLiteHelper sqLiteHelper;
    Context context;
    TypeHelper typeHelper;
    PrefsHelper prefshelper;

    public Opcode(Context context) {
        this.context = context;
        instructionDbHelper = new InstructionDbHelper(context);
        sqLiteHelper = new SQLiteHelper(context);
        typeHelper = new TypeHelper();
        prefshelper = new PrefsHelper();


        instructionDbHelper = new InstructionDbHelper(context);


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

    }


    public int execute() {

        String flags = "00000000", temp1, temp2;
        int[] flags_new, flags_new2;
        String pc_address = prefshelper.getregister(context, "pc");
        String opcode = sqLiteHelper.read_memory(pc_address);
        String data_address_1 = typeHelper.Dec2Hex(Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(pc_address.substring(0, pc_address.length() - 1))) + 1));
        data_address_1 = new String(new char[4 - data_address_1.length()]).replace('\0', '0') + data_address_1 + "H";
        String data_address_2 = typeHelper.Dec2Hex(Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(pc_address.substring(0, pc_address.length() - 1))) + 2));
        data_address_2 = new String(new char[4 - data_address_2.length()]).replace('\0', '0') + data_address_2 + "H";
        Cursor cursor = instructionDbHelper.getsizeopcode(opcode);
        int size = cursor.getInt(1);
        String time = cursor.getString(2);
        String next_pc_address = typeHelper.Dec2Hex(Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(pc_address.substring(0, pc_address.length() - 1))) + size));
        next_pc_address = new String(new char[4 - next_pc_address.length()]).replace('\0', '0') + next_pc_address + "H";
        prefshelper.saveregister(context, "pc", next_pc_address);


        switch (opcode) {


            /// MOV start here
            case "7FH":
                Log.d("Ritik", "execute: mov a,a");
                // nothing to do here
                break;
            case "78H":
                Log.d("Ritik", "execute: mov a,b");
                prefshelper.saveregister(context, "a", prefshelper.getregister(context, "b"));
                break;
            case "79H":
                Log.d("Ritik", "execute: mov a, c");
                prefshelper.saveregister(context, "a", prefshelper.getregister(context, "c"));
                break;
            case "7AH":
                Log.d("Ritik", "execute: mov a, d");
                prefshelper.saveregister(context, "a", prefshelper.getregister(context, "d"));
                break;
            case "7BH":
                Log.d("Ritik", "execute: mov a, e");
                prefshelper.saveregister(context, "a", prefshelper.getregister(context, "e"));
                break;
            case "7CH":
                Log.d("Ritik", "execute: mov a, h");
                prefshelper.saveregister(context, "a", prefshelper.getregister(context, "h"));
                break;
            case "7DH":
                Log.d("Ritik", "execute: mov a, l");
                prefshelper.saveregister(context, "a", prefshelper.getregister(context, "l"));
                break;
            case "7EH":
                Log.d("Ritik", "execute: mov a, m");
                prefshelper.saveregister(context, "a", sqLiteHelper.read_memory(prefshelper.getregister(context, "h").substring(0, prefshelper.getregister(context, "h").length() - 1) + prefshelper.getregister(context, "l")));
                break;
            case "47H":
                Log.d("Ritik", "execute: mov b, a");
                prefshelper.saveregister(context, "b", prefshelper.getregister(context, "a"));
                break;
            case "40H":
                Log.d("Ritik", "execute: mov b, b");
                //do nothig
                break;
            case "41H":
                Log.d("Ritik", "execute: mov b, c");
                prefshelper.saveregister(context, "b", prefshelper.getregister(context, "c"));
                break;
            case "42H":
                Log.d("Ritik", "execute: mov b, d");
                prefshelper.saveregister(context, "b", prefshelper.getregister(context, "d"));
                break;
            case "43H":
                Log.d("Ritik", "execute: mov b, e");
                prefshelper.saveregister(context, "b", prefshelper.getregister(context, "e"));
                break;
            case "44H":
                Log.d("Ritik", "execute: mov b, h");
                prefshelper.saveregister(context, "b", prefshelper.getregister(context, "h"));
                break;
            case "45H":
                Log.d("Ritik", "execute: mov b, l");
                prefshelper.saveregister(context, "b", prefshelper.getregister(context, "l"));
                break;
            case "46H":
                Log.d("Ritik", "execute: mov b, m");
                prefshelper.saveregister(context, "b", sqLiteHelper.read_memory(prefshelper.getregister(context, "h").substring(0, prefshelper.getregister(context, "h").length() - 1) + prefshelper.getregister(context, "l")));
                break;
            case "4FH":
                Log.d("Ritik", "execute: mov c, a");
                prefshelper.saveregister(context, "c", prefshelper.getregister(context, "a"));
                break;
            case "48H":
                Log.d("Ritik", "execute: mov c, b");
                prefshelper.saveregister(context, "c", prefshelper.getregister(context, "b"));
                break;
            case "49H":
                Log.d("Ritik", "execute: mov c, c");
                //do nothing here
                break;
            case "4AH":
                Log.d("Ritik", "execute: mov c, d");
                prefshelper.saveregister(context, "c", prefshelper.getregister(context, "d"));
                break;
            case "4BH":
                Log.d("Ritik", "execute: mov c, e");
                prefshelper.saveregister(context, "c", prefshelper.getregister(context, "e"));
                break;
            case "4CH":
                Log.d("Ritik", "execute: mov c, h");
                prefshelper.saveregister(context, "c", prefshelper.getregister(context, "h"));
                break;
            case "4DH":
                Log.d("Ritik", "execute: mov c, l");
                prefshelper.saveregister(context, "c", prefshelper.getregister(context, "l"));
                break;
            case "4EH":
                Log.d("Ritik", "execute: mov c, m");
                prefshelper.saveregister(context, "c", sqLiteHelper.read_memory(prefshelper.getregister(context, "h").substring(0, prefshelper.getregister(context, "h").length() - 1) + prefshelper.getregister(context, "l")));
                break;
            case "57H":
                Log.d("Ritik", "execute: mov d, a");
                prefshelper.saveregister(context, "d", prefshelper.getregister(context, "a"));
                break;
            case "50H":
                Log.d("Ritik", "execute: mov d, b");
                prefshelper.saveregister(context, "d", prefshelper.getregister(context, "b"));
                break;
            case "51H":
                Log.d("Ritik", "execute: mov d, c");
                prefshelper.saveregister(context, "d", prefshelper.getregister(context, "c"));
                break;
            case "52H":
                Log.d("Ritik", "execute: mov d, d");
                //do nothing here
                break;
            case "53H":
                Log.d("Ritik", "execute: mov d, e");
                prefshelper.saveregister(context, "d", prefshelper.getregister(context, "e"));
                break;
            case "54H":
                Log.d("Ritik", "execute: mov d, h");
                prefshelper.saveregister(context, "d", prefshelper.getregister(context, "h"));
                break;
            case "55H":
                Log.d("Ritik", "execute: mov d, l");
                prefshelper.saveregister(context, "d", prefshelper.getregister(context, "l"));
                break;
            case "56H":
                Log.d("Ritik", "execute: mov d, m");
                prefshelper.saveregister(context, "d", sqLiteHelper.read_memory(prefshelper.getregister(context, "h").substring(0, prefshelper.getregister(context, "h").length() - 1) + prefshelper.getregister(context, "l")));
                break;
            case "5FH":
                Log.d("Ritik", "execute: mov e, a");
                prefshelper.saveregister(context, "e", prefshelper.getregister(context, "a"));
                break;
            case "58H":
                Log.d("Ritik", "execute: mov e, b");
                prefshelper.saveregister(context, "e", prefshelper.getregister(context, "b"));
                break;
            case "59H":
                Log.d("Ritik", "execute: mov e, c");
                prefshelper.saveregister(context, "e", prefshelper.getregister(context, "c"));
                break;
            case "5AH":
                Log.d("Ritik", "execute: mov e, d");
                prefshelper.saveregister(context, "e", prefshelper.getregister(context, "d"));

                break;
            case "5BH":
                Log.d("Ritik", "execute: mov e, e");
                //do nothing here
                break;
            case "5CH":
                Log.d("Ritik", "execute: mov e, h");
                prefshelper.saveregister(context, "e", prefshelper.getregister(context, "h"));
                break;
            case "5DH":
                Log.d("Ritik", "execute: mov e, l");
                prefshelper.saveregister(context, "e", prefshelper.getregister(context, "l"));
                break;
            case "5EH":
                Log.d("Ritik", "execute: mov e, m");
                prefshelper.saveregister(context, "e", sqLiteHelper.read_memory(prefshelper.getregister(context, "h").substring(0, prefshelper.getregister(context, "h").length() - 1) + prefshelper.getregister(context, "l")));
                break;
            case "67H":
                Log.d("Ritik", "execute: mov h, a");
                prefshelper.saveregister(context, "h", prefshelper.getregister(context, "a"));
                break;
            case "60H":
                Log.d("Ritik", "execute: mov h, b");
                prefshelper.saveregister(context, "h", prefshelper.getregister(context, "b"));
                break;
            case "61H":
                Log.d("Ritik", "execute: mov h, c");
                prefshelper.saveregister(context, "h", prefshelper.getregister(context, "c"));
                break;
            case "62H":
                Log.d("Ritik", "execute: mov h, d");
                prefshelper.saveregister(context, "h", prefshelper.getregister(context, "d"));

                break;
            case "63H":
                Log.d("Ritik", "execute: mov h, e");
                prefshelper.saveregister(context, "h", prefshelper.getregister(context, "e"));
                break;
            case "64H":
                Log.d("Ritik", "execute: mov h, h");
                //do nothing here
                break;
            case "65H":
                Log.d("Ritik", "execute: mov h, l");
                prefshelper.saveregister(context, "h", prefshelper.getregister(context, "l"));
                break;
            case "66H":
                Log.d("Ritik", "execute: mov h, m");
                prefshelper.saveregister(context, "h", sqLiteHelper.read_memory(prefshelper.getregister(context, "h").substring(0, prefshelper.getregister(context, "h").length() - 1) + prefshelper.getregister(context, "l")));
                break;
            case "6FH":
                Log.d("Ritik", "execute: mov l, a");
                prefshelper.saveregister(context, "l", prefshelper.getregister(context, "a"));
                break;
            case "68H":
                Log.d("Ritik", "execute: mov l, b");
                prefshelper.saveregister(context, "l", prefshelper.getregister(context, "b"));
                break;
            case "69H":
                Log.d("Ritik", "execute: mov l, c");
                prefshelper.saveregister(context, "l", prefshelper.getregister(context, "c"));
                break;
            case "6AH":
                Log.d("Ritik", "execute: mov l, d");
                prefshelper.saveregister(context, "l", prefshelper.getregister(context, "d"));

                break;
            case "6BH":
                Log.d("Ritik", "execute: mov l, e");
                prefshelper.saveregister(context, "l", prefshelper.getregister(context, "e"));
                break;
            case "6CH":
                Log.d("Ritik", "execute: mov l, h");
                prefshelper.saveregister(context, "l", prefshelper.getregister(context, "h"));
                break;
            case "6DH":
                Log.d("Ritik", "execute: mov l, l");
                //do nothing here
                break;
            case "6EH":
                Log.d("Ritik", "execute: mov l, m");
                prefshelper.saveregister(context, "l", sqLiteHelper.read_memory(prefshelper.getregister(context, "h").substring(0, prefshelper.getregister(context, "h").length() - 1) + prefshelper.getregister(context, "l")));
                break;
            case "77H":
                Log.d("Ritik", "execute: mov m, a");
                sqLiteHelper.save_memory(prefshelper.getregister(context, "h").substring(0, prefshelper.getregister(context, "h").length() - 1) + prefshelper.getregister(context, "l"), prefshelper.getregister(context, "a"));
                break;
            case "70H":
                Log.d("Ritik", "execute: mov m, b");
                sqLiteHelper.save_memory(prefshelper.getregister(context, "h").substring(0, prefshelper.getregister(context, "h").length() - 1) + prefshelper.getregister(context, "l"), prefshelper.getregister(context, "b"));
                break;
            case "71H":
                Log.d("Ritik", "execute: mov m, c");
                sqLiteHelper.save_memory(prefshelper.getregister(context, "h").substring(0, prefshelper.getregister(context, "h").length() - 1) + prefshelper.getregister(context, "l"), prefshelper.getregister(context, "c"));
                break;
            case "72H":
                Log.d("Ritik", "execute: mov m, d");
                sqLiteHelper.save_memory(prefshelper.getregister(context, "h").substring(0, prefshelper.getregister(context, "h").length() - 1) + prefshelper.getregister(context, "l"), prefshelper.getregister(context, "d"));
                break;
            case "73H":
                Log.d("Ritik", "execute: mov m, e");
                sqLiteHelper.save_memory(prefshelper.getregister(context, "h").substring(0, prefshelper.getregister(context, "h").length() - 1) + prefshelper.getregister(context, "l"), prefshelper.getregister(context, "e"));
                break;
            case "74H":
                Log.d("Ritik", "execute: mov m, h");
                sqLiteHelper.save_memory(prefshelper.getregister(context, "h").substring(0, prefshelper.getregister(context, "h").length() - 1) + prefshelper.getregister(context, "l"), prefshelper.getregister(context, "h"));
                break;
            case "75H":
                Log.d("Ritik", "execute: mov m, l");
                sqLiteHelper.save_memory(prefshelper.getregister(context, "h").substring(0, prefshelper.getregister(context, "h").length() - 1) + prefshelper.getregister(context, "l"), prefshelper.getregister(context, "l"));
                break;
            //MOV end here


            //MVI starts here
            case "3EH":
                Log.d("Ritik", "execute: mvi a, DATA");
                prefshelper.saveregister(context, "a", sqLiteHelper.read_memory(data_address_1));
                break;
            case "06H":
                Log.d("Ritik", "execute: mvi b, DATA");
                prefshelper.saveregister(context, "b", sqLiteHelper.read_memory(data_address_1));
                break;
            case "0EH":
                Log.d("Ritik", "execute: mvi c, DATA");
                prefshelper.saveregister(context, "c", sqLiteHelper.read_memory(data_address_1));
                break;
            case "16H":
                Log.d("Ritik", "execute: mvi d, DATA");
                prefshelper.saveregister(context, "d", sqLiteHelper.read_memory(data_address_1));
                break;
            case "1EH":
                Log.d("Ritik", "execute: mvi e, DATA");
                prefshelper.saveregister(context, "e", sqLiteHelper.read_memory(data_address_1));
                break;
            case "26H":
                Log.d("Ritik", "execute: mvi h, DATA");
                prefshelper.saveregister(context, "h", sqLiteHelper.read_memory(data_address_1));
                break;
            case "2EH":
                Log.d("Ritik", "execute: mvi l, DATA");
                prefshelper.saveregister(context, "l", sqLiteHelper.read_memory(data_address_1));
                break;
            case "36H":
                Log.d("Ritik", "execute: mvi m, DATA");
                sqLiteHelper.save_memory(prefshelper.getregister(context, "h").substring(0, prefshelper.getregister(context, "h").length() - 1) + prefshelper.getregister(context, "l"), sqLiteHelper.read_memory(data_address_1));
                break;
            //MVI end here

            case "3AH":
                Log.d("Ritik", "execute: lda address");
                prefshelper.saveregister(context, "a", sqLiteHelper.read_memory(sqLiteHelper.read_memory(data_address_2).substring(0, sqLiteHelper.read_memory(data_address_2).length() - 1) + sqLiteHelper.read_memory(data_address_1)));
                break;
            case "0AH":
                Log.d("Ritik", "execute: ldax b");
                prefshelper.saveregister(context, "a", sqLiteHelper.read_memory(prefshelper.getregister(context, "b").substring(0, prefshelper.getregister(context, "b").length() - 1) + prefshelper.getregister(context, "c")));
                break;
            case "1AH":
                Log.d("Ritik", "execute: ldax d");
                prefshelper.saveregister(context, "a", sqLiteHelper.read_memory(prefshelper.getregister(context, "d").substring(0, prefshelper.getregister(context, "d").length() - 1) + prefshelper.getregister(context, "e")));
                break;
            case "01H":
                Log.d("Ritik", "execute: LXI B Data ");
                prefshelper.saveregister(context, "b", sqLiteHelper.read_memory(data_address_2));
                prefshelper.saveregister(context, "c", sqLiteHelper.read_memory(data_address_1));
                break;
            case "11H":
                Log.d("Ritik", "execute: LXI D Data ");
                prefshelper.saveregister(context, "d", sqLiteHelper.read_memory(data_address_2));
                prefshelper.saveregister(context, "e", sqLiteHelper.read_memory(data_address_1));
                break;
            case "21H":
                Log.d("Ritik", "execute: LXI H Data ");
                prefshelper.saveregister(context, "h", sqLiteHelper.read_memory(data_address_2));
                prefshelper.saveregister(context, "l", sqLiteHelper.read_memory(data_address_1));
                break;
            case "31H":
                Log.d("Ritik", "execute: LXI sp Data ");
                prefshelper.saveregister(context, "sp", sqLiteHelper.read_memory(data_address_2).substring(0, sqLiteHelper.read_memory(data_address_2).length() - 1) + sqLiteHelper.read_memory(data_address_1));
                break;
            case "2AH":
                Log.d("Ritik", "execute: LHLD Address ");
                String address = sqLiteHelper.read_memory(data_address_2).substring(0, sqLiteHelper.read_memory(data_address_2).length() - 1) + sqLiteHelper.read_memory(data_address_1);
                prefshelper.saveregister(context, "l", sqLiteHelper.read_memory(address));
                address = Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(address.substring(0, address.length() - 1))) + 1);
                address = new String(new char[5 - address.length()]).replace('\0', '0') + address;
                prefshelper.saveregister(context, "h", sqLiteHelper.read_memory(address));
                break;
            case "32H":
                Log.d("Ritik", "execute: STA Data ");
                sqLiteHelper.save_memory(sqLiteHelper.read_memory(data_address_2).substring(0, sqLiteHelper.read_memory(data_address_2).length() - 1) + sqLiteHelper.read_memory(data_address_1), prefshelper.getregister(context, "a"));
                break;
            case "12H":
                Log.d("Ritik", "execute: stax d ");
                String address1 = prefshelper.getregister(context, "d").substring(0, prefshelper.getregister(context, "d").length() - 1) + prefshelper.getregister(context, "e");
                sqLiteHelper.save_memory(address1, prefshelper.getregister(context, "a"));
                break;
            case "02H":
                Log.d("Ritik", "execute: stax b");
                String address2 = prefshelper.getregister(context, "b").substring(0, prefshelper.getregister(context, "b").length() - 1) + prefshelper.getregister(context, "c");
                sqLiteHelper.save_memory(address2, prefshelper.getregister(context, "a"));
                break;
            case "22H":
                Log.d("Ritik", "execute: SHLD Address");
                String address3 = sqLiteHelper.read_memory(data_address_2).substring(0, sqLiteHelper.read_memory(data_address_2).length() - 1) + sqLiteHelper.read_memory(data_address_1);
                sqLiteHelper.save_memory(address3, prefshelper.getregister(context, "l"));
                sqLiteHelper.save_memory(typeHelper.AddHex(address3, "0001H", 4), prefshelper.getregister(context, "h"));
                break;
            case "EBH":
                Log.d("Ritik", "execute: XCHG");
                String temp = prefshelper.getregister(context, "d");
                prefshelper.saveregister(context, "d", prefshelper.getregister(context, "h"));
                prefshelper.saveregister(context, "h", temp);
                temp = prefshelper.getregister(context, "e");
                prefshelper.saveregister(context, "e", prefshelper.getregister(context, "l"));
                prefshelper.saveregister(context, "l", temp);
                break;
            case "F9H":
                Log.d("Ritik", "execute: SPHL");
                prefshelper.saveregister(context, "sp", prefshelper.getregister(context, "h").substring(0, prefshelper.getregister(context, "h").length() - 1) + prefshelper.getregister(context, "l"));
                break;
            case "E3H":
                Log.d("Ritik", "execute: XTHL");
                temp1 = sqLiteHelper.read_memory(prefshelper.getregister(context, "sp"));
                sqLiteHelper.save_memory(prefshelper.getregister(context, "sp"), prefshelper.getregister(context, "l"));
                prefshelper.saveregister(context, "l", temp1);
                temp1 = sqLiteHelper.read_memory(typeHelper.AddHex(prefshelper.getregister(context, "sp"), "0001H", 4));
                sqLiteHelper.save_memory(typeHelper.AddHex(prefshelper.getregister(context, "sp"), "0001H", 4), prefshelper.getregister(context, "h"));
                prefshelper.saveregister(context, "h", temp1);
                break;
            case "C5H":
                Log.d("Ritik", "execute: push b");
                push(prefshelper.getregister(context, "b"));
                push(prefshelper.getregister(context, "c"));
                break;
            case "D5H":
                Log.d("Ritik", "execute: push d");
                push(prefshelper.getregister(context, "d"));
                push(prefshelper.getregister(context, "e"));
                break;
            case "E5H":
                Log.d("Ritik", "execute: push h");
                push(prefshelper.getregister(context, "h"));
                push(prefshelper.getregister(context, "e"));
                break;
            case "F5H":
                Log.d("Ritik", "execute: push psw");
                push(prefshelper.getregister(context, "a"));
                push(prefshelper.getregister(context, "f"));
                break;
            case "C1H":
                Log.d("Ritik", "execute: pop b");
                prefshelper.saveregister(context, "c", pop());
                prefshelper.saveregister(context, "b", pop());
                break;
            case "D1H":
                Log.d("Ritik", "execute: pop d");
                prefshelper.saveregister(context, "e", pop());
                prefshelper.saveregister(context, "d", pop());
                break;
            case "E1H":
                Log.d("Ritik", "execute: pop h");
                prefshelper.saveregister(context, "l", pop());
                prefshelper.saveregister(context, "h", pop());
                break;
            case "F1H":
                Log.d("Ritik", "execute: pop psw");
                prefshelper.saveregister(context, "f", pop());
                prefshelper.saveregister(context, "a", pop());
                break;
            case "D3H":
                Log.d("Ritik", "execute: OUT Address");
                sqLiteHelper.save_memory("DO" + sqLiteHelper.read_memory(data_address_1), prefshelper.getregister(context, "a"));
                break;
            case "DBH":
                Log.d("Ritik", "execute: in Address");
                Log.d("Ritik", "execute: in Address " + "DI" + sqLiteHelper.read_memory(data_address_1));
                prefshelper.saveregister(context, "a", sqLiteHelper.read_memory(("DI" + sqLiteHelper.read_memory(data_address_1)).toUpperCase()));
                Log.d("Ritik", "execute: in Address 2");
                break;
            case "E9H":
                Log.d("Ritik", "execute: pchl");
                prefshelper.saveregister(context, "pc", prefshelper.getregister(context, "h").substring(0, prefshelper.getregister(context, "h").length() - 1) + prefshelper.getregister(context, "l"));
                break;
            case "00H":
                Log.d("Ritik", "execute: nop");
                //do nothing , OFFICIALY
                break;

            //jump instructions start here
            case "C3H":
                Log.d("Ritik", "execute: jump address");
                prefshelper.saveregister(context, "pc", sqLiteHelper.read_memory(data_address_2).substring(0, sqLiteHelper.read_memory(data_address_2).length() - 1) + sqLiteHelper.read_memory(data_address_1));
                break;
            case "DAH":
                Log.d("Ritik", "execute: jc address");
                Log.d("Ritik", "execute: time1 is " + time);
                if (prefshelper.getflagbit(context, 0)) {
                    prefshelper.saveregister(context, "pc", sqLiteHelper.read_memory(data_address_2).substring(0, sqLiteHelper.read_memory(data_address_2).length() - 1) + sqLiteHelper.read_memory(data_address_1));
                    time = time.substring(time.length() - 2);
                } else {
                    time = time.substring(0, 1);
                }
                Log.d("Ritik", "execute: time is " + time);
                break;
            case "D2H":
                Log.d("Ritik", "execute: jnc address");
                Log.d("Ritik", "execute: time1 is " + time.substring(time.length() - 2));
                if (!prefshelper.getflagbit(context, 0)) {
                    prefshelper.saveregister(context, "pc", sqLiteHelper.read_memory(data_address_2).substring(0, sqLiteHelper.read_memory(data_address_2).length() - 1) + sqLiteHelper.read_memory(data_address_1));
                    time = time.substring(time.length() - 2);
                } else {
                    time = time.substring(0, 1);
                }
                Log.d("Ritik", "execute: time is " + time);
                break;
            case "F2H":
                Log.d("Ritik", "execute: jp address");
                if (prefshelper.getflagbit(context, 7)) {
                    prefshelper.saveregister(context, "pc", sqLiteHelper.read_memory(data_address_2).substring(0, sqLiteHelper.read_memory(data_address_2).length() - 1) + sqLiteHelper.read_memory(data_address_1));
                    time = time.substring(time.length() - 2);
                } else {
                    time = time.substring(0, 1);
                }
                break;
            case "FAH":
                Log.d("Ritik", "execute: jm address");
                if (!prefshelper.getflagbit(context, 7)) {
                    prefshelper.saveregister(context, "pc", sqLiteHelper.read_memory(data_address_2).substring(0, sqLiteHelper.read_memory(data_address_2).length() - 1) + sqLiteHelper.read_memory(data_address_1));
                    time = time.substring(time.length() - 2);
                } else {
                    time = time.substring(0, 1);
                }
                break;
            case "CAH":
                Log.d("Ritik", "execute: jz address");
                if (prefshelper.getflagbit(context, 6)) {
                    prefshelper.saveregister(context, "pc", sqLiteHelper.read_memory(data_address_2).substring(0, sqLiteHelper.read_memory(data_address_2).length() - 1) + sqLiteHelper.read_memory(data_address_1));
                    time = time.substring(time.length() - 2);
                } else {
                    time = time.substring(0, 1);
                }
                break;
            case "C2H":
                Log.d("Ritik", "execute: jnz address");
                if (!prefshelper.getflagbit(context, 6)) {
                    prefshelper.saveregister(context, "pc", sqLiteHelper.read_memory(data_address_2).substring(0, sqLiteHelper.read_memory(data_address_2).length() - 1) + sqLiteHelper.read_memory(data_address_1));
                    time = time.substring(time.length() - 2);
                } else {
                    time = time.substring(0, 1);
                }
                break;
            case "EAH":
                Log.d("Ritik", "execute: jpe address");
                if (prefshelper.getflagbit(context, 2)) {
                    prefshelper.saveregister(context, "pc", sqLiteHelper.read_memory(data_address_2).substring(0, sqLiteHelper.read_memory(data_address_2).length() - 1) + sqLiteHelper.read_memory(data_address_1));
                    time = time.substring(time.length() - 2);
                } else {
                    time = time.substring(0, 1);
                }
                break;
            case "E2H":
                Log.d("Ritik", "execute: jpo address");
                if (!prefshelper.getflagbit(context, 2)) {
                    prefshelper.saveregister(context, "pc", sqLiteHelper.read_memory(data_address_2).substring(0, sqLiteHelper.read_memory(data_address_2).length() - 1) + sqLiteHelper.read_memory(data_address_1));
                    time = time.substring(time.length() - 2);
                } else {
                    time = time.substring(0, 1);
                }
                break;
            //jump ends here

            //call starts here
            case "CDH":
                Log.d("Ritik", "execute: call label");
                push(prefshelper.getregister(context, "pc").substring(0, 2) + "H");
                push(prefshelper.getregister(context, "pc").substring(2));
                prefshelper.saveregister(context, "pc", sqLiteHelper.read_memory(data_address_2).substring(0, sqLiteHelper.read_memory(data_address_2).length() - 1) + sqLiteHelper.read_memory(data_address_1));
                break;
            case "DCH":
                Log.d("Ritik", "execute: cc label");
                if (prefshelper.getflagbit(context, 0)) {
                    push(prefshelper.getregister(context, "pc").substring(0, 2) + "H");
                    push(prefshelper.getregister(context, "pc").substring(2));
                    prefshelper.saveregister(context, "pc", sqLiteHelper.read_memory(data_address_2).substring(0, sqLiteHelper.read_memory(data_address_2).length() - 1) + sqLiteHelper.read_memory(data_address_1));
                    time = time.substring(time.length() - 2);
                } else {
                    time = time.substring(0, 1);
                }
                break;
            case "D4H":
                Log.d("Ritik", "execute: cnc label");
                if (!prefshelper.getflagbit(context, 0)) {
                    push(prefshelper.getregister(context, "pc").substring(0, 2) + "H");
                    push(prefshelper.getregister(context, "pc").substring(2));
                    prefshelper.saveregister(context, "pc", sqLiteHelper.read_memory(data_address_2).substring(0, sqLiteHelper.read_memory(data_address_2).length() - 1) + sqLiteHelper.read_memory(data_address_1));
                    time = time.substring(time.length() - 2);
                } else {
                    time = time.substring(0, 1);
                }
                break;
            case "F4H":
                Log.d("Ritik", "execute: cp label");
                if (prefshelper.getflagbit(context, 7)) {
                    push(prefshelper.getregister(context, "pc").substring(0, 2) + "H");
                    push(prefshelper.getregister(context, "pc").substring(2));
                    prefshelper.saveregister(context, "pc", sqLiteHelper.read_memory(data_address_2).substring(0, sqLiteHelper.read_memory(data_address_2).length() - 1) + sqLiteHelper.read_memory(data_address_1));
                    time = time.substring(time.length() - 2);
                } else {
                    time = time.substring(0, 1);
                }
                break;
            case "FCH":
                Log.d("Ritik", "execute: cm label");
                if (!prefshelper.getflagbit(context, 7)) {
                    push(prefshelper.getregister(context, "pc").substring(0, 2) + "H");
                    push(prefshelper.getregister(context, "pc").substring(2));
                    prefshelper.saveregister(context, "pc", sqLiteHelper.read_memory(data_address_2).substring(0, sqLiteHelper.read_memory(data_address_2).length() - 1) + sqLiteHelper.read_memory(data_address_1));
                    time = time.substring(time.length() - 2);
                } else {
                    time = time.substring(0, 1);
                }
                break;
            case "CCH":
                Log.d("Ritik", "execute: cz label");
                if (prefshelper.getflagbit(context, 6)) {
                    push(prefshelper.getregister(context, "pc").substring(0, 2) + "H");
                    push(prefshelper.getregister(context, "pc").substring(2));
                    prefshelper.saveregister(context, "pc", sqLiteHelper.read_memory(data_address_2).substring(0, sqLiteHelper.read_memory(data_address_2).length() - 1) + sqLiteHelper.read_memory(data_address_1));
                    time = time.substring(time.length() - 2);
                } else {
                    time = time.substring(0, 1);
                }
                break;
            case "C4H":
                Log.d("Ritik", "execute: cnz label");
                if (!prefshelper.getflagbit(context, 6)) {
                    push(prefshelper.getregister(context, "pc").substring(0, 2) + "H");
                    push(prefshelper.getregister(context, "pc").substring(2));
                    prefshelper.saveregister(context, "pc", sqLiteHelper.read_memory(data_address_2).substring(0, sqLiteHelper.read_memory(data_address_2).length() - 1) + sqLiteHelper.read_memory(data_address_1));
                    time = time.substring(time.length() - 2);
                } else {
                    time = time.substring(0, 1);
                }
                break;
            case "ECH":
                Log.d("Ritik", "execute: cpe label");
                if (prefshelper.getflagbit(context, 2)) {
                    push(prefshelper.getregister(context, "pc").substring(0, 2) + "H");
                    push(prefshelper.getregister(context, "pc").substring(2));
                    prefshelper.saveregister(context, "pc", sqLiteHelper.read_memory(data_address_2).substring(0, sqLiteHelper.read_memory(data_address_2).length() - 1) + sqLiteHelper.read_memory(data_address_1));
                    time = time.substring(time.length() - 2);
                } else {
                    time = time.substring(0, 1);
                }
                break;
            case "E4H":
                Log.d("Ritik", "execute: cpo label");
                if (!prefshelper.getflagbit(context, 2)) {
                    push(prefshelper.getregister(context, "pc").substring(0, 2) + "H");
                    push(prefshelper.getregister(context, "pc").substring(2));
                    prefshelper.saveregister(context, "pc", sqLiteHelper.read_memory(data_address_2).substring(0, sqLiteHelper.read_memory(data_address_2).length() - 1) + sqLiteHelper.read_memory(data_address_1));
                    time = time.substring(time.length() - 2);
                } else {
                    time = time.substring(0, 1);
                }
                break;
            //call end here

            //ret start here
            case "C9H":
                Log.d("Ritik", "execute: ret");
                String return_addr = pop();
                return_addr = pop().substring(0, 2) + return_addr;
                prefshelper.saveregister(context, "pc", return_addr);
                break;
            case "RCH":
                Log.d("Ritik", "execute: rc");
                if (prefshelper.getflagbit(context, 0)) {
                    String return_addr1 = pop();
                    return_addr1 = pop().substring(0, 2) + return_addr1;
                    prefshelper.saveregister(context, "pc", return_addr1);
                    time = time.substring(time.length() - 2);
                } else {
                    time = time.substring(0, 1);
                }
                break;
            case "D0H":
                Log.d("Ritik", "execute: rnc");
                if (!prefshelper.getflagbit(context, 0)) {
                    String return_addr2 = pop();
                    return_addr2 = pop().substring(0, 2) + return_addr2;
                    prefshelper.saveregister(context, "pc", return_addr2);
                    time = time.substring(time.length() - 2);
                } else {
                    time = time.substring(0, 1);
                }
                break;
            case "F0H":
                Log.d("Ritik", "execute: rp");
                if (prefshelper.getflagbit(context, 7)) {
                    String return_addr3 = pop();
                    return_addr3 = pop().substring(0, 2) + return_addr3;
                    prefshelper.saveregister(context, "pc", return_addr3);
                    time = time.substring(time.length() - 2);
                } else {
                    time = time.substring(0, 1);
                }
                break;
            case "F8H":
                Log.d("Ritik", "execute: rm");
                if (!prefshelper.getflagbit(context, 7)) {
                    String return_addr4 = pop();
                    return_addr4 = pop().substring(0, 2) + return_addr4;
                    prefshelper.saveregister(context, "pc", return_addr4);
                    time = time.substring(time.length() - 2);
                } else {
                    time = time.substring(0, 1);
                }
                break;
            case "C8H":
                Log.d("Ritik", "execute: rz");
                if (prefshelper.getflagbit(context, 6)) {
                    String return_addr5 = pop();
                    return_addr5 = pop().substring(0, 2) + return_addr5;
                    prefshelper.saveregister(context, "pc", return_addr5);
                    time = time.substring(time.length() - 2);
                } else {
                    time = time.substring(0, 1);
                }
                break;
            case "C0H":
                Log.d("Ritik", "execute: rnz");
                if (!prefshelper.getflagbit(context, 6)) {
                    String return_addr6 = pop();
                    return_addr6 = pop().substring(0, 2) + return_addr6;
                    prefshelper.saveregister(context, "pc", return_addr6);
                    time = time.substring(time.length() - 2);
                } else {
                    time = time.substring(0, 1);
                }
                break;
            case "E8H":
                Log.d("Ritik", "execute: rpe");
                if (prefshelper.getflagbit(context, 2)) {
                    String return_addr7 = pop();
                    return_addr7 = pop().substring(0, 2) + return_addr7;
                    prefshelper.saveregister(context, "pc", return_addr7);
                    time = time.substring(time.length() - 2);
                } else {
                    time = time.substring(0, 1);
                }
                break;
            case "E0H":
                Log.d("Ritik", "execute: rpo");
                if (!prefshelper.getflagbit(context, 2)) {
                    String return_addr8 = pop();
                    return_addr8 = pop().substring(0, 2) + return_addr8;
                    prefshelper.saveregister(context, "pc", return_addr8);
                    time = time.substring(time.length() - 2);
                } else {
                    time = time.substring(0, 1);
                }
                break;
            //ret end here

            //add starts here
            case "87H":
                Log.d("Ritik", "execute: add a");
                flags = prefshelper.getregister(context, "f");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "a"));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags = Integer.toString(flags_new[0]) + flags_new[1] + flags.substring(2, 3) + Integer.toString(flags_new[3]) + flags.substring(4, 5) + Integer.toString(flags_new[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                prefshelper.saveregister(context, "a", typeHelper.AddHex(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "a"), 2));
                break;
            case "80H":
                Log.d("Ritik", "execute: add b");
                flags = prefshelper.getregister(context, "f");
                Log.d("Ritik", "execute: before op flags are " + flags);
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "b"));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags = Integer.toString(flags_new[0]) + flags_new[1] + flags.substring(2, 3) + Integer.toString(flags_new[3]) + flags.substring(4, 5) + Integer.toString(flags_new[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7]);
                Log.d("Ritik", "execute: after op flags are " + flags);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                prefshelper.saveregister(context, "a", typeHelper.AddHex(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "b"), 2));
                break;
            case "81H":
                Log.d("Ritik", "execute: add c");
                flags = prefshelper.getregister(context, "f");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "c"));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags = Integer.toString(flags_new[0]) + flags_new[1] + flags.substring(2, 3) + Integer.toString(flags_new[3]) + flags.substring(4, 5) + Integer.toString(flags_new[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                prefshelper.saveregister(context, "a", typeHelper.AddHex(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "c"), 2));
                break;
            case "82H":
                Log.d("Ritik", "execute: add d");
                flags = prefshelper.getregister(context, "f");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "d"));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags = Integer.toString(flags_new[0]) + flags_new[1] + flags.substring(2, 3) + Integer.toString(flags_new[3]) + flags.substring(4, 5) + Integer.toString(flags_new[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                prefshelper.saveregister(context, "a", typeHelper.AddHex(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "d"), 2));
                break;
            case "83H":
                Log.d("Ritik", "execute: add e");
                flags = prefshelper.getregister(context, "f");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "e"));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags = Integer.toString(flags_new[0]) + flags_new[1] + flags.substring(2, 3) + Integer.toString(flags_new[3]) + flags.substring(4, 5) + Integer.toString(flags_new[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                prefshelper.saveregister(context, "a", typeHelper.AddHex(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "e"), 2));
                break;
            case "84H":
                Log.d("Ritik", "execute: add h");
                flags = prefshelper.getregister(context, "f");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "h"));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags = Integer.toString(flags_new[0]) + flags_new[1] + flags.substring(2, 3) + Integer.toString(flags_new[3]) + flags.substring(4, 5) + Integer.toString(flags_new[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                prefshelper.saveregister(context, "a", typeHelper.AddHex(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "h"), 2));
                break;
            case "85H":
                Log.d("Ritik", "execute: add l");
                flags = prefshelper.getregister(context, "f");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "l"));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags = Integer.toString(flags_new[0]) + flags_new[1] + flags.substring(2, 3) + Integer.toString(flags_new[3]) + flags.substring(4, 5) + Integer.toString(flags_new[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                prefshelper.saveregister(context, "a", typeHelper.AddHex(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "l"), 2));
                break;
            case "86H":
                Log.d("Ritik", "execute: add m");
                flags = prefshelper.getregister(context, "f");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), sqLiteHelper.read_memory(prefshelper.getregister(context, "h").substring(0, 2) + prefshelper.getregister(context, "l")));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags = Integer.toString(flags_new[0]) + flags_new[1] + flags.substring(2, 3) + Integer.toString(flags_new[3]) + flags.substring(4, 5) + Integer.toString(flags_new[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                prefshelper.saveregister(context, "a", typeHelper.AddHex(prefshelper.getregister(context, "a"), sqLiteHelper.read_memory(prefshelper.getregister(context, "h").substring(0, 2) + prefshelper.getregister(context, "l")), 2));
                break;
            case "C6H":
                Log.d("Ritik", "execute: adi data");
                flags = prefshelper.getregister(context, "f");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), sqLiteHelper.read_memory(data_address_1));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags = Integer.toString(flags_new[0]) + flags_new[1] + flags.substring(2, 3) + Integer.toString(flags_new[3]) + flags.substring(4, 5) + Integer.toString(flags_new[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                prefshelper.saveregister(context, "a", typeHelper.AddHex(prefshelper.getregister(context, "a"), sqLiteHelper.read_memory(data_address_1), 2));
                break;

            case "8FH":
                Log.d("Ritik", "execute: adc a");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "a"));
                prefshelper.saveregister(context, "a", typeHelper.AddHex(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "a"), 2));
                flags_new2 = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H");
                prefshelper.saveregister(context, "a", typeHelper.AddHex(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H", 2));
                flags = Integer.toString(flags_new2[0]) + Integer.toString(flags_new[1] & flags_new2[1]) + flags.substring(2, 3) + Integer.toString(flags_new[3] | flags_new2[3]) + flags.substring(4, 5) + Integer.toString(flags_new2[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7] | flags_new2[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "88H":
                Log.d("Ritik", "execute: adc b");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "b"));
                prefshelper.saveregister(context, "a", typeHelper.AddHex(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "b"), 2));
                flags_new2 = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H");
                prefshelper.saveregister(context, "a", typeHelper.AddHex(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H", 2));
                flags = Integer.toString(flags_new2[0]) + Integer.toString(flags_new[1] & flags_new2[1]) + flags.substring(2, 3) + Integer.toString(flags_new[3] | flags_new2[3]) + flags.substring(4, 5) + Integer.toString(flags_new2[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7] | flags_new2[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "89H":
                Log.d("Ritik", "execute: adc c");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "c"));
                prefshelper.saveregister(context, "a", typeHelper.AddHex(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "c"), 2));
                flags_new2 = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H");
                prefshelper.saveregister(context, "a", typeHelper.AddHex(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H", 2));
                flags = Integer.toString(flags_new2[0]) + Integer.toString(flags_new[1] & flags_new2[1]) + flags.substring(2, 3) + Integer.toString(flags_new[3] | flags_new2[3]) + flags.substring(4, 5) + Integer.toString(flags_new2[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7] | flags_new2[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "8AH":
                Log.d("Ritik", "execute: adc d");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "d"));
                prefshelper.saveregister(context, "a", typeHelper.AddHex(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "d"), 2));
                flags_new2 = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H");
                prefshelper.saveregister(context, "a", typeHelper.AddHex(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H", 2));
                flags = Integer.toString(flags_new2[0]) + Integer.toString(flags_new[1] & flags_new2[1]) + flags.substring(2, 3) + Integer.toString(flags_new[3] | flags_new2[3]) + flags.substring(4, 5) + Integer.toString(flags_new2[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7] | flags_new2[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "8BH":
                Log.d("Ritik", "execute: adc e");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "e"));
                prefshelper.saveregister(context, "a", typeHelper.AddHex(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "e"), 2));
                flags_new2 = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H");
                prefshelper.saveregister(context, "a", typeHelper.AddHex(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H", 2));
                flags = Integer.toString(flags_new2[0]) + Integer.toString(flags_new[1] & flags_new2[1]) + flags.substring(2, 3) + Integer.toString(flags_new[3] | flags_new2[3]) + flags.substring(4, 5) + Integer.toString(flags_new2[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7] | flags_new2[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "8CH":
                Log.d("Ritik", "execute: adc h");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "h"));
                prefshelper.saveregister(context, "a", typeHelper.AddHex(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "h"), 2));
                flags_new2 = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H");
                prefshelper.saveregister(context, "a", typeHelper.AddHex(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H", 2));
                flags = Integer.toString(flags_new2[0]) + Integer.toString(flags_new[1] & flags_new2[1]) + flags.substring(2, 3) + Integer.toString(flags_new[3] | flags_new2[3]) + flags.substring(4, 5) + Integer.toString(flags_new2[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7] | flags_new2[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "8DH":
                Log.d("Ritik", "execute: adc l");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "l"));
                prefshelper.saveregister(context, "a", typeHelper.AddHex(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "l"), 2));
                flags_new2 = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H");
                prefshelper.saveregister(context, "a", typeHelper.AddHex(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H", 2));
                flags = Integer.toString(flags_new2[0]) + Integer.toString(flags_new[1] & flags_new2[1]) + flags.substring(2, 3) + Integer.toString(flags_new[3] | flags_new2[3]) + flags.substring(4, 5) + Integer.toString(flags_new2[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7] | flags_new2[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "8EH":
                Log.d("Ritik", "execute: adc m");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), sqLiteHelper.read_memory(prefshelper.getregister(context, "h").substring(0, 2) + prefshelper.getregister(context, "l")));
                prefshelper.saveregister(context, "a", typeHelper.AddHex(prefshelper.getregister(context, "a"), sqLiteHelper.read_memory(prefshelper.getregister(context, "h").substring(0, 2) + prefshelper.getregister(context, "l")), 2));
                flags_new2 = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H");
                prefshelper.saveregister(context, "a", typeHelper.AddHex(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H", 2));
                flags = Integer.toString(flags_new2[0]) + Integer.toString(flags_new[1] & flags_new2[1]) + flags.substring(2, 3) + Integer.toString(flags_new[3] | flags_new2[3]) + flags.substring(4, 5) + Integer.toString(flags_new2[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7] | flags_new2[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "CEH":
                Log.d("Ritik", "execute: aci data");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), sqLiteHelper.read_memory(data_address_1));
                prefshelper.saveregister(context, "a", typeHelper.AddHex(prefshelper.getregister(context, "a"), sqLiteHelper.read_memory(data_address_1), 2));
                flags_new2 = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H");
                prefshelper.saveregister(context, "a", typeHelper.AddHex(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H", 2));
                flags = Integer.toString(flags_new2[0]) + Integer.toString(flags_new[1] & flags_new2[1]) + flags.substring(2, 3) + Integer.toString(flags_new[3] | flags_new2[3]) + flags.substring(4, 5) + Integer.toString(flags_new2[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7] | flags_new2[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "09H":
                Log.d("Ritik", "execute: dad b");
                flags_new2 = new int[8];
                Log.d("Ritik", "dad b: 1");
                flags = prefshelper.getregister(context, "f");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "l"), prefshelper.getregister(context, "c"));
                prefshelper.saveregister(context, "l", typeHelper.AddHex(prefshelper.getregister(context, "l"), prefshelper.getregister(context, "c"), 2));
                if (flags_new[7] == 1) {
                    flags_new2 = typeHelper.getFlagadd(prefshelper.getregister(context, "h"), "01H");
                    prefshelper.saveregister(context, "h", typeHelper.AddHex(prefshelper.getregister(context, "h"), "01H", 2));
                }
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "h"), prefshelper.getregister(context, "b"));
                prefshelper.saveregister(context, "h", typeHelper.AddHex(prefshelper.getregister(context, "h"), prefshelper.getregister(context, "b"), 2));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2)).substring(0, 7) + (flags_new[7] | flags_new2[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "19H":
                Log.d("Ritik", "execute: dad d");
                flags_new2 = new int[8];
                flags = prefshelper.getregister(context, "f");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "l"), prefshelper.getregister(context, "e"));
                prefshelper.saveregister(context, "l", typeHelper.AddHex(prefshelper.getregister(context, "l"), prefshelper.getregister(context, "e"), 2));
                if (flags_new[7] == 1) {
                    flags_new2 = typeHelper.getFlagadd(prefshelper.getregister(context, "h"), "01H");
                    prefshelper.saveregister(context, "h", typeHelper.AddHex(prefshelper.getregister(context, "h"), "01H", 2));
                }
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "h"), prefshelper.getregister(context, "d"));
                prefshelper.saveregister(context, "h", typeHelper.AddHex(prefshelper.getregister(context, "h"), prefshelper.getregister(context, "d"), 2));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2)).substring(0, 7) + (flags_new[7] | flags_new2[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "29H":
                Log.d("Ritik", "execute: dad h");
                flags_new2 = new int[8];
                flags = prefshelper.getregister(context, "f");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "l"), prefshelper.getregister(context, "l"));
                prefshelper.saveregister(context, "l", typeHelper.AddHex(prefshelper.getregister(context, "l"), prefshelper.getregister(context, "l"), 2));
                String h_orig = prefshelper.getregister(context, "h");
                if (flags_new[7] == 1) {
                    flags_new2 = typeHelper.getFlagadd(prefshelper.getregister(context, "h"), "01H");
                    prefshelper.saveregister(context, "h", typeHelper.AddHex(prefshelper.getregister(context, "h"), "01H", 2));
                }
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "h"), h_orig);
                prefshelper.saveregister(context, "h", typeHelper.AddHex(prefshelper.getregister(context, "h"), h_orig, 2));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2)).substring(0, 7) + (flags_new[7] | flags_new2[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "39H":
                Log.d("Ritik", "execute: dad sp");
                flags_new2 = new int[8];
                flags = prefshelper.getregister(context, "f");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "l"), prefshelper.getregister(context, "sp").substring(2));
                prefshelper.saveregister(context, "l", typeHelper.AddHex(prefshelper.getregister(context, "l"), prefshelper.getregister(context, "sp").substring(2), 2));
                if (flags_new[7] == 1) {
                    flags_new2 = typeHelper.getFlagadd(prefshelper.getregister(context, "h"), "01H");
                    prefshelper.saveregister(context, "h", typeHelper.AddHex(prefshelper.getregister(context, "h"), "01H", 2));
                }
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "h"), prefshelper.getregister(context, "sp").substring(0, 2) + "H");
                prefshelper.saveregister(context, "h", typeHelper.AddHex(prefshelper.getregister(context, "h"), prefshelper.getregister(context, "sp").substring(0, 2) + "H", 2));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2)).substring(0, 7) + (flags_new[7] | flags_new2[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;


            case "3CH":
                Log.d("Ritik", "execute: inr a");
                flags = prefshelper.getregister(context, "f");
                Log.d("Ritik", "inr : 1");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                Log.d("Ritik", "inr : 2");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "01H");
                Log.d("Ritik", "inr : 3");
                prefshelper.saveregister(context, "a", typeHelper.AddHex(prefshelper.getregister(context, "a"), "01H", 2));
                Log.d("Ritik", "inr : 4");
                flags = Integer.toString(flags_new[0]) + Integer.toString(flags_new[1]) + Integer.toString(1) + Integer.toString(flags_new[3]) + Integer.toString(0) + Integer.toString(flags_new[5]) + Integer.toString(0) + flags.substring(7);
                Log.d("Ritik", "inr : 5 " + flags);
                flags = typeHelper.Bin2Hex(flags);
                Log.d("Ritik", "inr : 6");
                prefshelper.saveregister(context, "f", new String(new char[2 - flags.length()]).replace('\0', '0') + flags + "H");
                Log.d("Ritik", "inr : 7");
                break;
            case "04H":
                Log.d("Ritik", "execute: inr b");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "b"), "01H");
                prefshelper.saveregister(context, "b", typeHelper.AddHex(prefshelper.getregister(context, "b"), "01H", 2));
                flags = Integer.toString(flags_new[0]) + Integer.toString(flags_new[1]) + Integer.toString(1) + Integer.toString(flags_new[3]) + Integer.toString(0) + Integer.toString(flags_new[5]) + Integer.toString(0) + flags.substring(7);
                flags = typeHelper.Bin2Hex(flags);
                prefshelper.saveregister(context, "f", new String(new char[2 - flags.length()]).replace('\0', '0') + flags + "H");
                break;
            case "0CH":
                Log.d("Ritik", "execute: inr c");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "c"), "01H");
                prefshelper.saveregister(context, "c", typeHelper.AddHex(prefshelper.getregister(context, "c"), "01H", 2));
                flags = Integer.toString(flags_new[0]) + Integer.toString(flags_new[1]) + Integer.toString(1) + Integer.toString(flags_new[3]) + Integer.toString(0) + Integer.toString(flags_new[5]) + Integer.toString(0) + flags.substring(7);
                flags = typeHelper.Bin2Hex(flags);
                prefshelper.saveregister(context, "f", new String(new char[2 - flags.length()]).replace('\0', '0') + flags + "H");
                break;
            case "14H":
                Log.d("Ritik", "execute: inr d");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "d"), "01H");
                prefshelper.saveregister(context, "d", typeHelper.AddHex(prefshelper.getregister(context, "d"), "01H", 2));
                flags = Integer.toString(flags_new[0]) + Integer.toString(flags_new[1]) + Integer.toString(1) + Integer.toString(flags_new[3]) + Integer.toString(0) + Integer.toString(flags_new[5]) + Integer.toString(0) + flags.substring(7);
                flags = typeHelper.Bin2Hex(flags);
                prefshelper.saveregister(context, "f", new String(new char[2 - flags.length()]).replace('\0', '0') + flags + "H");
                break;
            case "1CH":
                Log.d("Ritik", "execute: inr e");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "e"), "01H");
                prefshelper.saveregister(context, "e", typeHelper.AddHex(prefshelper.getregister(context, "e"), "01H", 2));
                flags = Integer.toString(flags_new[0]) + Integer.toString(flags_new[1]) + Integer.toString(1) + Integer.toString(flags_new[3]) + Integer.toString(0) + Integer.toString(flags_new[5]) + Integer.toString(0) + flags.substring(7);
                flags = typeHelper.Bin2Hex(flags);
                prefshelper.saveregister(context, "f", new String(new char[2 - flags.length()]).replace('\0', '0') + flags + "H");
                break;
            case "24H":
                Log.d("Ritik", "execute: inr h");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "h"), "01H");
                prefshelper.saveregister(context, "h", typeHelper.AddHex(prefshelper.getregister(context, "h"), "01H", 2));
                flags = Integer.toString(flags_new[0]) + Integer.toString(flags_new[1]) + Integer.toString(1) + Integer.toString(flags_new[3]) + Integer.toString(0) + Integer.toString(flags_new[5]) + Integer.toString(0) + flags.substring(7);
                flags = typeHelper.Bin2Hex(flags);
                prefshelper.saveregister(context, "f", new String(new char[2 - flags.length()]).replace('\0', '0') + flags + "H");
                break;
            case "2CH":
                Log.d("Ritik", "execute: inr l");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "l"), "01H");
                prefshelper.saveregister(context, "l", typeHelper.AddHex(prefshelper.getregister(context, "l"), "01H", 2));
                flags = Integer.toString(flags_new[0]) + Integer.toString(flags_new[1]) + Integer.toString(1) + Integer.toString(flags_new[3]) + Integer.toString(0) + Integer.toString(flags_new[5]) + Integer.toString(0) + flags.substring(7);
                flags = typeHelper.Bin2Hex(flags);
                prefshelper.saveregister(context, "f", new String(new char[2 - flags.length()]).replace('\0', '0') + flags + "H");
                break;
            case "34H":
                Log.d("Ritik", "execute: inr m");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                temp = sqLiteHelper.read_memory(prefshelper.getregister(context, "h").substring(0, 2) + prefshelper.getregister(context, "l"));
                flags_new = typeHelper.getFlagadd(sqLiteHelper.read_memory(temp), "01H");
                sqLiteHelper.save_memory(temp, typeHelper.AddHex(sqLiteHelper.read_memory(temp), "01H", 2));
                flags = Integer.toString(flags_new[0]) + Integer.toString(flags_new[1]) + Integer.toString(1) + Integer.toString(flags_new[3]) + Integer.toString(0) + Integer.toString(flags_new[5]) + Integer.toString(0) + flags.substring(7);
                flags = typeHelper.Bin2Hex(flags);
                prefshelper.saveregister(context, "f", new String(new char[2 - flags.length()]).replace('\0', '0') + flags + "H");
                break;
            case "03H":
                Log.d("Ritik", "execute: inx b");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "c"), "01H");
                prefshelper.saveregister(context, "c", typeHelper.AddHex(prefshelper.getregister(context, "c"), "01H", 2));
                prefshelper.saveregister(context, "b", typeHelper.AddHex(prefshelper.getregister(context, "b"), "0" + flags_new[7] + "H", 2));
                break;
            case "13H":
                Log.d("Ritik", "execute: inx d");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "e"), "01H");
                prefshelper.saveregister(context, "e", typeHelper.AddHex(prefshelper.getregister(context, "e"), "01H", 2));
                prefshelper.saveregister(context, "d", typeHelper.AddHex(prefshelper.getregister(context, "d"), "0" + flags_new[7] + "H", 2));
                break;
            case "23H":
                Log.d("Ritik", "execute: inx h");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "l"), "01H");
                prefshelper.saveregister(context, "l", typeHelper.AddHex(prefshelper.getregister(context, "l"), "01H", 2));
                prefshelper.saveregister(context, "h", typeHelper.AddHex(prefshelper.getregister(context, "h"), "0" + flags_new[7] + "H", 2));
                break;
            case "33H":
                Log.d("Ritik", "execute: inx sp");
                prefshelper.saveregister(context, "sp", typeHelper.AddHex(prefshelper.getregister(context, "sp"), "0001H", 4));
                break;


            //add end here

            //sub starts here
            case "97H":
                Log.d("Ritik", "execute: sub a");
                flags = prefshelper.getregister(context, "f");
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "a"));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags = Integer.toString(flags_new[0]) + flags_new[1] + flags.substring(2, 3) + Integer.toString(flags_new[3]) + flags.substring(4, 5) + Integer.toString(flags_new[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                prefshelper.saveregister(context, "a", typeHelper.SubHex(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "a"), 2));
                break;
            case "90H":
                Log.d("Ritik", "execute: sub b");
                flags = prefshelper.getregister(context, "f");
                Log.d("Ritik", "execute: before op flags are " + flags);
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "b"));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags = Integer.toString(flags_new[0]) + flags_new[1] + flags.substring(2, 3) + Integer.toString(flags_new[3]) + flags.substring(4, 5) + Integer.toString(flags_new[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7]);
                Log.d("Ritik", "execute: after op flags are " + flags);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                prefshelper.saveregister(context, "a", typeHelper.SubHex(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "b"), 2));
                break;
            case "91H":
                Log.d("Ritik", "execute: sub c");
                flags = prefshelper.getregister(context, "f");
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "c"));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags = Integer.toString(flags_new[0]) + flags_new[1] + flags.substring(2, 3) + Integer.toString(flags_new[3]) + flags.substring(4, 5) + Integer.toString(flags_new[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                prefshelper.saveregister(context, "a", typeHelper.SubHex(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "c"), 2));
                break;
            case "92H":
                Log.d("Ritik", "execute: sub d");
                flags = prefshelper.getregister(context, "f");
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "d"));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags = Integer.toString(flags_new[0]) + flags_new[1] + flags.substring(2, 3) + Integer.toString(flags_new[3]) + flags.substring(4, 5) + Integer.toString(flags_new[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                prefshelper.saveregister(context, "a", typeHelper.SubHex(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "d"), 2));
                break;
            case "93H":
                Log.d("Ritik", "execute: sub e");
                flags = prefshelper.getregister(context, "f");
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "e"));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags = Integer.toString(flags_new[0]) + flags_new[1] + flags.substring(2, 3) + Integer.toString(flags_new[3]) + flags.substring(4, 5) + Integer.toString(flags_new[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                prefshelper.saveregister(context, "a", typeHelper.SubHex(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "e"), 2));
                break;
            case "94H":
                Log.d("Ritik", "execute: sub h");
                flags = prefshelper.getregister(context, "f");
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "h"));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags = Integer.toString(flags_new[0]) + flags_new[1] + flags.substring(2, 3) + Integer.toString(flags_new[3]) + flags.substring(4, 5) + Integer.toString(flags_new[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                prefshelper.saveregister(context, "a", typeHelper.SubHex(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "h"), 2));
                break;
            case "95H":
                Log.d("Ritik", "execute: sub l");
                flags = prefshelper.getregister(context, "f");
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "l"));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags = Integer.toString(flags_new[0]) + flags_new[1] + flags.substring(2, 3) + Integer.toString(flags_new[3]) + flags.substring(4, 5) + Integer.toString(flags_new[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                prefshelper.saveregister(context, "a", typeHelper.SubHex(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "l"), 2));
                break;
            case "96H":
                Log.d("Ritik", "execute: sub m");
                flags = prefshelper.getregister(context, "f");
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), sqLiteHelper.read_memory(prefshelper.getregister(context, "h").substring(0, 2) + prefshelper.getregister(context, "l")));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags = Integer.toString(flags_new[0]) + flags_new[1] + flags.substring(2, 3) + Integer.toString(flags_new[3]) + flags.substring(4, 5) + Integer.toString(flags_new[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                prefshelper.saveregister(context, "a", typeHelper.SubHex(prefshelper.getregister(context, "a"), sqLiteHelper.read_memory(prefshelper.getregister(context, "h").substring(0, 2) + prefshelper.getregister(context, "l")), 2));
                break;
            case "D6H":
                Log.d("Ritik", "execute: sui data");
                flags = prefshelper.getregister(context, "f");
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), sqLiteHelper.read_memory(data_address_1));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags = Integer.toString(flags_new[0]) + flags_new[1] + flags.substring(2, 3) + Integer.toString(flags_new[3]) + flags.substring(4, 5) + Integer.toString(flags_new[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                prefshelper.saveregister(context, "a", typeHelper.SubHex(prefshelper.getregister(context, "a"), sqLiteHelper.read_memory(data_address_1), 2));
                break;


            case "3DH":
                Log.d("Ritik", "execute: dcr a");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), "01H");
                prefshelper.saveregister(context, "a", typeHelper.SubHex(prefshelper.getregister(context, "a"), "01H", 2));
                flags = Integer.toString(flags_new[0]) + Integer.toString(flags_new[1]) + Integer.toString(1) + Integer.toString(flags_new[3]) + Integer.toString(0) + Integer.toString(flags_new[5]) + Integer.toString(0) + flags.substring(7);
                flags = typeHelper.Bin2Hex(flags);
                prefshelper.saveregister(context, "f", new String(new char[2 - flags.length()]).replace('\0', '0') + flags + "H");
                break;
            case "05H":
                Log.d("Ritik", "execute: dcr b");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "b"), "01H");
                prefshelper.saveregister(context, "b", typeHelper.SubHex(prefshelper.getregister(context, "b"), "01H", 2));
                flags = Integer.toString(flags_new[0]) + Integer.toString(flags_new[1]) + Integer.toString(1) + Integer.toString(flags_new[3]) + Integer.toString(0) + Integer.toString(flags_new[5]) + Integer.toString(0) + flags.substring(7);
                flags = typeHelper.Bin2Hex(flags);
                prefshelper.saveregister(context, "f", new String(new char[2 - flags.length()]).replace('\0', '0') + flags + "H");
                break;
            case "0DH":
                Log.d("Ritik", "execute: dcr c");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "c"), "01H");
                prefshelper.saveregister(context, "c", typeHelper.SubHex(prefshelper.getregister(context, "c"), "01H", 2));
                flags = Integer.toString(flags_new[0]) + Integer.toString(flags_new[1]) + Integer.toString(1) + Integer.toString(flags_new[3]) + Integer.toString(0) + Integer.toString(flags_new[5]) + Integer.toString(0) + flags.substring(7);
                flags = typeHelper.Bin2Hex(flags);
                prefshelper.saveregister(context, "f", new String(new char[2 - flags.length()]).replace('\0', '0') + flags + "H");
                break;
            case "15H":
                Log.d("Ritik", "execute: dcr d");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "d"), "01H");
                prefshelper.saveregister(context, "d", typeHelper.SubHex(prefshelper.getregister(context, "d"), "01H", 2));
                flags = Integer.toString(flags_new[0]) + Integer.toString(flags_new[1]) + Integer.toString(1) + Integer.toString(flags_new[3]) + Integer.toString(0) + Integer.toString(flags_new[5]) + Integer.toString(0) + flags.substring(7);
                flags = typeHelper.Bin2Hex(flags);
                prefshelper.saveregister(context, "f", new String(new char[2 - flags.length()]).replace('\0', '0') + flags + "H");
                break;
            case "1DH":
                Log.d("Ritik", "execute: dcr e");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "e"), "01H");
                prefshelper.saveregister(context, "e", typeHelper.SubHex(prefshelper.getregister(context, "e"), "01H", 2));
                flags = Integer.toString(flags_new[0]) + Integer.toString(flags_new[1]) + Integer.toString(1) + Integer.toString(flags_new[3]) + Integer.toString(0) + Integer.toString(flags_new[5]) + Integer.toString(0) + flags.substring(7);
                flags = typeHelper.Bin2Hex(flags);
                prefshelper.saveregister(context, "f", new String(new char[2 - flags.length()]).replace('\0', '0') + flags + "H");
                break;
            case "25H":
                Log.d("Ritik", "execute: dcr h");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "h"), "01H");
                prefshelper.saveregister(context, "h", typeHelper.SubHex(prefshelper.getregister(context, "h"), "01H", 2));
                flags = Integer.toString(flags_new[0]) + Integer.toString(flags_new[1]) + Integer.toString(1) + Integer.toString(flags_new[3]) + Integer.toString(0) + Integer.toString(flags_new[5]) + Integer.toString(0) + flags.substring(7);
                flags = typeHelper.Bin2Hex(flags);
                prefshelper.saveregister(context, "f", new String(new char[2 - flags.length()]).replace('\0', '0') + flags + "H");
                break;
            case "2DH":
                Log.d("Ritik", "execute: dcr l");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "l"), "01H");
                prefshelper.saveregister(context, "l", typeHelper.SubHex(prefshelper.getregister(context, "l"), "01H", 2));
                flags = Integer.toString(flags_new[0]) + Integer.toString(flags_new[1]) + Integer.toString(1) + Integer.toString(flags_new[3]) + Integer.toString(0) + Integer.toString(flags_new[5]) + Integer.toString(0) + flags.substring(7);
                flags = typeHelper.Bin2Hex(flags);
                prefshelper.saveregister(context, "f", new String(new char[2 - flags.length()]).replace('\0', '0') + flags + "H");
                break;
            case "35H":
                Log.d("Ritik", "execute: dcr m");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                temp = sqLiteHelper.read_memory(prefshelper.getregister(context, "h").substring(0, 2) + prefshelper.getregister(context, "l"));
                flags_new = typeHelper.getFlagsub(sqLiteHelper.read_memory(temp), "01H");
                sqLiteHelper.save_memory(temp, typeHelper.SubHex(sqLiteHelper.read_memory(temp), "01H", 2));
                flags = Integer.toString(flags_new[0]) + Integer.toString(flags_new[1]) + Integer.toString(1) + Integer.toString(flags_new[3]) + Integer.toString(0) + Integer.toString(flags_new[5]) + Integer.toString(0) + flags.substring(7);
                flags = typeHelper.Bin2Hex(flags);
                prefshelper.saveregister(context, "f", new String(new char[2 - flags.length()]).replace('\0', '0') + flags + "H");
                break;
            case "0BH":
                Log.d("Ritik", "execute: dcx b");
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "c"), "01H");
                prefshelper.saveregister(context, "c", typeHelper.SubHex(prefshelper.getregister(context, "c"), "01H", 2));
                prefshelper.saveregister(context, "b", typeHelper.SubHex(prefshelper.getregister(context, "b"), "0" + flags_new[7] + "H", 2));
                break;
            case "1BH":
                Log.d("Ritik", "execute: dcx d");
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "e"), "01H");
                prefshelper.saveregister(context, "e", typeHelper.SubHex(prefshelper.getregister(context, "e"), "01H", 2));
                prefshelper.saveregister(context, "d", typeHelper.SubHex(prefshelper.getregister(context, "d"), "0" + flags_new[7] + "H", 2));
                break;
            case "2BH":
                Log.d("Ritik", "execute: dcx h");
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "l"), "01H");
                prefshelper.saveregister(context, "l", typeHelper.SubHex(prefshelper.getregister(context, "l"), "01H", 2));
                prefshelper.saveregister(context, "h", typeHelper.SubHex(prefshelper.getregister(context, "h"), "0" + flags_new[7] + "H", 2));
                break;
            case "3BH":
                Log.d("Ritik", "execute: dcx sp");
                prefshelper.saveregister(context, "sp", typeHelper.SubHex(prefshelper.getregister(context, "sp"), "0001H", 4));
                break;

            case "9FH":
                Log.d("Ritik", "execute: sbb a");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "a"));
                prefshelper.saveregister(context, "a", typeHelper.SubHex(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "a"), 2));
                flags_new2 = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H");
                prefshelper.saveregister(context, "a", typeHelper.SubHex(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H", 2));
                flags = Integer.toString(flags_new2[0]) + Integer.toString(flags_new[1] & flags_new2[1]) + flags.substring(2, 3) + Integer.toString(flags_new[3] | flags_new2[3]) + flags.substring(4, 5) + Integer.toString(flags_new2[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7] | flags_new2[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "98H":
                Log.d("Ritik", "execute: sbb b");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "b"));
                prefshelper.saveregister(context, "a", typeHelper.SubHex(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "b"), 2));
                flags_new2 = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H");
                prefshelper.saveregister(context, "a", typeHelper.SubHex(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H", 2));
                flags = Integer.toString(flags_new2[0]) + Integer.toString(flags_new[1] & flags_new2[1]) + flags.substring(2, 3) + Integer.toString(flags_new[3] | flags_new2[3]) + flags.substring(4, 5) + Integer.toString(flags_new2[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7] | flags_new2[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "99H":
                Log.d("Ritik", "execute: sbb c");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "c"));
                prefshelper.saveregister(context, "a", typeHelper.SubHex(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "c"), 2));
                flags_new2 = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H");
                prefshelper.saveregister(context, "a", typeHelper.SubHex(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H", 2));
                flags = Integer.toString(flags_new2[0]) + Integer.toString(flags_new[1] & flags_new2[1]) + flags.substring(2, 3) + Integer.toString(flags_new[3] | flags_new2[3]) + flags.substring(4, 5) + Integer.toString(flags_new2[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7] | flags_new2[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "9AH":
                Log.d("Ritik", "execute: sbb d");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "d"));
                prefshelper.saveregister(context, "a", typeHelper.SubHex(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "d"), 2));
                flags_new2 = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H");
                prefshelper.saveregister(context, "a", typeHelper.SubHex(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H", 2));
                flags = Integer.toString(flags_new2[0]) + Integer.toString(flags_new[1] & flags_new2[1]) + flags.substring(2, 3) + Integer.toString(flags_new[3] | flags_new2[3]) + flags.substring(4, 5) + Integer.toString(flags_new2[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7] | flags_new2[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "9BH":
                Log.d("Ritik", "execute: sbb e");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "e"));
                prefshelper.saveregister(context, "a", typeHelper.SubHex(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "e"), 2));
                flags_new2 = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H");
                prefshelper.saveregister(context, "a", typeHelper.SubHex(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H", 2));
                flags = Integer.toString(flags_new2[0]) + Integer.toString(flags_new[1] & flags_new2[1]) + flags.substring(2, 3) + Integer.toString(flags_new[3] | flags_new2[3]) + flags.substring(4, 5) + Integer.toString(flags_new2[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7] | flags_new2[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "9CH":
                Log.d("Ritik", "execute: sbb h");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "h"));
                prefshelper.saveregister(context, "a", typeHelper.SubHex(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "h"), 2));
                flags_new2 = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H");
                prefshelper.saveregister(context, "a", typeHelper.SubHex(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H", 2));
                flags = Integer.toString(flags_new2[0]) + Integer.toString(flags_new[1] & flags_new2[1]) + flags.substring(2, 3) + Integer.toString(flags_new[3] | flags_new2[3]) + flags.substring(4, 5) + Integer.toString(flags_new2[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7] | flags_new2[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "9DH":
                Log.d("Ritik", "execute: sbb l");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "l"));
                prefshelper.saveregister(context, "a", typeHelper.SubHex(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "l"), 2));
                flags_new2 = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H");
                prefshelper.saveregister(context, "a", typeHelper.SubHex(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H", 2));
                flags = Integer.toString(flags_new2[0]) + Integer.toString(flags_new[1] & flags_new2[1]) + flags.substring(2, 3) + Integer.toString(flags_new[3] | flags_new2[3]) + flags.substring(4, 5) + Integer.toString(flags_new2[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7] | flags_new2[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "9EH":
                Log.d("Ritik", "execute: sbb m");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), sqLiteHelper.read_memory(prefshelper.getregister(context, "h").substring(0, 2) + prefshelper.getregister(context, "l")));
                prefshelper.saveregister(context, "a", typeHelper.SubHex(prefshelper.getregister(context, "a"), sqLiteHelper.read_memory(prefshelper.getregister(context, "h").substring(0, 2) + prefshelper.getregister(context, "l")), 2));
                flags_new2 = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H");
                prefshelper.saveregister(context, "a", typeHelper.SubHex(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H", 2));
                flags = Integer.toString(flags_new2[0]) + Integer.toString(flags_new[1] & flags_new2[1]) + flags.substring(2, 3) + Integer.toString(flags_new[3] | flags_new2[3]) + flags.substring(4, 5) + Integer.toString(flags_new2[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7] | flags_new2[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "DEH":
                Log.d("Ritik", "execute: sbi data");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), sqLiteHelper.read_memory(data_address_1));
                prefshelper.saveregister(context, "a", typeHelper.SubHex(prefshelper.getregister(context, "a"), sqLiteHelper.read_memory(data_address_1), 2));
                flags_new2 = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H");
                prefshelper.saveregister(context, "a", typeHelper.SubHex(prefshelper.getregister(context, "a"), "0" + flags.substring(7) + "H", 2));
                flags = Integer.toString(flags_new2[0]) + Integer.toString(flags_new[1] & flags_new2[1]) + flags.substring(2, 3) + Integer.toString(flags_new[3] | flags_new2[3]) + flags.substring(4, 5) + Integer.toString(flags_new2[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7] | flags_new2[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;


            case "BFH":
                Log.d("Ritik", "execute: cmp a");
                flags = prefshelper.getregister(context, "f");
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "a"));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags = Integer.toString(flags_new[0]) + flags_new[1] + flags.substring(2, 3) + Integer.toString(flags_new[3]) + flags.substring(4, 5) + Integer.toString(flags_new[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "B8H":
                Log.d("Ritik", "execute: cmp b");
                flags = prefshelper.getregister(context, "f");
                Log.d("Ritik", "execute: before op flags are " + flags);
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "b"));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags = Integer.toString(flags_new[0]) + flags_new[1] + flags.substring(2, 3) + Integer.toString(flags_new[3]) + flags.substring(4, 5) + Integer.toString(flags_new[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7]);
                Log.d("Ritik", "execute: after op flags are " + flags);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "B9H":
                Log.d("Ritik", "execute: cmp c");
                flags = prefshelper.getregister(context, "f");
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "c"));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags = Integer.toString(flags_new[0]) + flags_new[1] + flags.substring(2, 3) + Integer.toString(flags_new[3]) + flags.substring(4, 5) + Integer.toString(flags_new[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "BAH":
                Log.d("Ritik", "execute: cmp d");
                flags = prefshelper.getregister(context, "f");
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "d"));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags = Integer.toString(flags_new[0]) + flags_new[1] + flags.substring(2, 3) + Integer.toString(flags_new[3]) + flags.substring(4, 5) + Integer.toString(flags_new[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "BBH":
                Log.d("Ritik", "execute: cmp e");
                flags = prefshelper.getregister(context, "f");
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "e"));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags = Integer.toString(flags_new[0]) + flags_new[1] + flags.substring(2, 3) + Integer.toString(flags_new[3]) + flags.substring(4, 5) + Integer.toString(flags_new[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "BCH":
                Log.d("Ritik", "execute: cmp h");
                flags = prefshelper.getregister(context, "f");
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "h"));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags = Integer.toString(flags_new[0]) + flags_new[1] + flags.substring(2, 3) + Integer.toString(flags_new[3]) + flags.substring(4, 5) + Integer.toString(flags_new[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "BDH":
                Log.d("Ritik", "execute: cmp l");
                flags = prefshelper.getregister(context, "f");
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), prefshelper.getregister(context, "l"));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags = Integer.toString(flags_new[0]) + flags_new[1] + flags.substring(2, 3) + Integer.toString(flags_new[3]) + flags.substring(4, 5) + Integer.toString(flags_new[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "BEH":
                Log.d("Ritik", "execute: cmp m");
                flags = prefshelper.getregister(context, "f");
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), sqLiteHelper.read_memory(prefshelper.getregister(context, "h").substring(0, 2) + prefshelper.getregister(context, "l")));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags = Integer.toString(flags_new[0]) + flags_new[1] + flags.substring(2, 3) + Integer.toString(flags_new[3]) + flags.substring(4, 5) + Integer.toString(flags_new[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "FEH":
                Log.d("Ritik", "execute: cpi data");
                flags = prefshelper.getregister(context, "f");
                flags_new = typeHelper.getFlagsub(prefshelper.getregister(context, "a"), sqLiteHelper.read_memory(data_address_1));
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                flags = Integer.toString(flags_new[0]) + flags_new[1] + flags.substring(2, 3) + Integer.toString(flags_new[3]) + flags.substring(4, 5) + Integer.toString(flags_new[5]) + flags.substring(6, 7) + Integer.toString(flags_new[7]);
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;


            //sub ends here


            //logical starts here
            case "A7H":
                Log.d("Ritik", "execute: ana a");
                temp = Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "a").substring(0, 2))) & Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "a").substring(0, 2))));
                temp = typeHelper.Dec2Hex(temp);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp.length()]).replace('\0', '0') + temp + "H");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "00H");
                flags = flags_new[0] + Integer.toString(flags_new[1]) + Integer.toString(1) + 1 + Integer.toString(0) + flags_new[5] + Integer.toString(0) + 0;
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "A0H":
                Log.d("Ritik", "execute: ana b");
                temp = Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "a").substring(0, 2))) & Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "b").substring(0, 2))));
                temp = typeHelper.Dec2Hex(temp);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp.length()]).replace('\0', '0') + temp + "H");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "00H");
                flags = flags_new[0] + Integer.toString(flags_new[1]) + Integer.toString(1) + 1 + Integer.toString(0) + flags_new[5] + Integer.toString(0) + 0;
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "A1H":
                Log.d("Ritik", "execute: ana c");
                temp = Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "a").substring(0, 2))) & Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "c").substring(0, 2))));
                temp = typeHelper.Dec2Hex(temp);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp.length()]).replace('\0', '0') + temp + "H");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "00H");
                flags = flags_new[0] + Integer.toString(flags_new[1]) + Integer.toString(1) + 1 + Integer.toString(0) + flags_new[5] + Integer.toString(0) + 0;
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "A2H":
                Log.d("Ritik", "execute: ana d");
                temp = Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "a").substring(0, 2))) & Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "d").substring(0, 2))));
                temp = typeHelper.Dec2Hex(temp);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp.length()]).replace('\0', '0') + temp + "H");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "00H");
                flags = flags_new[0] + Integer.toString(flags_new[1]) + Integer.toString(1) + 1 + Integer.toString(0) + flags_new[5] + Integer.toString(0) + 0;
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "A3H":
                Log.d("Ritik", "execute: ana e");
                temp = Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "a").substring(0, 2))) & Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "e").substring(0, 2))));
                temp = typeHelper.Dec2Hex(temp);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp.length()]).replace('\0', '0') + temp + "H");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "00H");
                flags = flags_new[0] + Integer.toString(flags_new[1]) + Integer.toString(1) + 1 + Integer.toString(0) + flags_new[5] + Integer.toString(0) + 0;
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "A4H":
                Log.d("Ritik", "execute: ana h");
                temp = Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "a").substring(0, 2))) & Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "h").substring(0, 2))));
                temp = typeHelper.Dec2Hex(temp);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp.length()]).replace('\0', '0') + temp + "H");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "00H");
                flags = flags_new[0] + Integer.toString(flags_new[1]) + Integer.toString(1) + 1 + Integer.toString(0) + flags_new[5] + Integer.toString(0) + 0;
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "A5H":
                Log.d("Ritik", "execute: ana l");
                temp = Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "a").substring(0, 2))) & Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "l").substring(0, 2))));
                temp = typeHelper.Dec2Hex(temp);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp.length()]).replace('\0', '0') + temp + "H");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "00H");
                flags = flags_new[0] + Integer.toString(flags_new[1]) + Integer.toString(1) + 1 + Integer.toString(0) + flags_new[5] + Integer.toString(0) + 0;
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "A6H":
                Log.d("Ritik", "execute: ana m");
                temp = Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "a").substring(0, 2))) & Integer.parseInt(sqLiteHelper.read_memory(data_address_1).substring(0, 2)));
                temp = typeHelper.Dec2Hex(temp);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp.length()]).replace('\0', '0') + temp + "H");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "00H");
                flags = flags_new[0] + Integer.toString(flags_new[1]) + Integer.toString(1) + 1 + Integer.toString(0) + flags_new[5] + Integer.toString(0) + 0;
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "E6H":
                Log.d("Ritik", "execute: ani data");
                temp = Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "a").substring(0, 2))) & Integer.parseInt(typeHelper.Hex2Dec(sqLiteHelper.read_memory(data_address_1).substring(0, 2))));
                temp = typeHelper.Dec2Hex(temp);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp.length()]).replace('\0', '0') + temp + "H");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "00H");
                flags = flags_new[0] + Integer.toString(flags_new[1]) + Integer.toString(1) + 1 + Integer.toString(0) + flags_new[5] + Integer.toString(0) + 0;
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "B7H":
                Log.d("Ritik", "execute: ora a");
                temp = Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "a").substring(0, 2))) | Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "a").substring(0, 2))));
                temp = typeHelper.Dec2Hex(temp);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp.length()]).replace('\0', '0') + temp + "H");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "00H");
                flags = flags_new[0] + Integer.toString(flags_new[1]) + Integer.toString(1) + 0 + Integer.toString(0) + flags_new[5] + Integer.toString(0) + 0;
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "B0H":
                Log.d("Ritik", "execute: ora b");
                temp = Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "a").substring(0, 2))) | Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "b").substring(0, 2))));
                temp = typeHelper.Dec2Hex(temp);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp.length()]).replace('\0', '0') + temp + "H");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "00H");
                flags = flags_new[0] + Integer.toString(flags_new[1]) + Integer.toString(1) + 0 + Integer.toString(0) + flags_new[5] + Integer.toString(0) + 0;
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "B1H":
                Log.d("Ritik", "execute: ora c");
                temp = Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "a").substring(0, 2))) | Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "c").substring(0, 2))));
                temp = typeHelper.Dec2Hex(temp);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp.length()]).replace('\0', '0') + temp + "H");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "00H");
                flags = flags_new[0] + Integer.toString(flags_new[1]) + Integer.toString(1) + 0 + Integer.toString(0) + flags_new[5] + Integer.toString(0) + 0;
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "B2H":
                Log.d("Ritik", "execute: ora d");
                temp = Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "a").substring(0, 2))) | Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "d").substring(0, 2))));
                temp = typeHelper.Dec2Hex(temp);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp.length()]).replace('\0', '0') + temp + "H");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "00H");
                flags = flags_new[0] + Integer.toString(flags_new[1]) + Integer.toString(1) + 0 + Integer.toString(0) + flags_new[5] + Integer.toString(0) + 0;
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "B3H":
                Log.d("Ritik", "execute: ora e");
                temp = Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "a").substring(0, 2))) | Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "e").substring(0, 2))));
                temp = typeHelper.Dec2Hex(temp);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp.length()]).replace('\0', '0') + temp + "H");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "00H");
                flags = flags_new[0] + Integer.toString(flags_new[1]) + Integer.toString(1) + 0 + Integer.toString(0) + flags_new[5] + Integer.toString(0) + 0;
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "B4H":
                Log.d("Ritik", "execute: ora h");
                temp = Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "a").substring(0, 2))) | Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "h").substring(0, 2))));
                temp = typeHelper.Dec2Hex(temp);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp.length()]).replace('\0', '0') + temp + "H");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "00H");
                flags = flags_new[0] + Integer.toString(flags_new[1]) + Integer.toString(1) + 0 + Integer.toString(0) + flags_new[5] + Integer.toString(0) + 0;
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "B5H":
                Log.d("Ritik", "execute: ora l");
                temp = Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "a").substring(0, 2))) | Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "l").substring(0, 2))));
                temp = typeHelper.Dec2Hex(temp);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp.length()]).replace('\0', '0') + temp + "H");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "00H");
                flags = flags_new[0] + Integer.toString(flags_new[1]) + Integer.toString(1) + 0 + Integer.toString(0) + flags_new[5] + Integer.toString(0) + 0;
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "B6H":
                Log.d("Ritik", "execute: ora m");
                temp = Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "a").substring(0, 2))) | Integer.parseInt(sqLiteHelper.read_memory(data_address_1).substring(0, 2)));
                temp = typeHelper.Dec2Hex(temp);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp.length()]).replace('\0', '0') + temp + "H");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "00H");
                flags = flags_new[0] + Integer.toString(flags_new[1]) + Integer.toString(1) + 0 + Integer.toString(0) + flags_new[5] + Integer.toString(0) + 0;
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "F6H":
                Log.d("Ritik", "execute: ori data");
                temp = Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "a").substring(0, 2))) | Integer.parseInt(typeHelper.Hex2Dec(sqLiteHelper.read_memory(data_address_1).substring(0, 2))));
                temp = typeHelper.Dec2Hex(temp);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp.length()]).replace('\0', '0') + temp + "H");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "00H");
                flags = flags_new[0] + Integer.toString(flags_new[1]) + Integer.toString(1) + 0 + Integer.toString(0) + flags_new[5] + Integer.toString(0) + 0;
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "AFH":
                Log.d("Ritik", "execute: xra a");
                temp = Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "a").substring(0, 2))) ^ Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "a").substring(0, 2))));
                temp = typeHelper.Dec2Hex(temp);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp.length()]).replace('\0', '0') + temp + "H");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "00H");
                flags = flags_new[0] + Integer.toString(flags_new[1]) + Integer.toString(1) + 0 + Integer.toString(0) + flags_new[5] + Integer.toString(0) + 0;
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "A8H":
                Log.d("Ritik", "execute: xra b");
                temp = Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "a").substring(0, 2))) ^ Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "b").substring(0, 2))));
                temp = typeHelper.Dec2Hex(temp);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp.length()]).replace('\0', '0') + temp + "H");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "00H");
                flags = flags_new[0] + Integer.toString(flags_new[1]) + Integer.toString(1) + 0 + Integer.toString(0) + flags_new[5] + Integer.toString(0) + 0;
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "A9H":
                Log.d("Ritik", "execute: xra c");
                temp = Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "a").substring(0, 2))) ^ Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "c").substring(0, 2))));
                temp = typeHelper.Dec2Hex(temp);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp.length()]).replace('\0', '0') + temp + "H");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "00H");
                flags = flags_new[0] + Integer.toString(flags_new[1]) + Integer.toString(1) + 0 + Integer.toString(0) + flags_new[5] + Integer.toString(0) + 0;
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "AAH":
                Log.d("Ritik", "execute: xra d");
                temp = Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "a").substring(0, 2))) ^ Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "d").substring(0, 2))));
                temp = typeHelper.Dec2Hex(temp);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp.length()]).replace('\0', '0') + temp + "H");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "00H");
                flags = flags_new[0] + Integer.toString(flags_new[1]) + Integer.toString(1) + 0 + Integer.toString(0) + flags_new[5] + Integer.toString(0) + 0;
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "ABH":
                Log.d("Ritik", "execute: xra e");
                temp = Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "a").substring(0, 2))) ^ Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "e").substring(0, 2))));
                temp = typeHelper.Dec2Hex(temp);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp.length()]).replace('\0', '0') + temp + "H");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "00H");
                flags = flags_new[0] + Integer.toString(flags_new[1]) + Integer.toString(1) + 0 + Integer.toString(0) + flags_new[5] + Integer.toString(0) + 0;
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "ACH":
                Log.d("Ritik", "execute: xra h");
                temp = Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "a").substring(0, 2))) ^ Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "h").substring(0, 2))));
                temp = typeHelper.Dec2Hex(temp);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp.length()]).replace('\0', '0') + temp + "H");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "00H");
                flags = flags_new[0] + Integer.toString(flags_new[1]) + Integer.toString(1) + 0 + Integer.toString(0) + flags_new[5] + Integer.toString(0) + 0;
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "ADH":
                Log.d("Ritik", "execute: xra l");
                temp = Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "a").substring(0, 2))) ^ Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "l").substring(0, 2))));
                temp = typeHelper.Dec2Hex(temp);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp.length()]).replace('\0', '0') + temp + "H");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "00H");
                flags = flags_new[0] + Integer.toString(flags_new[1]) + Integer.toString(1) + 0 + Integer.toString(0) + flags_new[5] + Integer.toString(0) + 0;
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "AEH":
                Log.d("Ritik", "execute: xra m");
                temp = Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "a").substring(0, 2))) ^ Integer.parseInt(sqLiteHelper.read_memory(data_address_1).substring(0, 2)));
                temp = typeHelper.Dec2Hex(temp);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp.length()]).replace('\0', '0') + temp + "H");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "00H");
                flags = flags_new[0] + Integer.toString(flags_new[1]) + Integer.toString(1) + 0 + Integer.toString(0) + flags_new[5] + Integer.toString(0) + 0;
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "EEH":
                Log.d("Ritik", "execute: xri data");
                temp = Integer.toString(Integer.parseInt(typeHelper.Hex2Dec(prefshelper.getregister(context, "a").substring(0, 2))) ^ Integer.parseInt(typeHelper.Hex2Dec(sqLiteHelper.read_memory(data_address_1).substring(0, 2))));
                temp = typeHelper.Dec2Hex(temp);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp.length()]).replace('\0', '0') + temp + "H");
                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), "00H");
                flags = flags_new[0] + Integer.toString(flags_new[1]) + Integer.toString(1) + 0 + Integer.toString(0) + flags_new[5] + Integer.toString(0) + 0;
                prefshelper.saveregister(context, "f", new String(new char[2 - typeHelper.Bin2Hex(flags).length()]).replace('\0', '0') + typeHelper.Bin2Hex(flags) + "H");
                break;
            case "2FH":
                Log.d("Ritik", "execute: cma");
                temp = typeHelper.Hex2Bin(prefshelper.getregister(context, "a").substring(0, 2));
                temp = temp.replaceAll("1", "A");
                temp = temp.replaceAll("0", "1");
                temp = temp.replaceAll("A", "0");
                temp = typeHelper.Bin2Hex(temp);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp.length()]).replace('\0', '0') + temp + "H");
                break;
            case "3FH":
                Log.d("Ritik", "execute: cmc");
                temp1 = typeHelper.Hex2Bin(prefshelper.getregister(context, "f").substring(0, 2));
                if (temp1.substring(7).compareTo("1") == 0)
                    temp = "0";
                else
                    temp = "1";
                temp = typeHelper.Bin2Hex(temp1.substring(0, 7) + temp);
                prefshelper.saveregister(context, "f", new String(new char[2 - temp.length()]).replace('\0', '0') + temp + "H");
                break;
            case "37H":
                Log.d("Ritik", "execute: stc");
                temp1 = typeHelper.Hex2Bin(prefshelper.getregister(context, "f").substring(0, 2));
                temp = typeHelper.Bin2Hex(temp1.substring(0, 7) + "1");
                prefshelper.saveregister(context, "f", new String(new char[2 - temp.length()]).replace('\0', '0') + temp + "H");
                break;
            case "17H":
                Log.d("Ritik", "execute: ral");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                temp = flags.substring(7);
                temp1 = prefshelper.getregister(context, "a");
                temp1 = typeHelper.Hex2Bin(temp1.substring(0, 2));
                temp2 = temp1.substring(0, 1);
                temp1 = temp1.substring(1) + temp;
                flags = flags.substring(0, 7) + temp2;
                temp1 = typeHelper.Bin2Hex(temp1);
                flags = typeHelper.Bin2Hex(flags);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp1.length()]).replace('\0', '0') + temp1 + "H");
                prefshelper.saveregister(context, "f", new String(new char[2 - flags.length()]).replace('\0', '0') + flags + "H");
                break;
            case "1FH":
                Log.d("Ritik", "execute: ral");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                temp = flags.substring(7);
                temp1 = prefshelper.getregister(context, "a");
                temp1 = typeHelper.Hex2Bin(temp1.substring(0, 2));
                temp2 = temp1.substring(7);
                temp1 = temp + temp1.substring(1);
                flags = flags.substring(0, 7) + temp2;
                temp1 = typeHelper.Bin2Hex(temp1);
                flags = typeHelper.Bin2Hex(flags);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp1.length()]).replace('\0', '0') + temp1 + "H");
                prefshelper.saveregister(context, "f", new String(new char[2 - flags.length()]).replace('\0', '0') + flags + "H");
                break;
            case "07H":
                Log.d("Ritik", "execute: rlc");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                temp1 = prefshelper.getregister(context, "a");
                temp1 = typeHelper.Hex2Bin(temp1.substring(0, 2));
                temp2 = temp1.substring(0, 1);
                temp1 = temp1.substring(1) + temp2;
                flags = flags.substring(0, 7) + temp2;
                temp1 = typeHelper.Bin2Hex(temp1);
                flags = typeHelper.Bin2Hex(flags);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp1.length()]).replace('\0', '0') + temp1 + "H");
                prefshelper.saveregister(context, "f", new String(new char[2 - flags.length()]).replace('\0', '0') + flags + "H");
                break;
            case "0FH":
                Log.d("Ritik", "execute: rrc");
                flags = prefshelper.getregister(context, "f");
                flags = typeHelper.Hex2Bin(flags.substring(0, 2));
                temp1 = prefshelper.getregister(context, "a");
                temp1 = typeHelper.Hex2Bin(temp1.substring(0, 2));
                temp2 = temp1.substring(7);
                temp1 = temp2 + temp1.substring(0, 7);
                flags = flags.substring(0, 7) + temp2;
                temp1 = typeHelper.Bin2Hex(temp1);
                flags = typeHelper.Bin2Hex(flags);
                prefshelper.saveregister(context, "a", new String(new char[2 - temp1.length()]).replace('\0', '0') + temp1 + "H");
                prefshelper.saveregister(context, "f", new String(new char[2 - flags.length()]).replace('\0', '0') + flags + "H");
                break;

            //logical end here
            //rst begin here
            case "C7H":
                Log.d("Ritik", "execute: rst 0");
                if (prefshelper.getregister(context, "intr").equals("0001H"))
                    prefshelper.saveregister(context, "pc", "0000H");
                break;
            case "CFH":
                Log.d("Ritik", "execute: rst 1");
                if (prefshelper.getregister(context, "intr").equals("0001H"))
                    prefshelper.saveregister(context, "pc", "0008H");
                break;
            case "D7H":
                Log.d("Ritik", "execute: rst 2");
                if (prefshelper.getregister(context, "intr").equals("0001H"))
                    prefshelper.saveregister(context, "pc", "0010H");
                break;
            case "DFH":
                Log.d("Ritik", "execute: rst 3 " + prefshelper.getregister(context, "intr"));

                if (prefshelper.getregister(context, "intr").equals("0001H"))
                    prefshelper.saveregister(context, "pc", "0018H");
                break;
            case "E7H":
                Log.d("Ritik", "execute: rst 4");
                if (prefshelper.getregister(context, "intr").equals("0001H"))
                    prefshelper.saveregister(context, "pc", "0020H");
                break;
            case "EFH":
                Log.d("Ritik", "execute: rst 5");
                if (prefshelper.getregister(context, "intr").equals("0001H"))
                    prefshelper.saveregister(context, "pc", "0028H");
                break;
            case "F7H":
                Log.d("Ritik", "execute: rst 6");
                if (prefshelper.getregister(context, "intr").equals("0001H"))
                    prefshelper.saveregister(context, "pc", "0030H");
                break;
            case "FFH":
                Log.d("Ritik", "execute: rst 7");
                if (prefshelper.getregister(context, "intr").equals("0001H"))
                    prefshelper.saveregister(context, "pc", "0038H");
                break;

            case "F3H":
                Log.d("Ritik", "execute: di");
                prefshelper.saveregister(context, "intr", "0000H");
                break;
            case "FBH":
                Log.d("Ritik", "execute: ei");
                prefshelper.saveregister(context, "intr", "0001H");
                break;
            case "76H":
                Log.d("Ritik", "execute: hlt");
                prefshelper.saveregister(context, "hlt", "0001H");
                break;
            case "27H":
                Log.d("Ritik", "execute: daa");
                temp = prefshelper.getregister(context, "a");
                flags = prefshelper.getregister(context, "f");
                temp1 = "0";
                temp2 = "0";

                if ((Integer.parseInt(temp.substring(1, 2), 16) > 9) || ((typeHelper.Hex2Bin(flags.substring(0, 2))).substring(3, 4).equals("1")))
                    temp1 = "6";
                if ((Integer.parseInt(temp.substring(0, 1), 16) > 9) || ((typeHelper.Hex2Bin(flags.substring(0, 2))).substring(7).equals("1")))
                    temp2 = "6";

                flags_new = typeHelper.getFlagadd(prefshelper.getregister(context, "a"), temp2 + temp1 + "H");
                flags = Integer.toString(flags_new[0]) + Integer.toString(flags_new[1]) + Integer.toString(1) + Integer.toString(flags_new[3]) + Integer.toString(0) + Integer.toString(flags_new[5]) + Integer.toString(0) + Integer.toString(flags_new[7]);
                prefshelper.saveregister(context, "a", typeHelper.AddHex(prefshelper.getregister(context, "a"), temp2 + temp1 + "H", 2));
                prefshelper.saveregister(context, "f", flags);
                break;
            case "20H":
                Log.d("Ritik", "execute: rim");
                String rim = sqLiteHelper.read_memory("SID").substring(sqLiteHelper.read_memory("SID").length() - 1);
                Log.d("Ritik", "execute: rim1 " + rim);
                sqLiteHelper.save_memory("SID", "0" + sqLiteHelper.read_memory("SID").substring(0, sqLiteHelper.read_memory("SID").length() - 1));
                Log.d("Ritik", "execute: rim2");
                rim = rim + "000" + prefshelper.getregister(context, "intr").substring(3, 4) + prefshelper.getregister(context, "imask").substring(0, 3);
                Log.d("Ritik", "execute: rim3 " + rim);
                rim = typeHelper.Bin2Hex(rim);
                Log.d("Ritik", "execute: rim4 " + rim);
                prefshelper.saveregister(context, "a", new String(new char[2 - rim.length()]).replace('\0', '0') + rim + "H");
                Log.d("Ritik", "execute: rim5");
                break;

            case "30H":
                Log.d("Ritik", "execute: sim");
                String sim = typeHelper.Hex2Bin(prefshelper.getregister(context, "a").substring(0, 2));
                if (sim.substring(1, 2).equals("1"))
                    sqLiteHelper.save_memory("SOD", sqLiteHelper.read_memory("SOD").substring(1) + sim.substring(0, 1));
                if (sim.substring(4, 5).equals("1"))
                    prefshelper.saveregister(context, "imask", sim.substring(5) + "0H");
                break;


        }

        return Integer.parseInt(time);


    }


    public String getinstruction(String address) {
        String opcode = sqLiteHelper.read_memory(address);
        Cursor result = instructionDbHelper.getsizeopcode(opcode);
        String instruction = result.getString(0);
        int size = result.getInt(1);

        if (size == 1) {
            //no need to do anything
        } else if (size == 2) {
            int data_address = Integer.parseInt(typeHelper.Hex2Dec(address.substring(0, address.length() - 1))) + 1;
            instruction = instruction.substring(0, instruction.lastIndexOf(" ") + 1);
            String data = sqLiteHelper.read_memory(new String(new char[4 - typeHelper.Dec2Hex(Integer.toString(data_address)).length()]).replace('\0', '0') + typeHelper.Dec2Hex(Integer.toString(data_address)) + "H");
            instruction = instruction + data;
        } else if (size == 3) {
            int data_address = Integer.parseInt(typeHelper.Hex2Dec(address.substring(0, address.length() - 1))) + 1;
            instruction = instruction.substring(0, instruction.lastIndexOf(" ") + 1);
            String data = sqLiteHelper.read_memory(new String(new char[4 - typeHelper.Dec2Hex(Integer.toString(data_address)).length()]).replace('\0', '0') + typeHelper.Dec2Hex(Integer.toString(data_address)) + "H");
            data_address = data_address + 1;
            String data2 = sqLiteHelper.read_memory(new String(new char[4 - typeHelper.Dec2Hex(Integer.toString(data_address)).length()]).replace('\0', '0') + typeHelper.Dec2Hex(Integer.toString(data_address)) + "H");
            data = data2.substring(0, data2.length() - 1) + data;
            instruction = instruction + data;
        }

        return instruction;
    }


    public void push(String content) {
        prefshelper.saveregister(context, "sp", typeHelper.SubHex(prefshelper.getregister(context, "sp"), "0001H", 4));
        sqLiteHelper.save_memory(prefshelper.getregister(context, "sp"), content);

    }

    public String pop() {
        String content = sqLiteHelper.read_memory(prefshelper.getregister(context, "sp"));
        prefshelper.saveregister(context, "sp", typeHelper.AddHex(prefshelper.getregister(context, "sp"), "0001H", 4));
        return content;
    }


}
