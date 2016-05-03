package me.lightfall.BotBot.utils;

public class NewsStory {

	private String link;
	private String title;
	
	public NewsStory(String link, String title) {
		this.link = link;
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public String getTitle() {
		return title;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
