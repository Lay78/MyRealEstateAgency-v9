package fr.efrei.reagency.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

//This class is an util one
//The class is abstract because we do not want to create any instance
public abstract class AppPreferences
{

  private static final String LOGIN_PREFERENCES_KEY = "loginPreferencesKey";

  public static void saveUserLogin(@NonNull Context context, @NonNull String login)
  {
    final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    final Editor editor = defaultSharedPreferences.edit();
    editor.putString(AppPreferences.LOGIN_PREFERENCES_KEY, login);
    editor.apply();
  }

  @Nullable
  public static String getUserLogin(@NonNull Context context)
  {
    final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    return defaultSharedPreferences.getString(AppPreferences.LOGIN_PREFERENCES_KEY, null);
  }

  //private constructor in order to avoid any instance creation
  private AppPreferences()
  {

  }

}
