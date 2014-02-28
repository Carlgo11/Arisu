package com.carlgo11.arisu.internalcommands;

import com.carlgo11.arisu.Arisu;
import com.carlgo11.arisu.Commands;
import org.jibble.pircbot.Colors;

public class HelloCommand implements Commands {

    public String getCommandName() {
        return "hello";
    }

    @Override
    public void handleMessage(Arisu bot, String channel, String sender, String msg, String[] args) {
        bot.sendMessage(channel, Colors.GREEN + "Hello World!");
    }
}
