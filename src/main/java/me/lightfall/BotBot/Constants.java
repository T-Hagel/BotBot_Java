package me.lightfall.BotBot;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.LoginException;

import me.lightfall.BotBot.commandListeners.CommandListener;
import me.lightfall.BotBot.commands.CommandConstants;
import me.lightfall.BotBot.exceptions.InvalidTextChannelIdException;
import me.lightfall.BotBot.exceptions.InvalidUserIdException;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.JDABuilder;
import net.dv8tion.jda.entities.PrivateChannel;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.entities.User;

public abstract class Constants {
	public static final int MAX_NEWS_REQUESTS = 5;
	
	private static JDA api;
	public static Map<String, CommandListener> superAdminListeners = new HashMap<String, CommandListener>();
	public static Map<String, CommandListener> basicListeners = new HashMap<String, CommandListener>();
	
	public static void initiateAPI() {
		if(api == null) {
			try {
				/*
				 * TODO Move this login to
				 * the GUI instead of console
				 * 
				 */

				Console console = System.console();
				if(console == null) {
					// Unable to get the System Console
					// Cannot acquire user/pass. Need to quit
					System.err.println("Unable to obtain the System Console. Quitting...");
					System.exit(0);
				}

				String user = console.readLine("Enter in the username: ");
				char[] passwordArray = console.readPassword("Enter in the password: ");
				String password = String.valueOf(passwordArray);
				
				
				api =  new JDABuilder(user,password).buildBlocking();				
			} catch (LoginException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void addSuperAdminListener(PrivateChannel channel) throws InvalidUserIdException { addSuperAdminListener(channel.getId()); }
	public static void addSuperAdminListener(User user) throws InvalidUserIdException { addSuperAdminListener(user.getPrivateChannel().getId()); }
	public static void addSuperAdminListener(String id) throws InvalidUserIdException {
		if(!superAdminListeners.containsKey(id)) {
			if(api.getPrivateChannels().contains(id))
				throw new InvalidUserIdException();
			
			CommandListener lis = new CommandListener(id);
			lis.registerCommand(CommandConstants.getSuperAdminCommands());
			lis.registerCommand(CommandConstants.getAdminCommands());
			lis.registerCommand(CommandConstants.getBasicCommands());
			superAdminListeners.put(id, lis);
			api.addEventListener(lis);
		}
	}
	
	/*
	 *	returns true if removed, false if not existent
	 * 
	 */
	public static void removeSuperAdminListener(PrivateChannel channel) { removeSuperAdminListener(channel.getId()); }
	public static void removeSuperAdminListener(User user) { removeSuperAdminListener(user.getId()); }
	public static boolean removeSuperAdminListener(String id) {
		if(superAdminListeners.containsKey(id)) {
			CommandListener lis = superAdminListeners.remove(id);
			lis.endListening();
			api.removeEventListener(lis);
			return true;
		}
		return false;
	}
	
	public static void addBasicListener(TextChannel channel) throws InvalidTextChannelIdException { addBasicListener(channel.getId()); }
	public static void addBasicListener(String id) throws InvalidTextChannelIdException {
		if(!basicListeners.containsKey(id)) {
			if(api.getTextChannelById(id) == null)
				throw new InvalidTextChannelIdException();
			CommandListener listener = new CommandListener(id);
			listener.registerCommand(CommandConstants.getBasicCommands());
			basicListeners.put(id, listener);
			api.addEventListener(listener);
		}

	}
	
	/*
	 *	returns true if removed, false if not existent
	 * 
	 */
	public static void removeBasicListener(TextChannel channel) { removeBasicListener(channel.getId()); }
	public static boolean removeBasicListener(String id) {
		if(basicListeners.containsKey(id)) {
			CommandListener lis = basicListeners.remove(id);
			lis.endListening();
			api.removeEventListener(lis);
			return true;
		}
		return false;
	}
	
	
	
	public static JDA getApi() { return api; }
}
