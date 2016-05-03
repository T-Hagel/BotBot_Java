package me.lightfall.BotBot.commands.superAdmin;

import me.lightfall.BotBot.Constants;
import me.lightfall.BotBot.commands.Command;
import me.lightfall.BotBot.commands.CommandConstants;
import net.dv8tion.jda.entities.Channel;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.PrivateChannel;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.entities.User;

public class SendCommand implements Command {

	public String[] getAliases() {
		return new String[] {"send", "s"};
	}

	public String getUsage() {
		return CommandConstants.COMMAND_PREFIX + "send <channel id> <message>";
	}

	public String getDescription() {
		return "sends channel id the given message";
	}

	public void stop() {
		// TODO Auto-generated method stub
		
	}

	public void run(String[] args, User user, MessageChannel channel) {
		String message = "";
		if(args.length >= 2) {
			if(args[1].equalsIgnoreCase("save")) {
				
			} else {
				String ch = args[0];
				MessageChannel tCh = Constants.getApi().getTextChannelById(ch);
				MessageChannel pCh = Constants.getApi().getPrivateChannelById(ch);
				if(tCh != null || pCh != null) {
					MessageChannel sendChannel = tCh == null ? pCh : tCh;
					for(int i = 1; i < args.length; i++) {
						message += args[i] + " ";
					}
					message = message.substring(0, message.length() - 1);
					sendChannel.sendMessage(message);
					message = "[" + ((Channel) sendChannel).getName() + "] " + message;
				} else {
					message = "Invalid channel id";
				}
			}
		} else {
			message = "Not enough arguments";
		}
		channel.sendMessage(message);
	}
}
