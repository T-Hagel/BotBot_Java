package me.lightfall.BotBot.audio;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.UnsupportedAudioFileException;

import me.lightfall.BotBot.Constants;
import net.dv8tion.jda.audio.player.FilePlayer;
import net.dv8tion.jda.audio.player.Player;
import net.dv8tion.jda.entities.VoiceChannel;

public class AudioManager {

	private static Map<String, String> files = new HashMap<String, String>();
	private static BetterPlayer currentPlayer = null;
	private static List<BetterPlayer> playlist = new ArrayList<BetterPlayer>();
	private static Timer timer;
	
	private static final String[] fileExtensions = {".mp3", ".wav", ".m4a"};
		
	public static void initiate() {
		findFiles();
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				if(currentPlayer != null)
					if(currentPlayer.isStopped()) {
						next();
					}
			}
			
		}, 0, 1000 * 1);	// Check every 1 second
	}
	
	public static int queuePlaylist(String name) throws IOException, UnsupportedAudioFileException {
		BetterPlayer next = getPlayerForSong(name);
		if(next == null)
			return -1;
		else
		{
			if(currentPlayer == null) {
				play(next);
				return 0;
			} else {
				return playlist.add(next) ? playlist.size() : -1 ;
			}
		}
	}
	
	public static void findFiles() {
		files.clear();
		try {
			loadFromDirectory(new File("D:\\Music"));
		//	loadFromDirectory(new File("D:\\Program Files (x86)\\Riot Games\\League of Legends"));
		//	for(String name : files.keySet())
		//		System.out.println(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void loadFromDirectory(File dir) throws IOException {	
		for(String name : dir.list()) {
			File file = new File(dir.getCanonicalPath() + "\\" + name);
			if(file.isDirectory())
				loadFromDirectory(file);
			else if(file.isFile())
				for(String extension : fileExtensions)
					if(name.endsWith(extension)) {
						
						files.put(name.toLowerCase(), file.getAbsolutePath());
						break;
					}
		}
	}
	
	public static void play(String name) throws IOException, UnsupportedAudioFileException, InvalidParameterException {
		if(Constants.getApi().getAudioManager().isConnected()) {
			if(files.containsKey(name)) {
				play(getPlayerForSong(name));
			} else {
				throw new InvalidParameterException();
			}
		} else {
			// Throw something?
		}
	}
	
	public static void play(BetterPlayer player) {
		if(Constants.getApi().getAudioManager().isConnected()) {
			currentPlayer = player;
			Constants.getApi().getAudioManager().setSendingHandler(currentPlayer);
	
			currentPlayer.play();
		}
	}
	
	public static void playOrResume(String name) throws InvalidParameterException, IOException, UnsupportedAudioFileException {
		if(currentPlayer != null) {
			if(currentPlayer.getFileName().equalsIgnoreCase(name)) {
				play();
			} else {
				play(name);
			}
		} else {
			play(name);
		}
	}
	
	private static BetterPlayer getPlayerForSong(String name) throws IOException, UnsupportedAudioFileException {
		name = name.toLowerCase();
		if(files.containsKey(name)) {
			return new BetterPlayer(new File(files.get(name)));
		}
		return null;
	}
	
	public static void join(VoiceChannel channel) throws IllegalStateException, java.lang.UnsupportedOperationException{
		net.dv8tion.jda.managers.AudioManager manager = Constants.getApi().getAudioManager();
		
		if(manager.isConnected()) {
			if(manager.getConnectedChannel().getGuild().getId().equals(channel.getGuild().getId())) {
				// Simply move the connection
				manager.moveAudioConnection(channel);
				return;
			}
			leave();
		}
		
		manager.openAudioConnection(channel);
	}
	
	public static void leave() {
		net.dv8tion.jda.managers.AudioManager manager = Constants.getApi().getAudioManager();
		if(manager.isConnected()) {
			manager.closeAudioConnection();
			System.out.println("Closed audio connection");
		}
	}
	
	public static void next() {
		if(playlist.size() > 0) {
			currentPlayer = playlist.remove(0);
			Constants.getApi().getAudioManager().setSendingHandler(currentPlayer);
			currentPlayer.play();
		}
	}
	
	public static void play() {
		if(currentPlayer != null)
			currentPlayer.play();
	}
	
	public static void volume(float fl) {
		if(currentPlayer != null)
			currentPlayer.setVolume(fl);
	}
	
	public static void pause() {
		if(currentPlayer != null)
			currentPlayer.pause();
	}
	
	public static void stop() {
		if(currentPlayer != null)
			currentPlayer.stop();
	}
	
	public static void restart() {
		if(currentPlayer != null)
			currentPlayer.restart();
	}
	
	public static Set<String> getFiles() {
		return files.keySet();
	}
	
	private static class BetterPlayer extends FilePlayer {
		private File file;

		public BetterPlayer(File file) throws IOException, UnsupportedAudioFileException {
			super(file);
			this.file = file;
		}
		
		public String getFileName() {
			return file.getName().toLowerCase();
		}
		
	}
}
