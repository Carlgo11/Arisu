package com.carlgo11.arisu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Files {

    public static void savelog(Arisu ar, String channel, String user, String msg) {
        try {
            Files.loadlog(ar, channel);
            File dir = new File("logs");
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(dir + "/" + channel + ".log");
            FileWriter d = new FileWriter(file);
            StringBuilder f = new StringBuilder();
            for (int i = 0; i < ar.log.size(); i++) {
                f.append(ar.log.get(i).toString());
                f.append("\n");
            }
            f.append(msg);
            f.append("\n");
            d.flush();
            d.write(f.toString());
            d.close();
        } catch (Exception ex) {
            System.out.println("savelog error: " + ex);
        }
    }

    public static void loadlog(Arisu ar, String channel) throws Exception {
        File dir = new File("logs");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(dir + "/" + channel + ".log");
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedReader read = new BufferedReader(new FileReader(file));
        String line;
        while ((line = read.readLine()) != null) {
            if (!ar.log.contains(line)) {
                if (!line.startsWith("!") && !line.isEmpty()) {
                    ar.log.add(line);
                    ar.joinChannel(line);
                }
            }
        }
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

    public static void loadChannels(Arisu ar) throws Exception {
        File file = new File("channels.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedReader read = new BufferedReader(new FileReader(file));
        String line;
        while ((line = read.readLine()) != null) {
            if (!ar.channels.contains(line)) {
                if (!line.startsWith("!") && !line.isEmpty()) {
                    ar.channels.add(line);
                    ar.joinChannel(line);
                    Files.loadlog(ar, line);
                }
            }
        }
        System.out.println("loaded channels.txt");
        System.out.println("channels: " + ar.channels.toString());
    }
    
    public static void loadIgnored( Arisu ar) throws Exception{
        File file = new File("ignored.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedReader read = new BufferedReader(new FileReader(file));
        String line;
        while ((line = read.readLine()) != null) {
            if (!ar.ignored.contains(line)) {
                if (!line.startsWith("!") && !line.isEmpty()) {
                    ar.ignored.add(line);
                    ar.joinChannel(line);
                    Files.loadlog(ar, line);
                }
            }
        }
        System.out.println("loaded ignored.txt");
        System.out.println("Ignored: " + ar.ignored.toString());
    }
    public static void saveIgnored(Arisu ar, String channel){
        try {
            File file = new File("ignored.txt");
            FileWriter d = new FileWriter(file);
            StringBuilder f = new StringBuilder();
            for (int i = 0; i < ar.ignored.size(); i++) {
                f.append(ar.ignored.get(i).toString());
                f.append("\n");
            }
            f.append(channel);
            f.append("\n");
            d.flush();
            d.write(f.toString());
            d.close();
            Files.loadIgnored(ar);
        } catch (Exception ex) {
            System.out.println("save-ignored error: " + ex);
        }
    }

}
