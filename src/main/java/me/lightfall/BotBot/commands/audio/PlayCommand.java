package me.lightfall.BotBot.commands.audio;

import java.io.IOException;
import java.security.InvalidParameterException;

import javax.sound.sampled.UnsupportedAudioFileException;

import me.lightfall.BotBot.audio.AudioManager;
import me.lightfall.BotBot.commands.Command;
import me.lightfall.BotBot.commands.CommandConstants;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.User;

public class PlayCommand implements Command {

	public String[] getAliases() {
		return new String[] {"play", "p"};
	}

	public void stop() {
		// Do nothing
	}

	public String getUsage() {
		return CommandConstants.COMMAND_PREFIX + "play <song name>";
	}

	public String getDescription() {
		return "Plays the song :arrow_forward:";
	}
	
	public void run(String[] args, User user, MessageChannel channel) {
		if(args != null) {
			if(args.length == 0) {
				AudioManager.play();
				return;
			}
			String message;
			
			message = args[0];
			for(int i = 1; i < args.length; i++) {
				message += " " + args[i];
			}
			
			try {
				AudioManager.play(message.toLowerCase());
				channel.sendMessage("Playing " + message);
			} catch (InvalidParameterException e) {
				channel.sendMessage("That is not a valid song!");
			} catch (IOException | UnsupportedAudioFileException e) {
				channel.sendMessage("Error");
				e.printStackTrace();
			}
		}
	}

}
