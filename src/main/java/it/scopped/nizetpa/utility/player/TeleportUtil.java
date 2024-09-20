package it.scopped.nizetpa.utility.player;

import it.scopped.nizetpa.NizeTpa;
import it.scopped.nizetpa.utility.strings.ChatUtils;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.mutable.MutableInt;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@UtilityClass
public class TeleportUtil {
	private final Map<UUID, BukkitTask> teleports = new HashMap<>();
	private final NizeTpa plugin = NizeTpa.getInstance();

	public void teleportAfter(Player player, Location destination) {
		cancelExistingTeleport(player);
		if (destination == null) return;
		int seconds = 5;

		Location initialLocation = player.getLocation();
		double initialX = initialLocation.getX();
		double initialY = initialLocation.getY();
		double initialZ = initialLocation.getZ();

		MutableInt cooldown = new MutableInt(seconds);

		teleports.put(player.getUniqueId(), Bukkit.getScheduler().runTaskTimer(plugin, () -> {
			if (!player.isOnline() || hasPlayerMoved(player, initialX, initialY, initialZ)) {
				cancelTeleport(player);
				if (player.isOnline())
					handleTeleportFailure(player);
				return;
			}

			if (cooldown.intValue() == 0) {
				handleTeleportSuccess(player, destination);
				cancelTeleport(player);
				return;
			}

			handleTeleportProgress(player, cooldown.intValue());
			cooldown.decrement();
		}, 1L, 20L));
	}

	private void cancelExistingTeleport(Player player) {
		BukkitTask existingTask = teleports.remove(player.getUniqueId());
		if (existingTask != null)
			existingTask.cancel();
	}

	private boolean hasPlayerMoved(Player player, double initialX, double initialY, double initialZ) {
		Location playerLocation = player.getLocation();
		return (playerLocation.getX() != initialX || playerLocation.getY() != initialY || playerLocation.getZ() != initialZ);
	}

	private void handleTeleportFailure(Player player) {
		ChatUtils.send(player, plugin.getMessagesConfig().teleportMessageFailed);
		ChatUtils.sendActionBar(player, plugin.getMessagesConfig().teleportActionBarFailed);

		player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
	}

	private void handleTeleportSuccess(Player player, Location toLocation) {
		player.teleportAsync(toLocation);

		ChatUtils.send(player, plugin.getMessagesConfig().teleportMessageCompleted);
		ChatUtils.sendActionBar(player, plugin.getMessagesConfig().teleportActionBarCompleted);

		player.playSound(toLocation, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
	}

	private void handleTeleportProgress(Player player, int cooldown) {
		ChatUtils.send(player, plugin.getMessagesConfig().teleportMessageProgress, "{time}", cooldown);
		ChatUtils.sendActionBar(player, plugin.getMessagesConfig().teleportActionBarProgress, "{time}", cooldown);

		player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F);
	}

	private void cancelTeleport(Player player) {
		teleports.remove(player.getUniqueId()).cancel();
	}
}