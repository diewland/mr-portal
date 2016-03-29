package diewland.ingress.portal.calc;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    private static String KEY_FACTOR = "KEY_FACTOR";
    public static String VAL_ENLIGHTENDED = "ENLIGHTENDED";
    public static String VAL_RESISTANCE = "RESISTANCE";

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences("myprefs", 0);
    }

    public static String getFactor(Context context) {
        return getPrefs(context).getString(KEY_FACTOR, VAL_ENLIGHTENDED);
    }

    public static void setFactor(Context context, String value) {
        getPrefs(context).edit().putString(KEY_FACTOR, value).commit();
    }

}