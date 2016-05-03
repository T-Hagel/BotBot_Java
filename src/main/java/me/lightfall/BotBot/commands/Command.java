package me.lightfall.BotBot.commands;

import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.PrivateChannel;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.entities.User;

public interface Command {

	public String[] getAliases();
	public String getUsage();
	public String getDescription();
	
	public void stop();
	public void run(String[] args, User user, MessageChannel channel);
	
}
