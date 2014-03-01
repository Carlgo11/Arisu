package com.carlgo11.arisu.commands;

import com.carlgo11.arisu.Arisu;
import com.carlgo11.arisu.Commands;

public class BanCommand implements Commands {

    @Override
    public String getCommandName() {
        return "ban";
    }

    @Override
    public void handleMessage(Arisu bot, String channel, String sender, String msg, String[] args) {
        if (args.length == 1) {
            bot.sendError(channel, bot.commandprefix + "ban <user> (reason)");
        }
    }

}
