package com.carlgo11.arisu.commands;

import com.carlgo11.arisu.Arisu;
import static java.lang.Thread.sleep;

public class ActCommand implements Commands {

    @Override
    public String getCommandName() {
        return "act";
    }

    @Override
    public void handleMessage(Arisu bot, String channel, String sender, String msg, String[] args) {
        if (bot.isAdmin(sender) || bot.isMod(sender)) {
            if (args.length == 1) {
                bot.sendUsage(sender, "act <message>");
            } else {
                if (!args[1].startsWith("#")) {
                    StringBuilder outp = new StringBuilder();
                    for (int i = 1; i < args.length; i++) {
                        outp.append(args[i]);
                        outp.append(" ");
                    }
                    bot.sendAction(channel, outp.toString());
                } else {
                    StringBuilder outp = new StringBuilder();
                    for (int i = 2; i < args.length; i++) {
                        outp.append(args[i]);
                        outp.append(" ");
                    }
                    if (!bot.inChannel(args[1])) {
                        bot.joinChannel(args[1]);

                        bot.sendAction(args[1], outp.toString());

                        try {
                            sleep(10l);
                            bot.partChannel(args[1], "Act command requested by " + sender + ".");
                        } catch (InterruptedException ex) {
                            bot.sendError(sender, ex.toString());
                        }
                    } else {
                        bot.sendAction(args[1], outp.toString());
                    }
                }
            }
        } else {
            bot.badperms(sender);
        }
    }

}
