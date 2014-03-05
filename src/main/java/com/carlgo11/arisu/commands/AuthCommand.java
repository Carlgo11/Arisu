package com.carlgo11.arisu.commands;

import com.carlgo11.arisu.*;
import com.carlgo11.arisu.auth.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jibble.pircbot.Colors;

public class AuthCommand implements Commands {

    @Override
    public String getCommandName() {
        return "auth";
    }

    @Override
    public void handleMessage(Arisu bot, String channel, String sender, String msg, String[] args) {
        if (args.length == 2) {
            long a = Long.valueOf(args[1]);
            long time = new Date().getTime() / 30000;
            if (!AuthAPI.isAuthed(sender)) {
                try {
                    if (Authorization.check_code(a, time)) {
                        AuthAPI.addAuth(sender);
                        bot.sendMessage(sender, Colors.GREEN + "Authorized!");
                    } else {
                        bot.sendError(channel, Colors.RED + "Invailid! Try again...");
                    }
                } catch (Exception ex) {
                    Logger.getLogger(AuthCommand.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                bot.sendError(channel, Colors.RED + "Already authorized.");
            }
        } else {
            bot.sendUsage(channel, "auth <code>");
        }

    }

}
