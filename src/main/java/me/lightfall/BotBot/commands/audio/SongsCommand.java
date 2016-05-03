package me.lightfall.BotBot.commands.audio;

import me.lightfall.BotBot.audio.AudioManager;
import me.lightfall.BotBot.commands.Command;
import me.lightfall.BotBot.commands.CommandConstants;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.User;

public class SongsCommand implements Command {

	public String[] getAliases() {
		return new String[] {"songs"};
	}

	public void stop() {
		// Do nothing
	}

	public String getUsage() {
		return CommandConstants.COMMAND_PREFIX + "songs";
	}

	public String getDescription() {
		return "Returns the list of available songs";
	}
	
	public void run(String[] args, User user, MessageChannel channel) {
		int count = 0;
		String message = "";
		for(String name : AudioManager.getFiles()) {
			message += name + "\n";
			count++;
			if(count >= 40) {
				count = 0;
				channel.sendMessage(message);
				message = "";
				try {
					Thread.currentThread().sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		channel.sendMessage(message);
	}

}
