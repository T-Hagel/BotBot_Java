package me.lightfall.BotBot.audio;

import javax.sound.sampled.AudioInputStream;

import net.dv8tion.jda.audio.player.Player;

public class DummyPlayer extends Player {

	private boolean started = false;
	private boolean playing = false;
	
	
	public DummyPlayer(AudioInputStream stream) {
		super();
		setAudioSource(stream);
	}
	
	@Override
	public void play() {
		playing = true;
	}

	@Override
	public void pause() {
		playing = false;
	}

	@Override
	public void stop() {
		started = false;
	}

	@Override
	public void restart() {
		
	}

	@Override
	public boolean isStarted() {
		return started;
	}

	@Override
	public boolean isPlaying() {
		return playing;
	}

	@Override
	public boolean isPaused() {
		return !playing;
	}

	@Override
	public boolean isStopped() {
		return !started;
	}

}
