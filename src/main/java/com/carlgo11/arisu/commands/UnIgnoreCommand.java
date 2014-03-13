package com.carlgo11.arisu.commands;

import com.carlgo11.arisu.Arisu;
import com.carlgo11.arisu.Files;

public class UnIgnoreCommand implements Commands {

    public String getCommandName() {
        return "unignore";
    }

    public void handleMessage(Arisu bot, String channel, String sender, String msg, String[] args) {
        if (args.length == 2) {
            if (bot.isMod(sender) || bot.isAdmin(sender)) {
                if (bot.ignored.contains(args[1].toLowerCase())) {
                    for (int i = 0; i < bot.ignored.size(); i++) {
                        if (bot.ignored.get(i).equalsIgnoreCase(args[1].toLowerCase())) {
                            bot.ignored.remove(i);
                        }
                    }
                    Files.saveIgnored(bot);
                    bot.sendMessage(channel, args[1].toLowerCase() + " is no longer ignored. You may now invite me to your channel if you wish. :)");
                } else {
                    bot.sendError(channel, "The channel " + args[1].toLowerCase() + " is not listed as ignored.");
                }
            } else {
                bot.sendError(channel, "Since I'm not in that channel I can't see if you're a op or not. Please ask an mod/admin to unignore that channel.");
            }
        } else {
            bot.sendUsage(channel, "unignore <channel>");
        }
    }

}
