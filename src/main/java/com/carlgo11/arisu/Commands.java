package com.carlgo11.arisu;

public interface Commands {

	public String getCommandName();

	public void handleMessage(Arisu bot, String channel, String sender, String msg, String[] args);

}
