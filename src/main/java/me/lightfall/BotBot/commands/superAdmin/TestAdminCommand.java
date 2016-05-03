package me.lightfall.BotBot.commands.superAdmin;

import me.lightfall.BotBot.Constants;
import me.lightfall.BotBot.commands.Command;
import me.lightfall.BotBot.commands.CommandConstants;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.User;

public class TestAdminCommand implements Command {

	public String[] getAliases() {
		return new String[] {"test", "t"};
	}

	public String getUsage() {
		return CommandConstants.COMMAND_PREFIX + "test";
	}

	public String getDescription() {
		return "test admin command";
	}

	public void stop() {
		// TODO Auto-generated method stub
		
	}

	public void run(String[] args, User user, MessageChannel channel) {
		channel.sendMessage("Test command ran");
	}

}
