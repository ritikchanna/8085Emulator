package ritik.e8085;

import android.util.Log;

/**
 * Created by SuperUser on 09-03-2017.
 */

public class TypeHelper {
    public String Hex2Bin(String Hex){
        String Converted=Dec2Bin(Hex2Dec(Hex));
        return new String(new char[Hex.length()*4 - Converted.length()]).replace('\0', '0')+Converted;
    }
    public String Hex2Dec(String Hex){
        int Dec1=Integer.parseInt(Hex, 16);
        return Integer.toString(Dec1);

    }
    public String Dec2Hex(String Dec){
        Log.d("Ritik", "Dec2Hex");
        return (Integer.toString(Integer.parseInt(Dec),16).toUpperCase());

    }
    public String Dec2Bin(String Dec){

        return (Integer.toString(Integer.parseInt(Dec), 2));

    }
    public String Bin2Hex(String Bin){
        return Dec2Hex(Bin2Dec(Bin));
    }
    public String Bin2Dec(String Bin){
        int Dec1 = Integer.parseInt(Bin, 2);
        return Integer.toString(Dec1);
    }


    public String Cycle(String input,int size){
        String output="99";
        if(input!=null){

            if(input.endsWith("H")||input.endsWith("h"))

                output=Hex2Bin(input.substring(0,input.length()-1))+"B";

            else if(input.endsWith("B")||input.endsWith("b"))
                output=Bin2Dec(input.substring(0,input.length()-1))+"D";
            else if(input.endsWith("D")||input.endsWith("d")) {
                String converted=Dec2Hex(input.substring(0, input.length() - 1));
                output = new String(new char[size - converted.length()]).replace('\0', '0')+converted+"H";

            }}
        else
            output="Error";
        Log.d("Ritik", "Cycle: "+output);
        return output;
    }

    public String[] HexanArray(String[] input){
        String[] output=new String[input.length];
        for (int i=0;i<input.length;i++){
            if(input[i].endsWith("H")||input[i].endsWith("h"))
                output[i]=input[i];
            else if(input[i].endsWith("B")||input[i].endsWith("b"))
                output[i]=Bin2Hex(input[i].substring(0,input[i].length()-1))+"H";
            else if(input[i].endsWith("D")||input[i].endsWith("d"))
                output[i]=Dec2Hex(input[i].substring(0,input[i].length()-1))+"H";
        }
        return output;
    }
    public String AddHex(String hex1,String hex2,int size){
        String hex3=Dec2Hex(Integer.toString(Integer.parseInt(Hex2Dec(hex1.substring(0,hex1.length()-1)))+Integer.parseInt(Hex2Dec(hex2.substring(0,hex2.length()-1)))));
        hex3=("00000"+hex3).substring(("00000"+hex3).length()-size,("00000"+hex3).length());
        return new String(new char[size - hex3.length()]).replace('\0', '0')+hex3+"H";
    }
    public String SubHex(String hex1,String hex2,int size){
        int a=Integer.parseInt(hex1.substring(0,hex1.length()-1),16);
        int b=Integer.parseInt(hex2.substring(0,hex2.length()-1),16);
        Log.d("Ritik", "SubHex: numer is "+a);
        Log.d("Ritik", "SubHex: numer is "+b);

        Log.d("Ritik", "SubHex: diff in int is "+(a-b));
        String hex3="0000000"+Integer.toHexString(a-b);
        return hex3.substring(hex3.length()-size)+"H";
    }
    public int[] getFlagadd(String hex1,String hex2){
        int[] flags={0,0,0,0,0,0,0,0};
        String hex1_t=hex1.substring(0,hex1.length()-1);
        String hex2_t=hex2.substring(0,hex2.length()-1);
        int full_sum=Integer.parseInt(hex1_t, 16)+Integer.parseInt(hex2_t, 16);
        hex1_t=hex1_t.substring(1);
        hex2_t=hex2_t.substring(1);
        int half_sum=Integer.parseInt(hex1_t, 16)+Integer.parseInt(hex2_t, 16);

        Log.d("Ritik", "getFlagadd: "+full_sum+" "+half_sum);
        if(full_sum==0)
            flags[1]=1;
        if(full_sum>255)
            flags[7]=1;
        if(half_sum>15)
            flags[3]=1;
        flags[0]=Integer.parseInt(Hex2Bin(AddHex(hex1,hex2,hex1.length()-1).substring(0,AddHex(hex1,hex2,hex1.length()-1).length()-1)).substring(0,1));

        if((Integer.toBinaryString(full_sum).split("1", -1).length - 1)%2==0)
            flags[5]=1;
        return flags;
    }
    public int[] getFlagsub(String hex1,String hex2){
        int[] flags={0,0,0,0,0,0,0,0};
        String hex1_t=hex1.substring(0,hex1.length()-1);
        String hex2_t=hex2.substring(0,hex2.length()-1);
        int full_diff=Integer.parseInt(hex1_t, 16)-Integer.parseInt(hex2_t, 16);
        hex1_t=hex1_t.substring(1);
        hex2_t=hex2_t.substring(1);
        int half_diff=Integer.parseInt(hex1_t, 16)-Integer.parseInt(hex2_t, 16);

        Log.d("Ritik", "getFlagadd: diffs "+full_diff+" "+half_diff);
        if(full_diff==0)
            flags[1]=1;
        if(full_diff<0)
            flags[7]=1;
        if(half_diff<0)
            flags[3]=1;
        flags[0]=Integer.parseInt(Hex2Bin(SubHex(hex1,hex2,hex1.length()-1).substring(0,SubHex(hex1,hex2,hex1.length()-1).length()-1)).substring(0,1));

        if((Integer.toBinaryString(full_diff).split("1", -1).length - 1)%2==0)
            flags[5]=1;
        return flags;
    }
    public boolean isHex(String Hex,int size){
        if(size==0)
            return true;
        if(Hex.length()==0)
            return false;

        if ((Hex.substring(0,Hex.length()-1).matches("[0123456789ABCDEF]*"))&&(Hex.length()-1==size)&&(Hex.substring(size).equals("H"))) {
            return true;
        } else {
            return false;
        }
    }
    public boolean isBin(String Bin,int size){
        if(size==0)
            return true;
        if(Bin.length()==0)
            return false;
        if ((Bin.substring(0,Bin.length()-1).matches("[01]*"))&&(Bin.length()-1==size)&&(Bin.substring(size).equals("B"))) {
            return true;
        } else {
            return false;
        }
    }public boolean isDec(String Dec,int size){
        if(size==0)
            return true;
        if(Dec.length()==0)
            return false;
        if ((Dec.substring(0,Dec.length()-1).matches("[0123456789]*"))&&(Integer.parseInt(Dec.substring(0,Dec.length()-1))<=size)&&(Dec.substring(Dec.length()-1).equals("D"))) {
            return true;
        } else {
            return false;
        }
    }
}
