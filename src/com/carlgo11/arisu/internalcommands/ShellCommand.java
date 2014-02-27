package com.carlgo11.arisu.internalcommands;

import com.carlgo11.arisu.Arisu;
import com.carlgo11.arisu.Commands;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ShellCommand implements Commands {

    @Override
    public String getCommandName() {
        return "shell";
    }

    @Override
    public void handleMessage(Arisu bot, String channel, String sender, String msg, String[] args) {
        if(bot.isAuthed(sender)){
            StringBuilder al = new StringBuilder();
            for(int i = 1; i < args.length; i++){
                al.append(args[i]);
                al.append(" ");
            }
            bot.sendMessage(channel, shell(al.toString()));
        }else{
            bot.needauth(sender);
        }
    }
    
    public String shell(String s){
        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(s);
            p.waitFor();
            BufferedReader reader
                    = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();
    }

}
