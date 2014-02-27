package com.carlgo11.arisu.internalcommands;

import com.carlgo11.arisu.*;
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
            if (!bot.isAuthed(sender)) {
                try {
                    if (Authorization.check_code(AuthSecret.secret, a, time)) {
                        bot.authed.add(sender);
                        bot.sendMessage(sender, Colors.GREEN + "Authorized!");
                    } else {
                        bot.sendMessage(sender, Colors.RED + "Invailid! Try again...");
                    }
                } catch (Exception ex) {
                    Logger.getLogger(AuthCommand.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            bot.sendUsage(sender, "auth <code>");
        }

    }

}
