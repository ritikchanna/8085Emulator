package ritik.e8085;

/**
 * Created by SuperUser on 10-03-2017.
 */


        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.SQLException;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteException;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

        import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


public class SQLiteHelper extends SQLiteAssetHelper {

    static String DATABASE_NAME="re8085";


    public static final String TABLE_NAME="memory";


    public static final String KEY_ADDRESS="address";

    public static final String KEY_CONTENT="content";
    private SQLiteDatabase db;

Context context;
    public SQLiteHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);
        this.context=context;



    }

//    @Override
//    public void onCreate(SQLiteDatabase database) {
//
//        String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+KEY_ADDRESS+" VARCHAR PRIMARY KEY, "+KEY_CONTENT+" VARCHAR )";
//        database.execSQL(CREATE_TABLE);
//
//
//    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }
    public void initiate_memory(SQLiteDatabase database) {
        TypeHelper typeHelper=new TypeHelper();
        for (int i=0;i<32768;i++){
            String Hexa=typeHelper.Dec2Hex(Integer.toString(i));
            String intiate_memory = "INSERT INTO memory (address,content) VALUES('"+new String(new char[4-Hexa.length()]).replace('\0', '0')+Hexa+"H" +
                    ""+"', 'EFH');";
            database.execSQL(intiate_memory);



        }}




    public void initiate_devices(SQLiteDatabase database) {


            String intiate_device = "INSERT INTO memory (address,content) VALUES('SID', '00000000');";
            database.execSQL(intiate_device);
            intiate_device = "INSERT INTO memory (address,content) VALUES('SOD', '00000000');";
            database.execSQL(intiate_device);
        }



    public void save_memory(String address,String content){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("Ritik", "save_memory: "+address+" : "+content);
        ContentValues args= new ContentValues();
        args.put(KEY_CONTENT,content);
        args.put(KEY_ADDRESS,address);
        Log.d("Ritik", "save_memory: 1");

        int id=(int)db.insertWithOnConflict(TABLE_NAME,null,args,SQLiteDatabase.CONFLICT_IGNORE);
        if (id == -1)
        db.update(TABLE_NAME,args,KEY_ADDRESS+"=\""+address+"\"",null);
        Log.d("Ritik", "save_memory: done");
    }
    public String read_memory(String address){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor = db.query(true, TABLE_NAME, new String[] {KEY_CONTENT},
                KEY_ADDRESS + " = \"" + address+"\"", null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        try {
            return mCursor.getString(0);
        }catch(Exception e){
            return "00H";
        }
    }
    public void create_memory(String address,String content){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args= new ContentValues();
        args.put(KEY_CONTENT,content);
        args.put(KEY_ADDRESS,address);
        Log.d("Ritik", "save_memory: 1");
        db.insert(TABLE_NAME,null,args);
        Log.d("Ritik", "save_memory: done");
    }
    public void delete_memory(String address){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("Ritik", "delete_memory:  "+address);
        db.delete(TABLE_NAME,KEY_ADDRESS+" = '"+address+"'",null);
        Log.d("Ritik", "delete_memory: 2");

    }

}