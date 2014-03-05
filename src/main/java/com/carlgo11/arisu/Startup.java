package com.carlgo11.arisu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.*;

public class Startup {

    public static void onStartup(ArrayList admins, Arisu ar, ArrayList mods) throws Exception {
        Files.loadadmin(admins);
        Files.loadmod(mods);
        ar.setAutoNickChange(Settings.autorejoin);
        Files.loadChannels(ar);
        ar.identify(ar.config.getProperty("nameserv-password"));
    }
}
