package it.scopped.nizetpa.configurations;

import it.scopped.nizetpa.NizeTpa;
import net.pino.simpleconfig.BaseConfig;
import net.pino.simpleconfig.annotations.Config;
import net.pino.simpleconfig.annotations.ConfigFile;
import net.pino.simpleconfig.annotations.inside.Path;

@Config
@ConfigFile("messages.yml")
public class MessagesConfig extends BaseConfig {
	public MessagesConfig(NizeTpa plugin) {
		registerConfig(plugin);
	}

	// ERRORS
	@Path("errors.player-not-found")
	public String playerNotFound = "<red>Il giocatore non è stato trovato! Potrebbe non essere online.</red>";
	@Path("errors.enter-player-name")
	public String enterPlayerName = "<red>Inserisci il nome del giocatore.</red>";

	// TPA
	@Path("tpa.request-accepted")
	public String tpaRequestAccepted = "<green>Richiesta di teletrasporto accettata.</green>";
	@Path("tpa.request-denied")
	public String tpaRequestDenied = "<red>Richiesta di teletrasporto rifiutata.</red>";
	@Path("tpa.request-expired")
	public String tpaRequestExpired = "<red>Richiesta di teletrasporto scaduta.</red>";
	@Path("tpa.request-already_accept")
	public String tpaRequestAlreadyAccept = "<red>La richiesta è stata già accettata.</red>";
	@Path("tpa.no-request-from-players")
	public String tpaNoRequestsFromPlayers = "<red>Non possiedi nessuna richiesta in sospeso.</red>";
	@Path("tpa.cant-request-yourself")
	public String tpaCantRequestYourself = "<red>Non puoi invare una richiesta di teletrasporto a te stesso.</red>";
	@Path("tpa.request-sent")
	public String tpaRequestSent = "<white>Richiesta di teletrasporto inviata a</white> <yellow>{receiver}</yellow><white>.</white>";
	@Path("tpa.request-received")
	public String tpaRequestReceived = "<white>Hai ricevuto una richiesta di <aqua>{tpa_type}</aqua> da parte di</white> <yellow>{sender}</yellow><white>!</white>\n&7> <aqua><bold><click:run_command:/tpaccept {sender}>[Accetta]</click></bold></aqua> <red><bold><click:run_command:/tparefuse {sender}>[Rifiuta]</click></bold></red>";

	// TELEPORT
	@Path("teleport.progress.message")
	public String teleportMessageProgress = "<aqua>Verrai teletrasportato tra {time} secondi...</aqua>";
	@Path("teleport.progress.action_bar")
	public String teleportActionBarProgress = "<aqua>{time}...</aqua>";
	@Path("teleport.completed.message")
	public String teleportMessageCompleted = "<green>Sei stato teletrasportato con successo!</green>";
	@Path("teleport.completed.action_bar")
	public String teleportActionBarCompleted = "<green>Teletrasporto completato!</green>";
	@Path("teleport.failed.message")
	public String teleportMessageFailed = "<white>Teletrasporto annullato!</white> <red>Ti sei mosso!</red>";
	@Path("teleport.failed.action_bar")
	public String teleportActionBarFailed = "<red>Ti sei mosso!</red>";

}