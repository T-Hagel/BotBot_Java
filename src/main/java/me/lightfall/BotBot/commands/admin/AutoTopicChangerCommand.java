package me.lightfall.BotBot.commands.admin;

import me.lightfall.BotBot.Constants;
import me.lightfall.BotBot.commands.Command;
import me.lightfall.BotBot.commands.CommandConstants;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.exceptions.PermissionException;

public class AutoTopicChangerCommand implements Command {

	public String[] getAliases() {
		return new String[] {"topic", "t"};
	}

	public String getUsage() {
		return CommandConstants.COMMAND_PREFIX + "topic <channel id> [type] <message>";
	}

	public String getDescription() {
		return "Changes the topic for the given channel.";
	}

	public void stop() {
		// TODO Auto-generated method stub
		
	}

	public void run(String[] args, User user, MessageChannel channel) {
		String message = "\n";
		if(args.length >= 1) {
			String id = args[0];
			TextChannel tCh = Constants.getApi().getTextChannelById(id); 
			if(tCh == null) {
				channel.sendMessage("Unknown channel id!");
				return;
			}
			
			String topic = "";
			for(int i = 1; i < args.length; i++) {
				topic += args[i] + " ";
			}
			if(topic.length() > 0)
				topic = topic.substring(0, topic.length() - 1);
			try {
				tCh.getManager().setTopic(topic).update();
				message = "Topic changed to: " + topic;
			} catch(PermissionException e) {
				message = "Bot Bot does not have permission to change the topic!";
			}
		} else {
			message = "Invalid number of arguments!";
		}
		
		
		channel.sendMessage(message);
	}

}
