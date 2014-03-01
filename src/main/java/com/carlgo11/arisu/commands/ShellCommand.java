package com.carlgo11.arisu.commands;

import com.carlgo11.arisu.Arisu;
import com.carlgo11.arisu.auth.AuthAPI;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ShellCommand implements Commands {

    @Override
    public String getCommandName() {
        return "shell";
    }

    @Override
    public void handleMessage(Arisu bot, String channel, String sender, String msg, String[] args) {
        if(AuthAPI.isAuthed(sender)){
            StringBuilder al = new StringBuilder();
            for(int i = 1; i < args.length; i++){
                al.append(args[i]);
                al.append(" ");
            }
            shell(al.toString(), channel, bot);
        }else{
            bot.needauth(sender);
        }
    }
    
    public void shell(String s, String channel, Arisu bot){

        Process p;
        try {
            p = Runtime.getRuntime().exec(s);
            p.waitFor();
            BufferedReader reader
                    = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                bot.sendMessage(channel, line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

            
    }

}
