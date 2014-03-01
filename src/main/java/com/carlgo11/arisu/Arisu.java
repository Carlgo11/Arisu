package com.carlgo11.arisu;

import com.carlgo11.arisu.internalcommands.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.jibble.pircbot.*;

public class Arisu extends PircBot {

    public String commandprefix = "?"; //Prefix of all commands
    public ArrayList<String> admins = new ArrayList<String>(); //ArrayList containing all admins
    public ArrayList<String> mods = new ArrayList<String>(); //ArrayList containing all mods
    public ArrayList<String> channels = new ArrayList<String>(); //ArrayList containing all channels
    public ArrayList<String> log = new ArrayList<String>();
    public Properties config = new Properties(); //Bot properties
    private final List<Commands> cmds; //List of commands

    public Arisu() {
        try {
            config.load(new FileInputStream("config.properties"));
        } catch (IOException ex) {
            System.err.println("Error loading config.properties.\n" + ex);
            System.exit(0);
        }

        try {
            Startup.onStartup(admins, this, mods);
            this.setName(config.getProperty("nick", "Arisu"));
            this.setLogin(config.getProperty("realname", "Arisu"));
        } catch (Exception ex) {
            System.out.println("Startup failed.\n" + ex);
            System.exit(0);
        }

        cmds = new ArrayList<Commands>();

        cmds.add(new AdminsCommand());
        cmds.add(new ModsCommand());
        cmds.add(new BanCommand());
        cmds.add(new HelloCommand());
        cmds.add(new BanCommand());
        cmds.add(new InviteCommand());
        cmds.add(new JoinCommand());
        cmds.add(new LeaveCommand());
        cmds.add(new ShutdownCommand());
        cmds.add(new SayCommand());
        cmds.add(new ActCommand());
        cmds.add(new OpCommand());
        cmds.add(new ShellCommand());
        cmds.add(new AuthCommand());
    }

    public void sendError(String target, String reason) {
        this.sendMessage(target, Colors.RED + "[Error] " + Colors.NORMAL + reason);
    }

    public void needauth(String user) {
        sendError(user, "You don't have permission to perform that action. You'll need to authorize first. ?auth (code)");
    }

    public void sendUsage(String target, String usage) {
        sendMessage(target, "<> = Required\t() = Optional");
        sendError(target, "Usage: " + commandprefix + usage);
    }

    public void badperms(String target) {
        sendError(target, "You don't have permission to perform that action.");
    }

    public boolean isAdmin(String user) {
        if (admins.contains(user)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isMod(String user) {
        if (mods.contains(user)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isOp(String sender, String channel) {
        User users[] = getUsers(channel);
        User u = null;
        for (User user : users) {
            String us = user.getNick().replace("~", "");
            System.out.println("user: " + us);
            if (sender.equals(us)) {
                u = user;
                break;
            }
        }

        if (u.isOp() || u.getNick().startsWith("~")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isVoice(String user, String channel) {
        User users[] = getUsers(channel);
        User u = null;
        for (User usa : users) {
            if (user.equals(usa.getNick())) {
                u = usa;
                break;
            }
        }
        if (u.hasVoice()) {
            return true;
        } else {
            return false;
        }
    }

    public void appendChannel(String channel, String sender) throws IOException {
        File file = new File("channels.txt");
        FileWriter d = new FileWriter(file);
        channels.add(channel.toLowerCase());
        StringBuilder f = new StringBuilder();
        for (int i = 0; i < channels.size(); i++) {
            f.append(channels.get(i).toString());
            f.append("\n");
        }
        System.out.println("f:" + f.toString());
        d.flush();
        d.write(f.toString());
        d.close();
        this.joinChannel(channel);
        this.sendMessage(channel, "Hello ya'll. I was invited by " + sender + ".");
    }

    public void removeChannel(String channel, String sender) throws IOException {
        File file = new File("channels.txt");
        FileWriter d = new FileWriter(file);
        channels.remove(channel);
        StringBuilder f = new StringBuilder();
        for (int i = 0; i < channels.size(); i++) {
            if (!channels.get(i).equalsIgnoreCase(channel)) {
                f.append(channels.get(i).toString().toLowerCase());
                f.append("\n");
            }
        }
        d.flush();
        d.write(f.toString());
        d.close();
    }

    public void onMessage(String channel, String sender, String login, String hostname, String msg) {
        String[] args = msg.split(" ");
        String cleancmd = args[0];
        String message = msg.replace(commandprefix, "").toLowerCase();
        if (msg.startsWith("?help")) {
            StringBuilder cmdlist = new StringBuilder();
            for (Commands command : cmds) {
                cmdlist.append("?");
                cmdlist.append(command.getCommandName());
                cmdlist.append(", ");
            }
            this.sendMessage(channel, "Commands: " + cmdlist.toString());
        } else if (msg.startsWith(commandprefix)) {
            for (Commands command : cmds) {
                if (message.startsWith(command.getCommandName())) {
                    Startup.savelog(this, channel, sender, msg);
                    command.handleMessage(this, channel, sender, message.replace(command.getCommandName(), "").trim(), args);
                }
            }

        }

    }

    public void onPrivateMessage(String sender, String login, String hostname, String msg) {
        String[] args = msg.split(" ");
        String cleancmd = args[0];
        String message = pmmsg(msg, commandprefix);

        for (Commands command : cmds) {
            if (message.startsWith(command.getCommandName())) {
                command.handleMessage(this, sender, sender, message.replace(command.getCommandName(), "").trim(), args);
            }
        }
    }

    String pmmsg(String msg, String prefix) {
        if (msg.startsWith(commandprefix)) {
            return msg.replace(commandprefix, "").toLowerCase().trim();
        } else {
            return msg;
        }
    }

    public void onInvite(String targetNick, String sender, String sourceLogin, String sourceHostname, String channel) {
        try {
            this.appendChannel(channel, sender);
        } catch (IOException ex) {
            this.sendError(sender, ex.toString());
        }
    }

    public boolean inChannel(String channel) {
        boolean outp = false;
        for (int i = 0; i < channels.size(); i++) {
            if (channels.get(i).equalsIgnoreCase(channel)) {
                outp=true;
            }
        }
        return outp;
    }

    public boolean onPartAll(String sender, String reason) {
        boolean done = false;
        for (int i = 0; i <= channels.size(); i++) {
            int d = channels.size();
            d--;
            if (!inChannel(channels.get(d))) {
                done = true;
                break;
            }
            if (reason == null) {
                this.partChannel(channels.get(i), config.getProperty("disconnect-message") + "  (Requested by " + sender + ")");
            } else {
                this.partChannel(channels.get(i), config.getProperty("disconnect-message") + " (Reason: " + reason + "  (Requested by " + sender + "))");
            }
        }
        return done;
    }

    public void onDisable(String sender, String reason) {
        while (1 != 0) {
            boolean partlyParted = onPartAll(sender, reason);
            if (partlyParted) {
                System.out.println("=============\tBot disabled by " + sender + "\t=============");
                this.disconnect();
                System.exit(0);
            }
        }
    }
}
