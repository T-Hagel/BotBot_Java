package me.lightfall.BotBot.commands.superAdmin;

import me.lightfall.BotBot.Constants;
import me.lightfall.BotBot.commandListeners.CommandListener;
import me.lightfall.BotBot.commands.Command;
import me.lightfall.BotBot.commands.CommandConstants;
import me.lightfall.BotBot.exceptions.InvalidTextChannelIdException;
import me.lightfall.BotBot.exceptions.InvalidUserIdException;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.PrivateChannel;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.entities.User;

public class ListenerManagerCommand implements Command {

	public String[] getAliases() {
		return new String[] {"listeners", "l"};
	}

	public String getUsage() {
		return CommandConstants.COMMAND_PREFIX + "listeners [type={basic,admin,super}|register|deregister] <type={basic,admin,super}> <id>";
	}

	public String getDescription() {
		return "Manages command listeners";
	}

	public void stop() {
		// TODO Auto-generated method stub
		
	}

	public void run(String[] args, User user, MessageChannel channel) {
		if(args.length == 0) {
			// List all listeners
			String message = getSuperListeners();
			
			message += getBasicListeners();
			
			channel.sendMessage(message);
		} else if(args.length == 1) {
			// List a specific listener
			String message = "";
			switch(args[0].toLowerCase()) {
				case "basic":
					message = getBasicListeners();
				break;
				case "admin":
					message = getAdminListeners();
				break;
				case "super":
					message = getSuperListeners();
				break;
				default:
					message = "Unknown listener type";
				break;
			}
			
			channel.sendMessage(message);
		} else if(args.length >= 3) {
			// Register/deregister a listener
			String message = "Error";
			switch(args[0].toLowerCase()) {
				case "register":
					switch(args[1].toLowerCase()) {
						case "basic":
							try {
								Constants.addBasicListener(args[2]);
								message = "Successfully added basic listener to channel (id=" + args[2] + ")";
							} catch (InvalidTextChannelIdException e) {
								message = "Unable to add basic listener to channel (id=" + args[2] + ")";
							}
						break;
						case "admin":
							message = "Unable to add admin listener to channel (id=" + args[2] + ")";
						break;
						case "super":
							try {
								Constants.addSuperAdminListener(args[2]);
								message = "Successfully added super listener to channel (id=" + args[2] + ")";
							} catch (InvalidUserIdException e) {
								message = "Unable to add super listener to channel (id=" + args[2] + ")";
							}
						break;
						default:
							message = "Unknown listener type";
						break;
					}
				break;
				case "deregister":
					switch(args[1].toLowerCase()) {
						case "basic":
							if(Constants.removeBasicListener(args[2])) {
								message = "Successfully removed basic listener from channel (id=" + args[2] + ")";
							} else {
								message = "Unable to removed basic listener from channel (id=" + args[2] + ")";
							}
						break;
						case "admin":
							
						break;
						case "super":
							if(Constants.removeSuperAdminListener(args[2])) {
								message = "Successfully removed super listener from channel (id=" + args[2] + ")";
							} else {
								message = "Unable to removed super listener from channel (id=" + args[2] + ")";
							}
						break;
						default:
							message = "Unknown listener type";
						break;
				}
				break;
				default:
					message = "Unknown arguments";
				break;
			}
			channel.sendMessage(message);
		} else {
			channel.sendMessage("Unknown arguments");
		}
	}
	
	
	private static String getBasicListeners() {
		String message = "\n**Basic Listeners**";
		for(CommandListener lis : Constants.basicListeners.values()) {
			message += "\n\t" + Constants.getApi().getTextChannelById(lis.getId()).getGuild().getName()
					+ " " + "[" + Constants.getApi().getTextChannelById(lis.getId()).getName() + "]";
		}
		return message;
	}

	private static String getAdminListeners() {
		String message = "\n**Admin listners:**";
//		for(AdminListener lis : Constants.adminListeners.values()) {
//			message += "\n\t" + Constants.getApi().getUserById(lis.getId()).getUsername();
//		}
		return message;
	}
	
	private static String getSuperListeners() {
		String message = "\n**Super Admin listners:**";
		for(CommandListener lis : Constants.superAdminListeners.values()) {
			message += "\n\t" + Constants.getApi().getPrivateChannelById(lis.getId()).getUser().getUsername();
		}
		return message;
	}
}
