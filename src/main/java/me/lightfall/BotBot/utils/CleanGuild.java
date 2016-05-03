package me.lightfall.BotBot.utils;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Vector;

import net.dv8tion.jda.JDA;
import net.dv8tion.jda.OnlineStatus;
import net.dv8tion.jda.Region;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.Role;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.entities.VoiceChannel;
import net.dv8tion.jda.entities.VoiceStatus;
import net.dv8tion.jda.managers.ChannelManager;
import net.dv8tion.jda.managers.GuildManager;
import net.dv8tion.jda.managers.RoleManager;
import net.dv8tion.jda.utils.InviteUtil.AdvancedInvite;

public class CleanGuild implements Guild {
	
	private Guild guild;
	
	public CleanGuild(Guild guild) {
		this.guild = guild;
	}

	public List<User> getOnlineUsers() {
		List<User> list = getUsers();
		List<User> newList = new Vector<User>();
		for(User user : list) {
			if(user.getOnlineStatus() != OnlineStatus.OFFLINE)
				newList.add(user);
		}
		return newList;
	}
	
	@Override
	public String toString() {
		return getName();
	}

	@Override
	public String getId() {
		return guild.getId();
	}

	@Override
	public String getName() {
		return guild.getName();
	}

	@Override
	public String getIconId() {
		return guild.getIconId();
	}

	@Override
	public String getIconUrl() {
		return guild.getIconUrl();
	}

	@Override
	public String getAfkChannelId() {
		return guild.getAfkChannelId();
	}

	@Override
	public String getOwnerId() {
		return guild.getOwnerId();
	}

	@Override
	public int getAfkTimeout() {
		return guild.getAfkTimeout();
	}

	@Override
	public Region getRegion() {
		return guild.getRegion();
	}

	@Override
	public List<User> getUsers() {
		return guild.getUsers();
	}

	@Override
	public List<TextChannel> getTextChannels() {
		return guild.getTextChannels();
	}

	@Override
	public ChannelManager createTextChannel(String name) {
		return guild.createTextChannel(name);
	}

	@Override
	public List<VoiceChannel> getVoiceChannels() {
		return guild.getVoiceChannels();
	}

	@Override
	public ChannelManager createVoiceChannel(String name) {
		return guild.createVoiceChannel(name);
	}

	@Override
	public List<Role> getRoles() {
		return guild.getRoles();
	}

	@Override
	public RoleManager createRole() {
		return guild.createRole();
	}

	@Override
	public List<Role> getRolesForUser(User user) {
		return guild.getRolesForUser(user);
	}

	@Override
	public Role getPublicRole() {
		return guild.getPublicRole();
	}

	@Override
	public TextChannel getPublicChannel() {
		return guild.getPublicChannel();
	}

	@Override
	public OffsetDateTime getJoinDateForUser(User user) {
		return guild.getJoinDateForUser(user);
	}

	@Override
	public GuildManager getManager() {
		return guild.getManager();
	}

	@Override
	public JDA getJDA() {
		return guild.getJDA();
	}

	@Override
	public VoiceStatus getVoiceStatusOfUser(User user) {
		return guild.getVoiceStatusOfUser(user);
	}

	@Override
	public List<VoiceStatus> getVoiceStatuses() {
		return guild.getVoiceStatuses();
	}

	@Override
	public boolean isAvailable() {
		return guild.isAvailable();
	}

	@Override
	public List<AdvancedInvite> getInvites() {
		return guild.getInvites();
	}

}
