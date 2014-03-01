package com.carlgo11.arisu.commands;

import com.carlgo11.arisu.Arisu;

public class GitHubCommand implements Commands{

    public String getCommandName() {
        return "github";
    }

    public void handleMessage(Arisu bot, String channel, String sender, String msg, String[] args) {
        bot.sendMessage(channel, "Arisu github page: http://git.io/jdwyiQ");
    }

}
