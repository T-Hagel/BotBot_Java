package me.lightfall.BotBot.commands.audio;

import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import me.lightfall.BotBot.audio.AudioManager;
import me.lightfall.BotBot.commands.Command;
import me.lightfall.BotBot.commands.CommandConstants;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.User;

public class QueueCommand implements Command {

	public String[] getAliases() {
		return new String[] {"queue"};
	}

	public void stop() {
		// Do nothing
	}

	public String getUsage() {
		return CommandConstants.COMMAND_PREFIX + "queue";
	}

	public String getDescription() {
		return "Queues the song to the playlist";
	}
	
	public void run(String[] args, User user, MessageChannel channel) {
		if(args != null) {
			String message;
			
			message = args[0];
			for(int i = 1; i < args.length; i++) {
				message += " " + args[i];
			}
			
			try {
				channel.sendMessage("Queued song " + message + ".\nPosition in queue is: " + AudioManager.queuePlaylist(message));
			} catch (IOException | UnsupportedAudioFileException e) {
				channel.sendMessage("Error");
				e.printStackTrace();
			}
		}
	}

}
