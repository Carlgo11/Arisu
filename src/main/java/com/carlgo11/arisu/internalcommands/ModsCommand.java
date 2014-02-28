package com.carlgo11.arisu.internalcommands;

import com.carlgo11.arisu.Arisu;
import com.carlgo11.arisu.Commands;

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
