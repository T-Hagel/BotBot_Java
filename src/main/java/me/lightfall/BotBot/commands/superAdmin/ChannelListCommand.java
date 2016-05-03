package me.lightfall.BotBot.commands.superAdmin;

import me.lightfall.BotBot.Constants;
import me.lightfall.BotBot.commands.Command;
import me.lightfall.BotBot.commands.CommandConstants;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.PrivateChannel;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.entities.VoiceChannel;

public class ChannelListCommand implements Command {

	public String[] getAliases() {
		return new String[] {"channelList", "cl"};
	}

	public String getUsage() {
		return CommandConstants.COMMAND_PREFIX + "channelList";
	}

	public String getDescription() {
		return "Lists channels";
	}

	public void stop() {
		// TODO Auto-generated method stub
		
	}

	public void run(String[] args, User user, MessageChannel channel) {
		String message = "\n";
		for(Guild guild : Constants.getApi().getGuilds()) {
			message += "**" + guild.getName() + "**" + " " + "[" + guild.getId() + "]" + "\n";
			message += "\t*Text Channels*\n";
			for(TextChannel ch : guild.getTextChannels()) {
				message += "\t\t" + ch.getName() + " " + "[" + ch.getId() + "]" + "\n";
			}
			message += "\t*Voice Channels*\n";
			for(VoiceChannel ch : guild.getVoiceChannels()) {
				message += "\t\t" + ch.getName() + " " + "[" + ch.getId() + "]" + "\n";
			}
		}

		channel.sendMessage(message);
		
		message = "\n";
		for(PrivateChannel ch : Constants.getApi().getPrivateChannels()) {
			message += "**" + ch.getUser().getUsername() + "**" + " " + "[" + ch.getUser().getPrivateChannel().getId() + "]" + "\n";
		}

		channel.sendMessage(message);
	}

}
