package me.lightfall.BotBot.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import me.lightfall.BotBot.Constants;
import me.lightfall.BotBot.utils.NewsStory;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.PrivateChannel;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.entities.User;

public class NewsCommand implements Command {
	
	private static final String NEWS_URL = "http://na.finalfantasyxiv.com/lodestone/news/";
	private static final String BASE_URL = "http://na.finalfantasyxiv.com";

	public String[] getAliases() {
		return new String[] {"news"};
	}

	public void stop() {
		// Do nothing
	}
	

	public String getUsage() {
		return CommandConstants.COMMAND_PREFIX + "news [number of articles]";
	}

	public String getDescription() {
		return "Pulls the latest news articles from FFXIV lodestone";
	}

	public void run(String[] args, User user, MessageChannel channel) {
		
		List<NewsStory> stories = null;
		try {
			stories = getNewsStories();
		} catch (IOException e1) {
			channel.sendMessage("Unable to connect to the website! It may be down.");
			return;
		}
		
		
		int number = 3;
		
		if(args.length > 0) {
			try {
				number = Integer.parseInt(args[0]);
				if(number > Constants.MAX_NEWS_REQUESTS)
					number = Constants.MAX_NEWS_REQUESTS;
			} catch (Exception e) {
				// Do nothing
			}
		}
		
		
		
		String message = "\n";
		for(int i = 0; i < number; i++) {
			NewsStory story = stories.get(i);
			message += "**" + story.getTitle() + "**\t" + story.getLink() + "\n\n";
		}
		
		channel.sendMessage(message);
	}

	public static List<NewsStory> getNewsStories() throws IOException {
		List<NewsStory> stories = new ArrayList<NewsStory>();
		Document doc = Jsoup.connect(NEWS_URL).get();

		Elements topics = doc.getElementsByClass("topics_list").get(0).children();


		for(int i = 0; i < topics.size(); i++) {
			try {
				Element a = topics.get(i).child(0).child(0).getElementsByTag("a").get(0);

				String link = a.attr("href");
				String title = a.html();

				NewsStory story = new NewsStory(BASE_URL+link, title);
				stories.add(story);
			} catch (Exception e) {
				break;
			}
		}

		return stories;
	}
}
