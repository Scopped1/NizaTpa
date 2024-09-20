package it.scopped.nizetpa;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.PaperCommandManager;
import it.scopped.nizetpa.commands.TpaAcceptCommand;
import it.scopped.nizetpa.commands.TpaCancelCommand;
import it.scopped.nizetpa.commands.TpaCommand;
import it.scopped.nizetpa.commands.TpaHereCommand;
import it.scopped.nizetpa.configurations.MessagesConfig;
import it.scopped.nizetpa.configurations.SettingsConfig;
import it.scopped.nizetpa.tpa.TpaManager;
import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class NizeTpa extends JavaPlugin {

	@Getter
	private static NizeTpa instance;

	private SettingsConfig settingsConfig;
	private MessagesConfig messagesConfig;

	private TpaManager tpaManager;

	@Override
	public void onEnable() {
		instance = this;

		initialize();

		registerCommands(
				new TpaCommand(this),
				new TpaHereCommand(this),
				new TpaAcceptCommand(this),
				new TpaCancelCommand(this)
		);
	}

	@Override
	public void onDisable() {

	}

	void initialize() {
		this.settingsConfig = new SettingsConfig(this);
		this.messagesConfig = new MessagesConfig(this);

		this.tpaManager = new TpaManager(this);
	}

	void registerCommands(BaseCommand... commands) {
		PaperCommandManager commandManager = new PaperCommandManager(this);

		for(BaseCommand command : commands)
			commandManager.registerCommand(command);
	}
}