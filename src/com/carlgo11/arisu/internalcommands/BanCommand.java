/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlgo11.arisu.internalcommands;

import com.carlgo11.arisu.Arisu;
import com.carlgo11.arisu.Commands;

/**
 *
 * @author Carl
 */
public class BanCommand implements Commands {

    @Override
    public String getCommandName() {
        return "ban";
    }

    @Override
    public void handleMessage(Arisu bot, String channel, String sender, String msg, String[] args) {
        if (args.length == 1) {
            bot.sendError(channel, bot.commandPrefix + "ban <user> (reason)");
        }
    }

}
