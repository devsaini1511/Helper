package in.thedebug.helper_utilis;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;


/**
 * Created By Dev Saini
 **/


public class SharedPrefManager {

    private static SharedPrefManager instance;
    private transient SharedPreferences prefs;

    private SharedPrefManager(Context context) {
        prefs = context.getSharedPreferences(context.getPackageName(),
                Context.MODE_PRIVATE);
    }

    public static SharedPrefManager getInstance(Context context) {
        if (instance == null)
            instance = new SharedPrefManager(context);
        return instance;
    }


    public void saveString(String key, String value) {
        prefs.edit().putString(key, value).apply();
    }


    public String getString(String key, String defValue) {
        return prefs.getString(key, defValue);
    }

    public void saveBoolean(String key, boolean value) {
        prefs.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return prefs.getBoolean(key, defValue);
    }

    public void saveInt(String key, int value) {
        prefs.edit().putInt(key, value).apply();
    }

    public int getInt(String key, int defValue) {
        return prefs.getInt(key, defValue);
    }

    public void saveLong(String key, long value) {
        prefs.edit().putLong(key, value).apply();
    }

    public long getLong(String key, long defValue) {
        return prefs.getLong(key, defValue);
    }

    public void saveSet(String key, Set<String> value) {
        prefs.edit().putStringSet(key, value).apply();
    }

    public Set<String> getSet(String key, Set<String> defValue) {

        return prefs.getStringSet(key, defValue);
    }

    public void clearAll() {
        prefs.edit().clear().apply();
    }

    public void clear(String key) {
        prefs.edit().remove(key).apply();
    }


}