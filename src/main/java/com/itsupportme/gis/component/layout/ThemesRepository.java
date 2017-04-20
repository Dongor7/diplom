package com.itsupportme.gis.component.layout;


import java.util.HashMap;

public class ThemesRepository {

    public static final Integer THEME_RED       = 0;
    public static final Integer THEME_PURPLE    = 1;
    public static final Integer THEME_BLUE      = 2;
    public static final Integer THEME_GREEN     = 3;
    public static final Integer THEME_YELLOW    = 4;
    public static final Integer THEME_BLACK     = 5;

    public static final Integer THEME_RED_LIGHT       = 6;
    public static final Integer THEME_PURPLE_LIGHT    = 7;
    public static final Integer THEME_BLUE_LIGHT      = 8;
    public static final Integer THEME_GREEN_LIGHT     = 9;
    public static final Integer THEME_YELLOW_LIGHT    = 10;
    public static final Integer THEME_BLACK_LIGHT     = 11;


    public static HashMap<Integer, String> getThemes(){

        HashMap<Integer, String> themes = new HashMap<>();
        themes.put(THEME_RED,       "red");
        themes.put(THEME_YELLOW,    "yellow");
        themes.put(THEME_BLUE,      "blue");
        themes.put(THEME_GREEN,     "green");
        themes.put(THEME_PURPLE,    "purple");
        themes.put(THEME_BLACK,     "black");

        themes.put(THEME_RED_LIGHT,       "red-light");
        themes.put(THEME_YELLOW_LIGHT,    "yellow-light");
        themes.put(THEME_BLUE_LIGHT,      "blue-light");
        themes.put(THEME_GREEN_LIGHT,     "green-light");
        themes.put(THEME_PURPLE_LIGHT,    "purple-light");
        themes.put(THEME_BLACK_LIGHT,     "black-light");

        return themes;
    }
}
