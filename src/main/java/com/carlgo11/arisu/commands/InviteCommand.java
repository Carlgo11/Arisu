package com.carlgo11.arisu.commands;

import com.carlgo11.arisu.Arisu;

public class InviteCommand implements Commands{

    @Override
    public String getCommandName() {
        return "invite";
    }

    @Override
    public void handleMessage(Arisu bot, String channel, String sender, String msg, String[] args) {
        bot.sendError(sender, "Just do /invite "+bot.getName());
    }

}
