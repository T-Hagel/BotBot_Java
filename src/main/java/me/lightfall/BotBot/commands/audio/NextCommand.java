package me.lightfall.BotBot.commands.audio;

import me.lightfall.BotBot.audio.AudioManager;
import me.lightfall.BotBot.commands.Command;
import me.lightfall.BotBot.commands.CommandConstants;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.User;

public class NextCommand implements Command {

	public String[] getAliases() {
		return new String[] {"next"};
	}

	public void stop() {
		// Do nothing
	}

	public String getUsage() {
		return CommandConstants.COMMAND_PREFIX + "next";
	}

	public String getDescription() {
		return "Goes to the next song in the playlist";
	}
	
	public void run(String[] args, User user, MessageChannel channel) {
		AudioManager.next();
	}

}
