package com.carlgo11.arisu.commands;

import com.carlgo11.arisu.Arisu;

public class IgnoreCommand implements Commands{

    public String getCommandName() {
        return "ignore";
    }

    public void handleMessage(Arisu bot, String channel, String sender, String msg, String[] args) {
        if(args.length == 2){
            if(bot.isOp(sender, args[1].toLowerCase())){
                
            }else{
                bot.sendError(channel, "You are not a channel operator in that channel. Ask an op to do this for you.");
            }
        }else{
            bot.sendUsage(channel, "ignore <channel>");
        }
        
        
        
    }

}
