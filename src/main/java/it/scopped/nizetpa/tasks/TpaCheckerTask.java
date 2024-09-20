package it.scopped.nizetpa.tasks;

import it.scopped.nizetpa.NizeTpa;
import it.scopped.nizetpa.tpa.TpaManager;
import it.scopped.nizetpa.tpa.model.TpaRequest;
import it.scopped.nizetpa.utility.strings.ChatUtils;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class TpaCheckerTask implements Runnable{

	private final NizeTpa plugin;

	@Override
	public void run() {
		TpaManager tpaManager = plugin.getTpaManager();

		if(tpaManager.getRequests().isEmpty()) return;

		for(TpaRequest request : tpaManager.getRequests().values()) {

			if(request == null) continue;
			if(request.isAccepted()) continue;

			Player sender = request.getPlayers().getFirst();
			Player receiver = request.getPlayers().getSecond();

			if(request.getExpirationTime() <= 0) {
				ChatUtils.send(sender, plugin.getMessagesConfig().tpaRequestExpired);
				ChatUtils.send(receiver, plugin.getMessagesConfig().tpaRequestExpired);
				return;
			}

			request.decrementTime();
		}
	}
}