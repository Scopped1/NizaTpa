package it.scopped.nizetpa.tpa;

import it.scopped.nizetpa.NizeTpa;
import it.scopped.nizetpa.tasks.TpaCheckerTask;
import it.scopped.nizetpa.tpa.model.TpaRequest;
import it.scopped.nizetpa.tpa.type.TpaType;
import it.scopped.nizetpa.utility.strings.ChatUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class TpaManager {

	private final NizeTpa plugin;
	private final Map<UUID, TpaRequest> requests = new HashMap<>();

	public TpaManager(NizeTpa plugin) {
		this.plugin = plugin;
		Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, new TpaCheckerTask(plugin), 0, 20L);
	}

	public void sendRequest(Player sender, Player receiver, TpaType tpaType) {
		String tpaTypeName = tpaType.name().toLowerCase().replace("_", "");

		ChatUtils.send(
				sender,
				plugin.getMessagesConfig().tpaRequestSent,
				"{tpa_type}",
				tpaTypeName,
				"{receiver}",
				receiver.getName(),
				"{sender}",
				sender.getName()
		);

		ChatUtils.send(
				receiver,
				plugin.getMessagesConfig().tpaRequestReceived,
				"{tpa_type}",
				tpaTypeName,
				"{sender}",
				sender.getName(),
				"{receiver}",
				receiver.getName()
		);

		TpaRequest request = new TpaRequest(sender, receiver, tpaType);
		requests.put(sender.getUniqueId(), request);
	}

	public void cancelRequest(Player player) {
		TpaRequest request = requests.remove(player.getUniqueId());
		if(request == null) return;

		ChatUtils.send(
				player,
				plugin.getMessagesConfig().tpaRequestDenied,
				"{sender}",
				player.getName(),
				"{receiver}",
				request.getPlayers().getSecond().getName()
		);

		ChatUtils.send(
				request.getPlayers().getSecond(),
				plugin.getMessagesConfig().tpaRequestDenied,
				"{sender}",
				player.getName(),
				"{receiver}",
				request.getPlayers().getSecond().getName()
		);
	}

	public TpaRequest getRequest(Player player) {
		return requests.getOrDefault(player.getUniqueId(), requests.values().stream()
				.filter(request -> request.getPlayers().getFirst().getUniqueId().equals(player.getUniqueId())
						|| request.getPlayers().getSecond().getUniqueId().equals(player.getUniqueId()))
				.findFirst().orElse(null));
	}

	public boolean hasRequest(Player player) {
		return getRequest(player) != null;
	}

	public void acceptRequest(TpaRequest request) {
		if(request == null) return;

		request.setAccepted(true);

		Player sender = request.getPlayers().getFirst();
		Player receiver = request.getPlayers().getSecond();

		ChatUtils.send(sender, plugin.getMessagesConfig().tpaRequestAccepted, "{sender}", sender.getName(), "{receiver}", receiver.getName());
		ChatUtils.send(receiver, plugin.getMessagesConfig().tpaRequestAccepted, "{sender}", sender.getName(), "{receiver}", receiver.getName());

		request.teleport();

		requests.remove(sender.getUniqueId());
	}
}