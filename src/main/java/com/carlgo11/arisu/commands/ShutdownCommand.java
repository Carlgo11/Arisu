package com.carlgo11.arisu.commands;

import com.carlgo11.arisu.Arisu;
import com.carlgo11.arisu.auth.AuthAPI;

public class ShutdownCommand implements Commands{

    @Override
    public String getCommandName() {
        return "shutdown";
    }

    @Override
    public void handleMessage(Arisu bot, String channel, String sender, String msg, String[] args) {
        if (AuthAPI.isAuthed(sender)) {
            if(args.length == 1){
                    bot.onDisable(sender,null);
            }else if(args.length > 1){
                 bot.onDisable(sender,msg);
            }else{
                    bot.sendUsage(channel, "shutdown (reason)");
                    }
                } else {
                    bot.needauth(channel);
                }
    }
    
}
