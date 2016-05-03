package me.lightfall.BotBot.commandListeners;

import me.lightfall.BotBot.commands.CommandConstants;
import net.dv8tion.jda.entities.Channel;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.PrivateChannel;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

public class CommandListener extends AbstractListener {

	private String channelId;
	
	public CommandListener(String id) {
		super();
		this.channelId = id;
	}
	
	public CommandListener(MessageChannel channel) {
		this(((Channel) channel).getId());
	}
	
	public String getId() {
		return channelId;
	}
	
	
	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		MessageChannel channel = e.getChannel();
		String id = "";
		if(channel instanceof PrivateChannel)
			id = ((PrivateChannel) channel).getId();
		else
			id = ((Channel) channel).getId();
		if(!id.equals(channelId))
			return;
		
		String message = e.getMessage().getContent();
		if(message.startsWith(CommandConstants.COMMAND_PREFIX)) {
			// User entered command, get the values
			String input = e.getMessage().getContent().substring(CommandConstants.COMMAND_PREFIX.length());
			int splitPoint =  input.indexOf(" ") < 0 ? input.length() : input.indexOf(" ");
			String command = input.substring(0, splitPoint);
			String[] args = new String[0];
			String split = input.substring(splitPoint).trim();
			if(!split.equals(""))
				 args = split.split(" ");
			
			if(commands.containsKey(command)) {
				commands.get(command).run(args, e.getAuthor(), channel);
			} else {
				channel.sendMessage("Unknown command!");
			}
		}
		
	}

	
}
