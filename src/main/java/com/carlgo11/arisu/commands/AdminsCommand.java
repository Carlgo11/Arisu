package com.carlgo11.arisu.commands;

import com.carlgo11.arisu.Arisu;

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