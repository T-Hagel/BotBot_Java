package me.lightfall.BotBot.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Weather {

	private static String URL = "http://en.ff14angler.com/skywatcher.php";
	
	private static Weather instance;
	public static void initWeather() {
		if(instance == null) {
			instance = new Weather();
		}
	}
	
	private Timer timer = new Timer();
	
	private Weather() {
		updateWeather();
		long timeTillUpdate = getTimeTillNextUpdate();
		System.out.println("Updating weather in " + timeTillUpdate/1000 + " seconds.");
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						updateWeather();
						System.out.println("Weather has updated.");
					}
				}, 0, ((8 * 60 * 35 * 1000) / 12) + 1);	// Run every 8 Eorzea hours
			}
		}, timeTillUpdate);
	}
	
	
	public enum WEATHER {
		CLEARSKIES("Clear skies", 1),
		FAIRSKIES("Fair Skies", 2),
		CLOUDS("Clouds", 3),
		FOG("Fog", 4),
		WIND("Wind", 5),
		GALES("Gales", 6),
		RAIN("Raid", 7),
		SHOWERS("Showers", 8),
		THUNDER("Thunder", 9),
		THUNDERSTORMS("Thunderstorms", 10),
		DUSTSTORMS("Dust Storms", 11),
		HEATWAVES("Heat waves", 14),
		SNOW("Snow", 15),
		BLIZZARDS("Blizzards", 16),
		GLOOM("Gloom", 17),
		UMBRALWIND("Umbral Wind", 49),
		UMBRALSTATIC("Umbral Static", 50);
		
		private String name;
		private int id;
		
		WEATHER(String name, int id) {
			this.name = name;
			this.id = id;
		}
		
		public String getName() { return name; }
		public int getId() { return id; }
		
		public static WEATHER findById(int id) {
			for(WEATHER we : WEATHER.values())
			{
				if(we.getId() == id)
					return we;
			}
			return null;
		}
		
	}
	
	public enum AREAS {
		LIMSA_LOMINSA("Limsa Lominsa", 1, null, null, null, null),
		MIDDLE_LA_NOSCEA("Middle La Noscea", 2, null, null, null, null),
		LOWER_LA_NOSCEA("Lower La Noscea", 3, null, null, null, null),
		EASTERN_LA_NOSCEA("Eastern La Noscea", 4, null, null, null, null),
		WESTERN_LA_NOSCEA("Wastern La Noscea", 5, null, null, null, null),
		UPPER_LA_NOSCEA("Upper La Noscea", 6, null, null, null, null),
		OUTER_LA_NOSCEA("Outer La Noscea", 7, null, null, null, null),
		WOLVES_DEN_PIER("Wolves' Den Pier", 8, null, null, null, null),
		MIST("Mist", 9, null, null, null, null),
		ULDAH("Ul'dah", 10, null, null, null, null),
		CENTRAL_SHROUD("Central Shroud", 11, null, null, null, null),
		EAST_SHROUD("East Shroud", 12, null, null, null, null),
		SOUTH_SHROUD("South Shroud", 13, null, null, null, null),
		NORTH_SHROUD("North Shroud", 14, null, null, null, null),
		LAVENDER_BEDS("Lavender Beds", 15, null, null, null, null),
		GRIDANIA("Gridania", 16, null, null, null, null),
		WESTERN_THANALAN("Western Thanalan", 17, null, null, null, null),
		CENTRAL_THANALAN("Central Thanalan", 18, null, null, null, null),
		EASTERN_THANALAN("Eastern Thanalan", 19, null, null, null, null),
		SOUTHERN_THANALAN("Southern Thanalan", 20, null, null, null, null),
		NORTHERN_THANALAN("Nothern Thanalan", 21, null, null, null, null),
		GOBLET("Goblet", 22, null, null, null, null),
		ISHGARD("Ishgard", 25, null, null, null, null),
		COERTHAS_CENTRAL_HIGHLANDS("Coerthas Central Highlands", 23, null, null, null, null),
		COERTHAS_WESTERN_HIGHLANDS("Coerthas Western Highlands", 26, null, null, null, null),
		SEA_OF_CLOUDS("Sea of Clouds", 27, null, null, null, null),
		AZYS_LA("Azys La", 28, null, null, null, null),
		IDYLSHIRE("Idylshire", 29, null, null, null, null),
		DRAVANIAN_FORELANDS("Dravanian Forelands", 30, null, null, null, null),
		DRAVANIAN_HINTERLANDS("Dravanian Hinterlands", 31, null, null, null, null),
		CHURNING_MISTS("Churning Mists", 32, null, null, null, null),
		MOR_DHONA("Mor Dhona", 24, null, null, null, null);
		
		private String name;
		private int id;
		private WEATHER past;
		private WEATHER current; 
		private WEATHER next;
		private WEATHER next2;
		
		AREAS(String name, int id, WEATHER past, WEATHER current, WEATHER next, WEATHER next2) {
			this.name = name;
			this.id = id;
			this.past = past;
			this.current = current;
			this.next = next;
			this.next2 = next2;
		}
		
		public static AREAS findById(int id) {
			for(AREAS ar : AREAS.values())
			{
				if(ar.getId() == id)
					return ar;
			}
			return null;
		}

		public String getName() {
			return name;
		}

		public int getId() {
			return id;
		}

		public WEATHER getPast() {
			return past;
		}

		public WEATHER getCurrent() {
			return current;
		}

		public WEATHER getNext() {
			return next;
		}

		public WEATHER getNext2() {
			return next2;
		}

		private void setCurrent(WEATHER current) {
			this.current = current;
		}

		private void setPast(WEATHER past) {
			this.past = past;
		}

		private void setNext(WEATHER next) {
			this.next = next;
		}

		private void setNext2(WEATHER next2) {
			this.next2 = next2;
		}
	}
	
	
	private static HttpURLConnection getContent() {
		try {
			URL url = new URL(URL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			return con;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static JSONObject getWeatherJSON() {
		HttpURLConnection content = getContent();
		if(content == null)
			return null;
		try {
			Reader br = new BufferedReader(new InputStreamReader(content.getInputStream()));
			return new JSONObject(new JSONTokener(br));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @return The time (in milliseconds) until the next
	 * weather update
	 */
	public static long getTimeTillNextUpdate() {
			JSONObject o = getWeatherJSON();
			if(o == null)
				return 0;
			
			int EHour = Integer.parseInt(o.getString("left_hour"));
			int EMin = Integer.parseInt(o.getString("left_minute"));

			long ETotalMins = EHour * 60 + EMin;
			return (ETotalMins * 35 * 1000) / 12; 
	}
	
	private static void clearWeather() {
		for(AREAS area : AREAS.values()) {
			area.setPast(null);
			area.setCurrent(null);
			area.setNext(null);
			area.setNext2(null);
		}
	}
	
	public static void updateWeather(){
		clearWeather();
		JSONObject obj = getWeatherJSON();
		JSONArray arr = obj.getJSONArray("data");
		for(int i = 0; i < arr.length(); i++)
		{
			int weather = Integer.parseInt(arr.getJSONObject(i).getString("weather"));
			WEATHER weatherEnum = WEATHER.findById(weather);
			if(weatherEnum != null) {
				int area = Integer.parseInt(arr.getJSONObject(i).getString("area"));
				AREAS areaEnum = AREAS.findById(area);
				if(areaEnum == null)
					continue;
				switch(arr.getJSONObject(i).getInt("time")) {
				case -1:
					areaEnum.setPast(weatherEnum);
					break;
				case 0:
					areaEnum.setCurrent(weatherEnum);
					break;
					
				case 1:
					areaEnum.setNext(weatherEnum);
					break;
					
				case 2:
					areaEnum.setNext2(weatherEnum);
					break;
				}
			} else {
				System.err.println("[WEATHER] Unknown weather: " + arr.getJSONObject(i).getString("html") + " " + arr.getJSONObject(i).getString("weather"));
			}

		}

	}
}
