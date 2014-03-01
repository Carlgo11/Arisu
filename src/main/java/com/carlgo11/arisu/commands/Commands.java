package com.carlgo11.arisu.commands;

import com.carlgo11.arisu.Arisu;

public interface Commands {

	public String getCommandName();

	public void handleMessage(Arisu bot, String channel, String sender, String msg, String[] args);

}
