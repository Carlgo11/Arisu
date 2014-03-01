package com.carlgo11.arisu.commands;

import com.carlgo11.arisu.Arisu;
import com.carlgo11.arisu.Commands;
import org.jibble.pircbot.User;

public class LeaveCommand implements Commands {

    @Override
    public String getCommandName() {
        return "leave";
    }

    @Override
    public void handleMessage(Arisu bot, String channel, String sender, String msg, String[] args) {
        User users[] = bot.getUsers(channel);
        User u = null;
        for (User user : users) {
            if (sender.equals(user.getNick())) {
                u = user;
                break;
            }
        }
        if (args.length == 1) {
            if (bot.isAdmin(sender) || bot.isMod(sender) || u.isOp()) {
                bot.partChannel(channel, "Leaving... (Requested by " + sender + ")");
            } else {
                bot.badperms(sender);
            }
        } else if (args.length == 2) {
            if (bot.isAdmin(sender) || bot.isMod(sender)) {
                if (bot.inChannel(args[1])) {
                    bot.partChannel(args[1].toString(), "Leaving... (Requested by " + sender + ")");
                    try {
                        bot.removeChannel(args[1].toString(), sender);
                    } catch (Exception ex) {
                        bot.sendError(sender, ex.toString());
                    }
                } else {
                    bot.sendError(sender, "I'm not in that channel. Did you misspell the name maybe?");
                }
            } else {
                bot.badperms(sender);
            }
        } else {
            bot.sendUsage(sender, "leave (channel)");
        }

    }

}
