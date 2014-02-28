package com.carlgo11.arisu;

public class Main {
    
    public static void main(String[] args) throws Exception {
    
        // Now start our bot up.
        Arisu bot = new Arisu();
        int port = Integer.parseInt(bot.config.getProperty("server-port","6667"));
        bot.connect(bot.config.getProperty("server","moo.kamino.in"),port,bot.config.getProperty("server-pass",""));
        System.out.println("connected to "+bot.getServer());
        bot.identify(bot.config.getProperty("")); //NameServ pass
        for (int i = 0; i < bot.channels.size(); i++) {
            bot.joinChannel(bot.channels.get(i).toString());
            System.out.println("Joining " + bot.channels.get(i).toString());
        }
        // Enable debugging output.
        bot.setVerbose(true);     
        

        
    }
    
}