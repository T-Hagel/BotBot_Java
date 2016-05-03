package me.lightfall.BotBot.commandListeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.lightfall.BotBot.commands.Command;
import me.lightfall.BotBot.commands.CommandConstants;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.hooks.ListenerAdapter;

public abstract class AbstractListener extends ListenerAdapter implements Command {
	protected Map<String, Command> commands = new HashMap<String, Command>();
	protected List<Command> commandList = new ArrayList<Command>();
	
	protected AbstractListener() {
		registerCommand(this);
	}
	
	public String[] getAliases() {
		return new String[] {"help", "?"};
	}
	
	public void endListening() {
		commands.clear();
		for(Command cmd : commandList) {
			cmd.stop();
		}
		commandList.clear();
	}
	
	public void stop() {
		// 
	}

	public void run(String[] args, User user, MessageChannel channel) {
		if(args.length == 0) {
			String message = "\n";
			for(Command cmd : commandList) {
				// Usage
				message += "**" + cmd.getUsage() + "**";
				message +=  "\t-\t";
				
				// Description
				message += cmd.getDescription();
				
				// Aliases
				String aliases = "";
				for(String alias : cmd.getAliases()) {
					aliases += alias + ", ";
				}
				aliases = aliases.substring(0, aliases.length() - 2);
				message += "\n\tAliases: " + aliases;
				
				message += '\n';
			}
			
			channel.sendMessage(message);
		}
	}

	public String getUsage() {
		return CommandConstants.COMMAND_PREFIX + "help [command]";
	}

	public String getDescription() {
		return "Lists the commands available or info on the requested command";
	}
	
	public void registerCommand(Map<String, Command> map) {
		commands.putAll(map);
		
		for(Command cmd : map.values()) {
			if(!commandList.contains(cmd))
				commandList.add(cmd);
		}
	}
	
	public void registerCommand(Command cmd) {
		for(String alias : cmd.getAliases())
			commands.put(alias, cmd);
		
		if(!commandList.contains(cmd))
			commandList.add(cmd);
	}
}
