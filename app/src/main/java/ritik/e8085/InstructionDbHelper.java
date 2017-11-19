package ritik.e8085;

/**
 * Created by SuperUser on 12-03-2017.
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class InstructionDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "8085";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "instructions";
    private static final String COLUMN_ID = "_id";
    public static final String COLUMN_INSTRUCTION = "instruction";
    public static final String COLUMN_OPCODE = "opcode";
    private static final String COLUMN_BYTE = "byte";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_DATA="data";

    private SQLiteDatabase database;

    private final Context context;

    // database path
    private static String DATABASE_PATH;

    /** constructor */
    public InstructionDbHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
        DATABASE_PATH = context.getFilesDir().getParentFile().getPath()
                + "/databases/";

    }

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     * */
    public void create() throws IOException {
        boolean check = checkDataBase();

        SQLiteDatabase db_Read = null;

        // Creates empty database default system path
        db_Read = this.getWritableDatabase();
        db_Read.close();
        try {
            if (!check) {
                copyDataBase();
            }
        } catch (IOException e) {
            throw new Error("Error copying database");
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DATABASE_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            // database does't exist yet.
        }

        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = DATABASE_PATH + DATABASE_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    /** open the database */
    public void open() throws SQLException {
        String myPath = DATABASE_PATH + DATABASE_NAME;
        database = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);
    }

    /** close the database */
    @Override
    public synchronized void close() {
        if (database != null)
            database.close();
        super.close();
    }



    // retrieves a particular instruction
    public Cursor getInstruction(String instruction) throws SQLException {

        Cursor mCursor = database.query(true, TABLE_NAME, new String[] {
                        COLUMN_OPCODE, COLUMN_BYTE, COLUMN_TIME },
                COLUMN_INSTRUCTION + " LIKE \"" + instruction+"%\"", null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;
    }
    public Cursor getallInstructions() throws SQLException{
        Cursor mCursor = database.query(true, TABLE_NAME, new String[] {COLUMN_ID,COLUMN_INSTRUCTION,COLUMN_OPCODE},null, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }
    public Cursor getInstructionFull(String instruction) throws SQLException {

        Cursor mCursor = database.query(true, TABLE_NAME, new String[] {
                        COLUMN_OPCODE, COLUMN_BYTE, COLUMN_TIME },
                COLUMN_INSTRUCTION + " = \"" + instruction+"\"", null, null, null, null, null);




        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;
    }

    public int getsize(String instruction){
        String[] words=instruction.split("\\s");
        instruction=words[0];
        Log.d("Ritik", "getsize: "+instruction);
        Cursor mCursor = database.query(true, TABLE_NAME, new String[] {COLUMN_BYTE},
                COLUMN_INSTRUCTION + " LIKE \"" + instruction+"%\"", null, null, null, null, null);
        Log.d("Ritik", "getsize: Cursor created");
        if (mCursor != null) {
            Log.d("Ritik", "getsize: not null");
            mCursor.moveToFirst();
        }

        Log.d("Ritik", "getsize: return not 0");
        try {
            return mCursor.getInt(0);
        }
        catch (Exception e){
            Log.d("Ritik", "getsize: exception");
            return 0;
        }

    }
    public Cursor getsizeopcode(String opcode){
        opcode=opcode.substring(0,opcode.length()-1);
        Cursor mCursor = database.query(true, TABLE_NAME, new String[] {COLUMN_INSTRUCTION,COLUMN_BYTE,COLUMN_TIME},
                COLUMN_OPCODE + " = \"" + opcode+"\"", null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }
    public boolean isInstruction(String instruction){
        int size=getsize(instruction);
        String data;
        TypeHelper typeHelper=new TypeHelper();
        if(size==1){
            Log.d("Ritik", "isInstruction: 1 ");
            try {
                Log.d("Ritik", "isInstruction: 1 "+getInstructionFull(instruction).getInt(1));
                if (getInstructionFull(instruction).getInt(1) == 1)
                    return true;
                else
                    return false;
            }
            catch (Exception e){
                return false;
            }
        }
        else if(size==2) {
           if(instruction.lastIndexOf(" ")+1==0)
               return false;
            data = instruction.substring((instruction.lastIndexOf(" ")+1));
            instruction = instruction.substring(0, instruction.lastIndexOf(" "));
            Log.d("Ritik", "isInstruction: 2 byte "+instruction+"     "+data);
            try {
                if (typeHelper.isHex(data.toUpperCase(), 2) && getInstructionFull(instruction+" Data").getInt(1) == 2){

                    Log.d("Ritik", "isInstruction: 2 byte 1");
                    return true;}
                else
                    return false;
            } catch (Exception e) {
                Log.d("Ritik", "isInstruction: 2 byte 2");
                return false;
            }
        }
        else if(size==3) {
            if(instruction.lastIndexOf(" ")+1==0)
                return false;
            data = instruction.substring(instruction.lastIndexOf(" ") + 1);
            instruction = instruction.substring(0, instruction.lastIndexOf(" "));
            try {
                if (typeHelper.isHex(data.toUpperCase(), 4) && getInstructionFull(instruction+" Data").getInt(1) == 3)
                    return true;
                else
                    return false;
            } catch (Exception e) {
                return false;
            }
        }
        else
            return false;
    }




    // retrieves all users
//    public Cursor getAllUsers() {
//        return database.query(TABLE_NAME, new String[] { COLUMN_ID,
//                        COLUMN_NAME, COLUMN_INITIALS, COLUMN_ADDRESS }, null, null,
//                null, null, null);
//    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

}