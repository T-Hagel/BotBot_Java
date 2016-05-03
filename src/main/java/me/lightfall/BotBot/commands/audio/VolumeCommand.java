package me.lightfall.BotBot.commands.audio;

import me.lightfall.BotBot.audio.AudioManager;
import me.lightfall.BotBot.commands.Command;
import me.lightfall.BotBot.commands.CommandConstants;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.User;

public class VolumeCommand implements Command {

	public String[] getAliases() {
		return new String[] {"vol"};
	}

	public void stop() {
		// Do nothing
	}

	public String getUsage() {
		return CommandConstants.COMMAND_PREFIX + "vol";
	}

	public String getDescription() {
		return "Changes the volume";
	}
	
	public void run(String[] args, User user, MessageChannel channel) {
		if(args.length == 1) {
			try {
				float fl = Float.parseFloat(args[0]);
				AudioManager.volume(fl);
			} catch(Exception e) {
				channel.sendMessage("Bad volume input");
			}
			
		}
	}

}
