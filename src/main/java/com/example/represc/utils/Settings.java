package com.example.represc.utils;

import java.util.Locale;
import java.util.prefs.Preferences;

/**
 * @author Schmetz Arnaud
 * @version 1.0
 *
 * @overview Settings serves to store and retrieve the value of different settings the user may change, like the language.
 *
 * @specfield instance : Settings // {@code instance} is a variable used to implement Settings as a Singleton.;
 * @specfield settings : Preferences // {@code settings} is used to store the user preferences, settings, like the language.;
 * @specfield languageKey : String // {@code languageKey} is used to make sure that the same String is always used for the language key in {@code settings}.
 */
public class Settings {

    private static Settings instance = null;
    private final Preferences settings = Preferences.userRoot().node(this.getClass().getName());
    private final String languageKey = "language";

    static public Settings getSettings(){
        if (instance==null) instance = new Settings();
        return instance;
    }

    public void setSettings(Locale locale){
        settings.put(languageKey ,locale.getLanguage());
    }

    public void setLocale(Locale locale){
        settings.put(languageKey ,locale.getLanguage());
    }
    public Locale getLocale(){
        String language = settings.get(languageKey, "en");
        return new Locale(language);
    }
}
