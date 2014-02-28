package com.carlgo11.arisu;

import java.util.ArrayList;

public class Alias {

    static public ArrayList<String> aliases = new ArrayList<String>();
    static public ArrayList<String> aliastocmd = new ArrayList<String>();

    public static String onAlias(String alias) {
        String output = null;
        if (aliases.contains(alias)) {
            for (int i = 0; i < aliases.size(); i++) {
                if (aliases.get(i).equalsIgnoreCase(alias)) {
                    output = aliastocmd.get(i).toString();
                }
            }
        }
        return output;
    }

}
