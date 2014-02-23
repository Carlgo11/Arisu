package com.carlgo11.arisu.internalcommands;

import com.carlgo11.arisu.Arisu;
import com.carlgo11.arisu.Commands;

public class ShutdownCommand implements Commands{

    @Override
    public String getCommandName() {
        return "shutdown";
    }

    @Override
    public void handleMessage(Arisu bot, String channel, String sender, String msg, String[] args) {
        if (bot.admins.contains(sender)) {
                    bot.onDisable(sender);
                } else {
                    bot.badperms(sender);
                }
    }
    
}
