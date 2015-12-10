package kr.co.dementor.dementorbank.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import java.util.Set;

/**
 * Created by dementor1 on 15. 11. 24..
 */
public class DementorUtil
{
    public static Object loadPreferance(@NonNull Context context, @NonNull String key, @NonNull Object defaultValue)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        if(defaultValue instanceof String)
        {
            return preferences.getString(key, (String)defaultValue);
        }
        else if(defaultValue instanceof Boolean)
        {
            return preferences.getBoolean(key, (boolean) defaultValue);
        }
        else if(defaultValue instanceof Integer)
        {
            return preferences.getInt(key, (int) defaultValue);
        }
        else if(defaultValue instanceof Set)
        {
            return preferences.getStringSet(key, (Set<String>) defaultValue);
        }
        else
        {
            LogTrace.e("Undefine value");
            return null;
        }
    }

    public static void savePreferance(Context context, String key, Object value)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor edit = preferences.edit();

        if(value instanceof String)
        {
            edit.putString(key, (String)value);
        }
        else if(value instanceof Boolean)
        {
            edit.putBoolean(key, (Boolean) value);
        }
        else if(value instanceof Integer)
        {
            edit.putInt(key, (Integer) value);
        }
        else if(value instanceof Set)
        {
            edit.putStringSet(key, (Set<String>) value);
        }
        else
        {
            LogTrace.e("Undefine value");
        }

        edit.commit();
    }

    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }
}
