package me.lightfall.BotBot.commands.audio;

import me.lightfall.BotBot.audio.AudioManager;
import me.lightfall.BotBot.commands.Command;
import me.lightfall.BotBot.commands.CommandConstants;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.User;

public class StopCommand implements Command {

	public String[] getAliases() {
		return new String[] {"stop"};
	}

	public void stop() {
		// Do nothing
	}

	public String getUsage() {
		return CommandConstants.COMMAND_PREFIX + "stop";
	}

	public String getDescription() {
		return "Stops the song";
	}
	
	public void run(String[] args, User user, MessageChannel channel) {
		AudioManager.stop();
	}

}
