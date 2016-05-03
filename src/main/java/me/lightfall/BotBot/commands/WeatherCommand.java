package me.lightfall.BotBot.commands;

import me.lightfall.BotBot.utils.Weather;
import me.lightfall.BotBot.utils.Weather.AREAS;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.User;

public class WeatherCommand implements Command {

	public WeatherCommand() {
		Weather.initWeather();
	}
	
	@Override
	public String[] getAliases() {
		return new String[] {"weather", "w"};
	}


	@Override
	public String getUsage() {
		return CommandConstants.COMMAND_PREFIX + "weather [location]";
	}


	@Override
	public String getDescription() {
		return "Provides the weather for the given location";
	}


	@Override
	public void stop() {
		// Do nothing
	}
	
	
	public void run(String[] args, User user, MessageChannel channel) {
		if(args.length == 0) {
			
			channel.sendMessage("You need more arguments!");
		} else {
			if(args[0].equalsIgnoreCase("all")) {
				int count = 0;
				String msg = "\n";
				for(AREAS area : AREAS.values()) {
					msg += createWeatherMessage(area) + '\n';
					count++;
					if(count >= 10) {
						count = 0;
						channel.sendMessage(msg);
						msg = "\n";
					}
				}
				channel.sendMessage(msg);
			} else {
				String arg = "";
				
				for(int i = 0; i < args.length; i++) {
					arg += args[i] + " ";
				}
				arg = arg.trim();

				AREAS area = null;
				try {
					// Assume it's an integer, try and get weather by ID
					int id = Integer.parseInt(arg);
					area = AREAS.findById(id);
				} catch(NumberFormatException e) {
					// Not an integer, search through words.
					area = searchAreasForName(arg);
				}

				if(area == null) {
					channel.sendMessage("Unknown area");
				} else { 
					channel.sendMessage('\n' + createWeatherMessage(area));
				}
			}
		}
	}
	
	
	public static String createWeatherMessage(AREAS area) {
		String[] values = new String[]{
				area.getPast() != null ? "\tPast: " + area.getPast().getName() + '\n' : "\tNo past data" + '\n',
				area.getCurrent() != null ? "\tCurrent: " + area.getCurrent().getName() + '\n' : "\tNo current data" + '\n',
				area.getNext() != null ? "\tNext: " + area.getNext().getName() + '\n' : "\tNo next data" + '\n',
				area.getNext2() != null ? "\tAfter: " + area.getNext2().getName() + '\n' : "\tNo after data" + '\n'};
		
		String msg = "**" + area.getName() + "**" + '\n'
				+ values[0]
				+ values[1]
				+ values[2]
				+ values[3];
		
		return msg;
		
	}
	
	private static AREAS searchAreasForName(String name) {
		AREAS area = null;
		switch(name.toLowerCase()) {
		// Limsa Lominsa
		case "limsa":
		case "limsa lominsa":
			area = AREAS.LIMSA_LOMINSA;
			break;
			
		// Middle La Noscea
		case "middle la noscea":
		case "mla":
		case "middle":
		case "middle la":
			area = AREAS.MIDDLE_LA_NOSCEA;
			break;
		
		// Lower La Noscea
		case "lower la noscea":
		case "lower la":
		case "lla":
		case "lower":
			area = AREAS.LOWER_LA_NOSCEA;
			break;
			
		// Eastern La Noscea
			
			
		// Western La Noscea
			
		// Upper La Noscea
			
			
		// Outer La Noscea
			
			
		// Wolves' Den Pier
			
			
		// Mist
			
			
		// Ul'Dah
			
			
		// Central Shroud
			
			
		// East Shroud
			
			
			
		// South Shroud
			
			
		// North Shroud
			
			
		// The Lavender Beds
			
			
		// Gridania
			
			
		// Western Thanalan
			
			
		// Central Thanalan
			
			
		// Eastern Thanalan
			
			
		// Southern Thanalan
			
			
		// Northern Thanalan
			
			
		// The Goblet
			
		// Ishgard
			
			
		// Coerthas Central Highlands
			
			
		// Coerthas Western Highlands
			
		// The Sea of Clouds
			
			
		// Azys Lla
			
			
		// Idyllshire
			
			
		// The Dravanian Forelands
			
			
		// The Dravanian Hinterlands
			
			
		// The Curninging Mists
			
			
		// Mor Dhona
			
			
			
			
			
			
		}
		
		
		
		return area;
	}
	
}
