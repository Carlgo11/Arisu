/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlgo11.arisu.commands;

import com.carlgo11.arisu.Arisu;
import java.io.IOException;

public class JoinCommand implements Commands {

    @Override
    public String getCommandName() {
        return "join";
    }

    @Override
    public void handleMessage(Arisu bot, String channel, String sender, String msg, String[] args) {
        if (bot.isAdmin(sender) || bot.isMod(sender)) {
            if (args.length == 2) {
                bot.sendMessage(channel, "Joining " + args[1] + "...");
                try {
                    bot.appendChannel(args[1], sender);
                } catch (IOException ex) {
                    bot.sendError(sender, ex.toString());
                }
            } else {
                bot.sendUsage(channel, "join <channel>");
            }
        } else {
            bot.badperms(channel);
        }
    }

}
