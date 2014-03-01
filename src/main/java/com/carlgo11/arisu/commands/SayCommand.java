package com.carlgo11.arisu.commands;

import com.carlgo11.arisu.Arisu;
import static java.lang.Thread.sleep;

public class SayCommand implements Commands {

    @Override
    public String getCommandName() {
        return "say";
    }

    @Override
    public void handleMessage(Arisu bot, String channel, String sender, String msg, String[] args) {
        if (bot.isAdmin(sender) || bot.isMod(sender)) {
            if (args.length == 1) {
                bot.sendUsage(sender, "say <message>");
            } else {
                if (!args[1].startsWith("#")) {
                    StringBuilder outp = new StringBuilder();
                    for (int i = 1; i < args.length; i++) {
                        outp.append(args[i]);
                        outp.append(" ");
                    }
                    bot.sendMessage(channel, outp.toString());
                } else {
                    boolean inchnl = true;
                    if (!bot.inChannel(args[1])) {
                        bot.joinChannel(args[1]);
                        inchnl = false;
                    }
                    StringBuilder outp = new StringBuilder();
                    for (int i = 2; i < args.length; i++) {
                        outp.append(args[i]);
                        outp.append(" ");
                    }

                    bot.sendMessage(args[1], outp.toString());
                    if (!inchnl) {
                        try {
                            sleep(10l);
                            bot.partChannel(args[1], "Say command requested by "+sender+".");
                        } catch (InterruptedException ex) {
                            bot.sendError(sender, ex.toString());
                        }
                    }
                }
            }
        } else {
            bot.badperms(sender);
        }
    }

}
