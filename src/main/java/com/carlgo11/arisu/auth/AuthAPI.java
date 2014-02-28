package com.carlgo11.arisu.auth;

import java.util.ArrayList;

public class AuthAPI {
    
    protected static ArrayList<String> authed = new ArrayList<String>();
    
    public static boolean isAuthed(String user) {
        if (authed.contains(user)) {
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean addAuth(String user){
        if(!isAuthed(user)){
            authed.add(user);
            return true;
        }else{
            return false;
        }
    }

}
