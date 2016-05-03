package me.lightfall.BotBot;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import me.lightfall.BotBot.audio.AudioManager;
import me.lightfall.BotBot.exceptions.InvalidUserIdException;
import me.lightfall.BotBot.gui.GUI;

public class Main {

	public static int count = 0;
	
	public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		int numTasks = 5;
		int task = 0;
		GUI.initialize();
		GUI.setProgrssBar(++task * (100 / numTasks));
		GUI.setFooter("Loading API...");
		Constants.initiateAPI();
		GUI.setProgrssBar(++task * (100 / numTasks));
		GUI.setFooter("Starting AudioManager...");
		AudioManager.initiate();
		GUI.setProgrssBar(++task * (100 / numTasks));
		GUI.setFooter("Loading voice channels...");
		GUI.loadVoiceChannels();
		GUI.setProgrssBar(++task * (100 / numTasks));
		GUI.setFooter("Loading songs...");
		GUI.loadSongs();
		GUI.setFooter("Done!");
		GUI.setProgrssBar(0);
		
		final String Lightfall = "97889142372831232";

		
		try {
			Constants.addSuperAdminListener(Constants.getApi().getUserById(Lightfall).getPrivateChannel());
		} catch (InvalidUserIdException e) {
			System.err.println("Unable to register SuperAdminListener for Lightfall (InvalidUserId)");
		}
	}


}
