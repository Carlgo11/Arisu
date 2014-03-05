package com.carlgo11.arisu.commands;

import com.carlgo11.arisu.Arisu;
import com.carlgo11.arisu.Files;

public class IgnoreCommand implements Commands {

    public String getCommandName() {
        return "ignore";
    }

    public void handleMessage(Arisu bot, String channel, String sender, String msg, String[] args) {
        if (args.length == 2) {
            try{
            if (bot.isMod(sender) || bot.isAdmin(sender) || bot.isOp(sender, args[1].toLowerCase())) {
                if (!bot.ignored.contains(args[1].toLowerCase())) {
                    Files.saveIgnored(bot, args[1].toLowerCase());
                    bot.sendMessage(channel, args[1].toLowerCase() + " is now ignored. You may kick the bot or ask a moderator to remove her.");
                    if(bot.inChannel(args[1].toLowerCase())){
                        bot.partChannel(args[1].toLowerCase(), "Channel ignored by "+sender+".");
                    }
                } else {
                    bot.sendError(channel, "The channel " + args[1].toLowerCase() + " is already ignored.");
                }
            } else {
                bot.sendError(channel, "You are not a channel operator in that channel. Ask an op to do this for you.");
            }
            }catch(NullPointerException ex){
                bot.sendError(channel, "I'm not in that channel. Please ask an moderator or admin to do this or temporarily invite me to that channel.");
            }
        } else {
            bot.sendUsage(channel, "ignore <channel>");
        }

    }

}
