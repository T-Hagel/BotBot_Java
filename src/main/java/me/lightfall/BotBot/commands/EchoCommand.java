package me.lightfall.BotBot.commands;

import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.PrivateChannel;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.entities.User;

public class EchoCommand implements Command {

	public String[] getAliases() {
		return new String[] {"echo", "e"};
	}

	public void stop() {
		// Do nothing
	}

	public void run(String[] args, User user, MessageChannel channel) {
		if(args != null) {
			String message;
			
			message = args[0];
			for(int i = 1; i < args.length; i++) {
				message += " " + args[i];
			}
			
			channel.sendMessage(message);
		}
	}

	public String getUsage() {
		return CommandConstants.COMMAND_PREFIX + "echo <message>";
	}

	public String getDescription() {
		return "Echos back the given message";
	}

}
