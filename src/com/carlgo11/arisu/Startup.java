package com.carlgo11.arisu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.*;

public class Startup{

    public static void onStartup(ArrayList admins, Arisu ar, ArrayList mods) {
        try {
            loadadmin(admins);
            loadmod(mods);
            ar.setAutoNickChange(Settings.autorejoin);
            loadChannels(ar);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void loadadmin(ArrayList admins) throws Exception {
        File file = new File("admins.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedReader read = new BufferedReader(new FileReader(file));
        String line;
        while ((line = read.readLine()) != null) {
            if (!admins.contains(line)) {
                admins.add(line);
            }
        }
        System.out.println("loaded admins.txt");
        System.out.println("admins: " + admins.toString());
    }
    
    public static void loadmod(ArrayList mods) throws Exception {
        File file = new File("mods.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedReader read = new BufferedReader(new FileReader(file));
        String line;
        while ((line = read.readLine()) != null) {
            if (!mods.contains(line)) {
                mods.add(line);
            }
        }
        System.out.println("loaded mods.txt");
        System.out.println("mods: " + mods.toString());
    }

    public static void loadChannels(Arisu ar) throws Exception {
        File file = new File("channels.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedReader read = new BufferedReader(new FileReader(file));
        String line;
        while ((line = read.readLine()) != null) {
            if (!ar.channels.contains(line)) {
                if(!line.startsWith("!")){
                ar.channels.add(line);
                ar.joinChannel(line);
                }
            }
        }
        System.out.println("loaded channels.txt");
        System.out.println("channels: " + ar.channels.toString());
    }

}
