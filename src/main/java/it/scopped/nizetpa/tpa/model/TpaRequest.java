package it.scopped.nizetpa.tpa.model;

import it.scopped.nizetpa.tpa.type.TpaType;
import it.scopped.nizetpa.utility.player.TeleportUtil;
import it.scopped.nizetpa.utility.strings.ChatUtils;
import it.scopped.nizetpa.utility.tuple.TuplePair;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

@Getter @Setter
public class TpaRequest {
	private final TuplePair<Player, Player> players;
	private TpaType tpaType;
	private boolean accepted;
	private int expirationTime;

	public TpaRequest(Player sender, Player receiver, TpaType tpaType) {
		this.players = new TuplePair<>(sender, receiver);

		this.tpaType = tpaType;
		this.accepted = false;

		this.expirationTime = 30;
	}

	public void decrementTime() {
		if(expirationTime <= 0)
			return;

		expirationTime--;
	}

	public void teleport() {
		if (tpaType == TpaType.TPA)
			TeleportUtil.teleportAfter(players.getFirst(), players.getSecond().getLocation());
		else
			TeleportUtil.teleportAfter(players.getSecond(), players.getFirst().getLocation());
	}
}