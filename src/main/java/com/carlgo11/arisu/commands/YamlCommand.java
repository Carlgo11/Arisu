package com.carlgo11.arisu.commands;

import com.carlgo11.arisu.Arisu;

public class YamlCommand implements Commands{

    public String getCommandName() {
        return "yaml";
    }

    public void handleMessage(Arisu bot, String channel, String sender, String msg, String[] args) {
        if(args.length == 2){
            String pastebin = args[1];
            if(pastebin.contains("pastebin.com") || pastebin.contains("http://") || pastebin.contains("simplemsg_") || pastebin.contains("cmdbnc_") || pastebin.contains("preip_")){
                try{
                   pastebin = pastebin.replace("http://", "").replace("www.", "").replace("pastebin.com", "").replace("/", "").replace("simplemsg_", "").replace("cmdbnc_", "").replace("preip_", "");
                }catch(Exception ex){
                    bot.sendMessage(channel, ex.toString());
                }
            }
                bot.sendMessage(channel, "http://yaml-online-parser.appspot.com/?url=http://pastebin.com/raw.php?i="+pastebin);
            
            
        }else{
            bot.sendUsage(sender, "yaml <pastebin token>");
        }
    }

}
