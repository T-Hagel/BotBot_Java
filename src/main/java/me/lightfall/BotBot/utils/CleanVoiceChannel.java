package me.lightfall.BotBot.utils;

import java.util.List;

import net.dv8tion.jda.JDA;
import net.dv8tion.jda.Permission;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.PermissionOverride;
import net.dv8tion.jda.entities.Role;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.entities.VoiceChannel;
import net.dv8tion.jda.entities.impl.VoiceChannelImpl;
import net.dv8tion.jda.managers.ChannelManager;
import net.dv8tion.jda.managers.PermissionOverrideManager;
import net.dv8tion.jda.utils.InviteUtil.AdvancedInvite;

public class CleanVoiceChannel implements VoiceChannel {
	
	private VoiceChannel channel;
	
	public CleanVoiceChannel(VoiceChannel channel) {
		this.channel = channel;
	}
	
	@Override
	public String toString() {
		return channel.getName();
	}

	@Override
	public String getId() {
		return channel.getId();
	}

	@Override
	public String getName() {
		return channel.getName();
	}

	@Override
	public String getTopic() {
		return channel.getTopic();
	}

	@Override
	public Guild getGuild() {
		return channel.getGuild();
	}

	@Override
	public List<User> getUsers() {
		return channel.getUsers();
	}

	@Override
	public int getPosition() {
		return channel.getPosition();
	}

	@Override
	public boolean checkPermission(User user, Permission permission) {
		return channel.checkPermission(user, permission);
	}

	@Override
	public ChannelManager getManager() {
		return channel.getManager();
	}

	@Override
	public JDA getJDA() {
		return channel.getJDA();
	}

	@Override
	public PermissionOverride getOverrideForUser(User user) {
		return channel.getOverrideForUser(user);
	}

	@Override
	public PermissionOverride getOverrideForRole(Role role) {
		return channel.getOverrideForRole(role);
	}

	@Override
	public List<PermissionOverride> getPermissionOverrides() {
		return channel.getPermissionOverrides();
	}

	@Override
	public List<PermissionOverride> getUserPermissionOverrides() {
		return channel.getUserPermissionOverrides();
	}

	@Override
	public List<PermissionOverride> getRolePermissionOverrides() {
		return channel.getRolePermissionOverrides();
	}

	@Override
	public PermissionOverrideManager createPermissionOverride(User user) {
		return channel.createPermissionOverride(user);
	}

	@Override
	public PermissionOverrideManager createPermissionOverride(Role role) {
		return channel.createPermissionOverride(role);
	}

	@Override
	public List<AdvancedInvite> getInvites() {
		return channel.getInvites();
	}
	
	
}
