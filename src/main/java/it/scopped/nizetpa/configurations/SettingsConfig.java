package it.scopped.nizetpa.configurations;

import it.scopped.nizetpa.NizeTpa;
import net.pino.simpleconfig.BaseConfig;
import net.pino.simpleconfig.annotations.Config;
import net.pino.simpleconfig.annotations.ConfigFile;

@Config
@ConfigFile("config.yml")
public class SettingsConfig extends BaseConfig {
	public SettingsConfig(NizeTpa plugin) {
		registerConfig(plugin);
	}


}