package me.lightfall.BotBot.commands.audio;

import me.lightfall.BotBot.Constants;
import me.lightfall.BotBot.audio.AudioManager;
import me.lightfall.BotBot.commands.Command;
import me.lightfall.BotBot.commands.CommandConstants;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.entities.VoiceChannel;

public class JoinCommand implements Command {

	public String[] getAliases() {
		return new String[] {"join"};
	}

	public String getUsage() {
		return CommandConstants.COMMAND_PREFIX + "join <id>";
	}

	public String getDescription() {
		return "Has Bot Bot join the given voice channel";
	}

	public void stop() {
		// TODO Auto-generated method stub
		
	}

	public void run(String[] args, User user, MessageChannel channel) {
		String message = "Error";
		if(args.length == 0) {
			message = "You must enter a channel ID!";
		} else if(args.length >= 1) {
			VoiceChannel ch = Constants.getApi().getVoiceChannelById(args[0]);
			if(ch == null) {
				message = "Invalid ID";
			} else {
				AudioManager.join(ch);
				message = "Joined " + ch.getName();
			}
		}
		channel.sendMessage(message);
	}
}
