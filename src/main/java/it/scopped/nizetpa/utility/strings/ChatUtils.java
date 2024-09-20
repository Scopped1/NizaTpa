package it.scopped.nizetpa.utility.strings;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;

@UtilityClass
public class ChatUtils {
	public Component translate(String string, Object... params) {
		return MiniMessage.miniMessage().deserialize(Strings.replace(string, params));
	}

	public void send(CommandSender sender, String message, Object... params) {
		sender.sendMessage(translate(message, params));
	}

	public void sendTitle(Player player, String title, Object... params) {
		String[] strings = title.split(";");

		if (strings.length == 0)
			throw new RuntimeException("Title formatting error! String: " + title);

		player.showTitle(
				Title.title(
						translate(strings[0], params),
						translate(strings[1], params),
						Title.Times.times(
								Duration.ofSeconds(1),
								Duration.ofSeconds(3),
								Duration.ofSeconds(1)
						)
				)
		);
	}

	public void sendActionBar(Player player, String text, Object... params) {
		player.sendActionBar(translate(text, params));
	}
}