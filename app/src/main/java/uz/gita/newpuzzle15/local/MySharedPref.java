package uz.gita.newpuzzle15.local;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uz.gita.newpuzzle15.MainActivity;

public class MySharedPref {
    private Context context;
    private static MySharedPref myPref;
    private SharedPreferences sharedPreferences;

    private MySharedPref(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("Sample", Context.MODE_PRIVATE);
    }

    public static MySharedPref getPref(Context context) {
        if (myPref == null) {
            myPref = new MySharedPref(context);
        }
        return myPref;
    }

    public void saveResult(int count){
        List<Integer> list = new ArrayList<>();
        list.add(count);
        list.add(getFirst());
        list.add(getSecond());
        list.add(getThird());
        Collections.sort(list);

        saveFirst(list.get(0));
        saveSecond(list.get(1));
        saveThird(list.get(2));
    }

    public void remove(){
        sharedPreferences.edit().remove("FIRST").remove("SECOND").remove("THIRD").apply();
    }


    public boolean getSound(){
        return sharedPreferences.getBoolean("SOUND", true);
    }
    public int getFirst(){
        return sharedPreferences.getInt("FIRST", Integer.MAX_VALUE);
    }
    public int getSecond(){
        return sharedPreferences.getInt("SECOND", Integer.MAX_VALUE);
    }
    public int getThird(){
        return sharedPreferences.getInt("THIRD", Integer.MAX_VALUE);
    }

    public void saveSound(boolean bool){
        sharedPreferences.edit().putBoolean("SOUND", bool).apply();
    }

    private void saveFirst(int count){
        sharedPreferences.edit().putInt("FIRST", count).apply();
    }
    private void saveSecond(int count){
        sharedPreferences.edit().putInt("SECOND", count).apply();
    }
    private void saveThird(int count){
        sharedPreferences.edit().putInt("THIRD", count).apply();
    }


    /**
     * bnsdf
     * aljfnajskd
     * aojnbdfkjsaf
     * lkdnfkjalsd
     * dfgsd
     *
     * */
}