package com.carlgo11.arisu;

public interface Commands {

	// Each BotCommand implementor will return the command name to which they respond
	public String getCommandName();

	// The method where each BotCommand implementor will handle the event
	public void handleMessage(Arisu bot, String channel, String sender, String msg, String[] args);

}
