package ritik.e8085;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by SuperUser on 09-03-2017.
 */

public class PrefsHelper {
        public String[] RegisterArray={"a","f","b","c","d","e","h","l","pc","sp","hlt","intr","imask"};
        public String[] default_values={"CCH","44H","00H","00H","00H","00H","00H","00H","0000H","27B4H","0000H","0001H","0000H"};
    public void initialize_registers(Context context){
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences("8085_Registers", Context.MODE_MULTI_PROCESS);
        if(!sharedPreferences.contains("a"))
        {
            saveregister(context,default_values);
        }

    }
    public String getregister(Context context,String Register) {
        SharedPreferences sharedPreferences;
        String register;
        sharedPreferences = context.getSharedPreferences("8085_Registers", Context.MODE_MULTI_PROCESS);
        register = sharedPreferences.getString(Register, null);
        return register;
    }
    public String[] getregister(Context context) {
        SharedPreferences sharedPreferences;
        String register[] = new String[RegisterArray.length];
        sharedPreferences = context.getSharedPreferences("8085_Registers", Context.MODE_MULTI_PROCESS);
        for(int i=0;i<RegisterArray.length;i++)
        register[i] = sharedPreferences.getString(RegisterArray[i], null);
        return register;
    }
    public void saveregister(Context context, String Register, String Value) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        sharedPreferences = context.getSharedPreferences("8085_Registers", Context.MODE_MULTI_PROCESS);
        editor = sharedPreferences.edit();
        editor.putString(Register, Value);
        editor.commit();
    }
    public void saveregister(Context context,String Value[]) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        sharedPreferences = context.getSharedPreferences("8085_Registers", Context.MODE_MULTI_PROCESS);
        editor = sharedPreferences.edit();
        Log.d("Ritik", "saveregister: "+Value.length);
        for(int i=0;i<8;i++){
            if(Value[i].length()<3)
               Value[i]=new String(new char[3-Value[i].length()]).replace('\0', '0')+Value[i];
        editor.putString(RegisterArray[i], Value[i].substring(0,2).toUpperCase()+"H");}
        for(int i=8;i<Value.length;i++){
            if(Value[i].length()<5)
                Value[i]=new String(new char[5-Value[i].length()]).replace('\0', '0')+Value[i];
            editor.putString(RegisterArray[i], Value[i].substring(0,4).toUpperCase()+"H");}
        editor.commit();
    }
    public boolean getflagbit(Context context,int position){
        String flag=getregister(context,"f");
        flag=new TypeHelper().Hex2Bin(flag.substring(0,flag.length()-1));
       if(flag.charAt(flag.length()-position-1)=='1')
           return true;
        else
           return false;

    }
    public boolean isFirstrun(Context context){
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        sharedPreferences = context.getSharedPreferences("8085_Registers", Context.MODE_MULTI_PROCESS);
        return sharedPreferences.getBoolean("firstrun", true);
    }
    public void firstrun(Context context){
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        sharedPreferences = context.getSharedPreferences("8085_Registers", Context.MODE_MULTI_PROCESS);
        sharedPreferences.edit().putBoolean("firstrun", false).commit();
    }
    public void makefirstrun(Context context){
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        sharedPreferences = context.getSharedPreferences("8085_Registers", Context.MODE_MULTI_PROCESS);
        sharedPreferences.edit().putBoolean("firstrun", true).commit();
    }

}
