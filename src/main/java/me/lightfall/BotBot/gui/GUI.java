package me.lightfall.BotBot.gui;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import me.lightfall.BotBot.Constants;
import me.lightfall.BotBot.audio.AudioManager;
import me.lightfall.BotBot.utils.CleanGuild;
import me.lightfall.BotBot.utils.CleanVoiceChannel;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.VoiceChannel;

public abstract class GUI {
	private static MainWindow mainGui;
	public static VoiceChannel selectedVoiceChannel;
	public static String selectedSong;
	
	public static void initialize() {
		if(mainGui == null) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				mainGui = new MainWindow();
				mainGui.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// Music GUI

	}
	
	public static void loadVoiceChannels() {
		DefaultTreeModel model = getMainModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot(); 
		root.removeAllChildren();
		for(Guild guild : Constants.getApi().getGuilds()) {
			CleanGuild gu = new CleanGuild(guild);
			DefaultMutableTreeNode gNode = new DefaultMutableTreeNode(new CleanGuild(guild));
			
			for(VoiceChannel ch : guild.getVoiceChannels()) {
				
				gNode.add(new DefaultMutableTreeNode(new CleanVoiceChannel(ch)));
			}
			root.add(gNode);
		}
		model.reload();
	}
	
	public static void loadSongs() {
		DefaultTreeModel model = getMusicModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		root.removeAllChildren();
		List<String> songs = new Vector<String>();
		songs.addAll(AudioManager.getFiles());
		songs.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		for(String song : songs) {
			root.add(new DefaultMutableTreeNode(song));
		}
		model.reload();
	}
	
	public static DefaultTreeModel getMainModel() { return mainGui.getListModel(); }
	public static DefaultTreeModel getMusicModel() { return mainGui.getMusicModel(); }
	public static void setFooter(String string) { mainGui.setFooter(string); }
	public static void setProgrssBar(int value) { mainGui.setProgrssBar(value); }
	public static void joinSelected() {
		if(GUI.selectedVoiceChannel != null) {
			try {
				AudioManager.join(GUI.selectedVoiceChannel);
				setFooter("Joined " + selectedVoiceChannel.getName());
			} catch (Exception e) {
				setFooter("Unable to join " + selectedVoiceChannel.getName());
			}
		}
	}
	
	public static void playSong() { 
		if(GUI.selectedSong != null) {
			try {
				AudioManager.playOrResume(GUI.selectedSong);
				setFooter("Playing " + GUI.selectedSong);
			} catch (InvalidParameterException | IOException | UnsupportedAudioFileException err) {
				setFooter("Unable to play song! " + err.getMessage());
			}
		}
	}
	
	
}
