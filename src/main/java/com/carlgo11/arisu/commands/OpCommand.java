package com.carlgo11.arisu.commands;

import com.carlgo11.arisu.Arisu;

public class OpCommand implements Commands {

    @Override
    public String getCommandName() {
        return "op";
    }

    @Override
    public void handleMessage(Arisu bot, String channel, String sender, String msg, String[] args) {

        if (args.length == 2) {
            if (bot.isOp(sender, channel)) {
                if (!args[1].startsWith("#")) {
                    bot.op(channel, args[1]);
                }
            } else {
                bot.badperms(sender);
            }
        } else if (args.length == 3) {
            if (args[1].startsWith("#")) {
                if (bot.isOp(sender, args[1])) {
                    bot.op(args[1], args[2]);
                } else {
                    bot.badperms(sender);
                }
            } else {
                bot.sendUsage(sender, "op <channel> (user)");
            }
        } else {
            bot.sendUsage(sender, "op <channel> (user)");
        }

    }

}
