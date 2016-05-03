package me.lightfall.BotBot.commands.admin;

import me.lightfall.BotBot.commands.Command;
import me.lightfall.BotBot.commands.CommandConstants;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.User;

public class ChannelManagerCommand implements Command {

	public String[] getAliases() {
		return new String[] {"channel", "ch"};
	}

	public String getUsage() {
		return CommandConstants.COMMAND_PREFIX + "channel [update]";
	}

	public String getDescription() {
		return "Lists available channels for moderation";
	}

	public void stop() {
		// TODO Auto-generated method stub
		
	}

	public void run(String[] args, User user, MessageChannel channel) {
		String message = "\nAdmin command used";
//		for(Guild guild : Constants.getApi().getGuilds()) {
//			message += "**" + guild.getName() + "**" + " " + "[" + guild.getId() + "]" + "\n";
//			for(TextChannel ch : guild.getTextChannels()) {
//				message += "\t" + ch.getName() + " " + "[" + ch.getId() + "]" + "\n";
//			}
//		}

		channel.sendMessage(message);
		
//		message = "\n";
//		for(PrivateChannel ch : Constants.getApi().getPrivateChannels()) {
//			message += "**" + ch.getUser().getUsername() + "**" + " " + "[" + ch.getUser().getPrivateChannel().getId() + "]" + "\n";
//		}

//		channel.sendMessage(message);
	}

}
