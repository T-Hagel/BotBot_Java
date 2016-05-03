package me.lightfall.BotBot.commands;

import java.util.HashMap;
import java.util.Map;

import me.lightfall.BotBot.commands.admin.AutoTopicChangerCommand;
import me.lightfall.BotBot.commands.admin.ChannelManagerCommand;
import me.lightfall.BotBot.commands.audio.JoinCommand;
import me.lightfall.BotBot.commands.audio.NextCommand;
import me.lightfall.BotBot.commands.audio.PauseCommand;
import me.lightfall.BotBot.commands.audio.PlayCommand;
import me.lightfall.BotBot.commands.audio.QueueCommand;
import me.lightfall.BotBot.commands.audio.RestartCommand;
import me.lightfall.BotBot.commands.audio.SongsCommand;
import me.lightfall.BotBot.commands.audio.StopCommand;
import me.lightfall.BotBot.commands.audio.VolumeCommand;
import me.lightfall.BotBot.commands.superAdmin.ChannelListCommand;
import me.lightfall.BotBot.commands.superAdmin.ListenerManagerCommand;
import me.lightfall.BotBot.commands.superAdmin.GameCommand;
import me.lightfall.BotBot.commands.superAdmin.SendCommand;
import me.lightfall.BotBot.commands.superAdmin.TestAdminCommand;

@SuppressWarnings("unchecked")
public abstract class CommandConstants {
	
	public static final String COMMAND_PREFIX = "/";

	public static Map<String, Command> getBasicCommands() {
		Class<? extends Command>[] list = new Class[] {
				EchoCommand.class,
				WeatherCommand.class,
				NewsCommand.class,
				QueueCommand.class,
				};
		
		return instantiateCommands(list);
	}
	
	public static Map<String, Command> getAdminCommands() {
		Class<? extends Command>[] list = new Class[] {
				ChannelManagerCommand.class,
				AutoTopicChangerCommand.class
				};
		
		return instantiateCommands(list);
	}
	
	
	public static Map<String, Command> getSuperAdminCommands() {
		Class<? extends Command>[] list = new Class[] {
				TestAdminCommand.class,
				ListenerManagerCommand.class,
				ChannelListCommand.class,
				SendCommand.class,
				GameCommand.class,
				PlayCommand.class,
				PauseCommand.class,
				StopCommand.class,
				RestartCommand.class,
				VolumeCommand.class,
				SongsCommand.class,
				QueueCommand.class,
				NextCommand.class,
				JoinCommand.class
				};
		
		return instantiateCommands(list);
	}
	
	
	private static Map<String, Command> instantiateCommands(Class<? extends Command>[] list) {
		HashMap<String, Command> map = new HashMap<String, Command>();
		
		for(Class<? extends Command> clazz : list) {
			try {
				Command cmd = clazz.newInstance();
				for(String alias : cmd.getAliases()) {
					map.put(alias, cmd);
				}
			} catch (Exception e) {
				// Do nothing, move one
			}
		}
		
		return map;
	}
}
