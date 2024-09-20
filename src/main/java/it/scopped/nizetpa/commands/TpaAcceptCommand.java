package it.scopped.nizetpa.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import it.scopped.nizetpa.NizeTpa;
import it.scopped.nizetpa.tpa.TpaManager;
import it.scopped.nizetpa.tpa.model.TpaRequest;
import it.scopped.nizetpa.utility.strings.ChatUtils;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("tpaccept")
@RequiredArgsConstructor
public class TpaAcceptCommand extends BaseCommand {

	private final NizeTpa plugin;

	@Default
	@CommandCompletion("@players")
	public void onCommand(Player player, String[] args) {
		if (args.length == 0) {
			ChatUtils.send(player, plugin.getMessagesConfig().enterPlayerName);
			return;
		}

		Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			ChatUtils.send(player, plugin.getMessagesConfig().playerNotFound);
			return;
		}

		TpaManager tpaManager = plugin.getTpaManager();

		TpaRequest request = tpaManager.getRequest(player);

		if (request == null) {
			ChatUtils.send(player, plugin.getMessagesConfig().tpaNoRequestsFromPlayers);
			return;
		}

		if (request.isAccepted()) {
			ChatUtils.send(player, plugin.getMessagesConfig().tpaRequestAlreadyAccept);
			return;
		}

		tpaManager.acceptRequest(request);
	}
}