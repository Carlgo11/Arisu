package com.carlgo11.arisu;
import org.jibble.pircbot.*;

public class Main {
    
    public static void main(String[] args) throws Exception {
    
        // Now start our bot up.
        Arisu bot = new Arisu();
        bot.connect("irc.kamino.in");
        System.out.println("connected to "+bot.getServer());
        for (int i = 0; i < bot.channels.size(); i++) {
            bot.joinChannel(bot.channels.get(i).toString());
            System.out.println("Joining " + bot.channels.get(i).toString());
        }
        // Enable debugging output.
        bot.setVerbose(true);     
        

        
    }
    
}