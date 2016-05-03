package me.lightfall.BotBot.commands.superAdmin;

import me.lightfall.BotBot.Constants;
import me.lightfall.BotBot.commands.Command;
import me.lightfall.BotBot.commands.CommandConstants;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.User;

public class GameCommand implements Command {

	public String[] getAliases() {
		return new String[] {"game"};
	}

	public String getUsage() {
		return CommandConstants.COMMAND_PREFIX + "game <game>";
	}

	public String getDescription() {
		return "Sets Bot Bot's game";
	}

	public void stop() {
		// TODO Auto-generated method stub
		
	}

	public void run(String[] args, User user, MessageChannel channel) {
		String message = "\n";
		String game = "";
		for(int i = 0; i < args.length; i++) {
			game += args[i] + " ";
		}
		game = game.trim();
		Constants.getApi().getAccountManager().setGame(game);
		
		message += "Game set to: " + game;
		channel.sendMessage(message);
	}

}
