package com.carlgo11.arisu.commands;

import com.carlgo11.arisu.Arisu;

public class ModsCommand implements Commands {

    @Override
    public String getCommandName() {
        return "mods";
    }

    @Override
    public void handleMessage(Arisu bot, String channel, String sender, String msg, String[] args) {
        bot.sendMessage(channel, "Mods: "+bot.mods.toString());
    }
}
