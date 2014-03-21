package com.carlgo11.arisu.commands;

import com.carlgo11.arisu.Arisu;

public class BanCommand implements Commands {

    @Override
    public String getCommandName() {
        return "ban";
    }

    @Override
    public void handleMessage(Arisu bot, String channel, String sender, String msg, String[] args) {
        if (args.length == 2) {
            if (!channel.startsWith("#")) {
                if (bot.isOp(sender, args[1].toLowerCase()) || bot.isVoice(sender, args[1].toLowerCase())) {
                    if (containsHostmask(args[1].toString())) {
                        bot.ban(channel, args[1].toString());
                    } else {
                        bot.ban(channel, args[1].toString() + "!*@*");
                    }
                } else {
                    bot.badperms(sender);
                }
            } else {
                bot.sendUsage(sender, "ban (channel) <user>");
            }
        } else if (args.length == 3) {
            if (channel.startsWith("#")) {
                if (bot.isOp(sender, args[1].toLowerCase()) || bot.isVoice(sender, args[1].toLowerCase())) {
                    if (containsHostmask(args[2].toString())) {
                        bot.ban(channel, args[2].toString());
                    } else {
                        bot.ban(channel, args[2].toString() + "!*@*");
                    }
                } else {
                    bot.badperms(sender);
                }
            } else {
                bot.sendUsage(sender, "ban (channel) <user>");
            }
        } else {
            bot.sendUsage(sender, "ban (channel) <user>");
        }

    }

    boolean containsHostmask(String s) {
        if (s.contains("!") && s.contains("@")) {
            return true;
        } else {
            return false;
        }
    }
}
