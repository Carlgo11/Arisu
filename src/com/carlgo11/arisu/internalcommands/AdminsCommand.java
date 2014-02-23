package com.carlgo11.arisu.internalcommands;

import com.carlgo11.arisu.Arisu;
import com.carlgo11.arisu.Commands;

public class AdminsCommand implements Commands {

    @Override
    public String getCommandName() {
        return "admins";
    }

    @Override
    public void handleMessage(Arisu bot, String channel, String sender, String msg, String[] args) {
        bot.sendMessage(channel, "Admins: "+bot.admins.toString());
    }

}
