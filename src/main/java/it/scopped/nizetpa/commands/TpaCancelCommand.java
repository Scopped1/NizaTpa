package it.scopped.nizetpa.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import it.scopped.nizetpa.NizeTpa;
import it.scopped.nizetpa.tpa.TpaManager;
import it.scopped.nizetpa.utility.strings.ChatUtils;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("tpadeny|tparefuse")
@RequiredArgsConstructor
public class TpaCancelCommand extends BaseCommand {

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

		if (!tpaManager.hasRequest(player) || !tpaManager.hasRequest(target)) {
			ChatUtils.send(player, plugin.getMessagesConfig().tpaNoRequestsFromPlayers);
			return;
		}

		tpaManager.cancelRequest(target);
	}
}