package com.carlgo11.arisu;

import com.carlgo11.arisu.commands.*;
import java.io.*;
import java.util.*;
import org.jibble.pircbot.*;

public class Arisu extends PircBot {

    public ArrayList<String> admins = new ArrayList<String>(); //ArrayList containing all admins
    public ArrayList<String> mods = new ArrayList<String>(); //ArrayList containing all mods
    public ArrayList<String> channels = new ArrayList<String>(); //ArrayList containing all channels
    public ArrayList<String> ignored = new ArrayList<String>(); //ArrayList containing all channels whom want to be ignored
    public ArrayList<String> log = new ArrayList<String>();
    public Properties config = new Properties(); //Bot properties
    public String commandprefix = "?"; //Prefix of all commands
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
        //cmds.add(new BanCommand());
        cmds.add(new InviteCommand());
        cmds.add(new JoinCommand());
        cmds.add(new LeaveCommand());
        cmds.add(new ShutdownCommand());
        cmds.add(new SayCommand());
        cmds.add(new ActCommand());
        cmds.add(new OpCommand());
        cmds.add(new ShellCommand());
        cmds.add(new AuthCommand());
        cmds.add(new GitHubCommand());
        cmds.add(new IgnoreCommand());
        cmds.add(new UnIgnoreCommand());
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

    public void badperms(String sender) {
        sendError(sender, "You don't have permission to perform that action.");
    }

    public void ignoredChannel(String channel, String target) {
        sendError(channel, "The ops of " + target.toLowerCase() + " have asked not to be bothered by Arisu");
    }

    public boolean isAdmin(String sender) {
        if (admins.contains(sender)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isMod(String sender) {
        if (mods.contains(sender)) {
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

    public boolean isVoice(String sender, String channel) {
        User users[] = getUsers(channel);
        User u = null;
        for (User usa : users) {
            if (sender.equals(usa.getNick())) {
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
        if (cleancmd.startsWith("?help")) {
            StringBuilder cmdlist = new StringBuilder();
            for (Commands command : cmds) {
                cmdlist.append("?");
                cmdlist.append(command.getCommandName());
                cmdlist.append(", ");
            }
            this.sendMessage(channel, "Commands: " + cmdlist.toString());
        } else if (cleancmd.startsWith(commandprefix)) {
            for (Commands command : cmds) {
                if (cleancmd.startsWith(commandprefix + command.getCommandName())) {
                    Files.savelog(this, channel, sender, msg);
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
        if (!ignored.contains(channel)) {
            try {
                this.appendChannel(channel, sender);
            } catch (IOException ex) {
                this.sendError(sender, ex.toString());
            }
        } else {
            ignoredChannel(sender, channel);
        }
    }

    public boolean inChannel(String channel) {
        boolean outp = false;
        for (int i = 0; i < channels.size(); i++) {
            if (channels.get(i).equalsIgnoreCase(channel)) {
                outp = true;
            }
        }
        return outp;
    }
    
    @Override
    protected void onJoin(String channel, String sender, String login, String hostname) {
        if(sender != getNick()){
            
        }
    }

    public boolean onPartAll(String sender, String reason) {
        int d = channels.size();
        d--;
        for (int i = 0; i <= channels.size(); i++) {
            boolean f = inChannel(channels.get(d));
            if (!f) {
                return true;
            } else {
                try {
                    if (reason == null) {
                        this.partChannel(channels.get(i), config.getProperty("disconnect-message") + "  (Requested by " + sender + ")");
                    } else {
                        this.partChannel(channels.get(i), config.getProperty("disconnect-message") + " (Reason: " + reason + "  (Requested by " + sender + "))");
                    }
                } catch (Exception ex) {
                    return true;
                }
            }
        }
        return false;
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
