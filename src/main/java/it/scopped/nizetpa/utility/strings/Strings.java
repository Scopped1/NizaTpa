package it.scopped.nizetpa.utility.strings;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class Strings {
	public boolean isNumber(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	public String formatNumber(double value) {
		if (value == 0) return "0";

		String[] suffixes = new String[]{"", "K", "M", "B", "T", "Q", "E"};

		int index = 0;

		while (value >= 1000 && index < suffixes.length - 1) {
			value /= 1000;
			index++;
		}

		return fixValue(String.format("%.1f%s", value, suffixes[index]));
	}

	public String fixValue(String value) {
		return value.endsWith(".0") ? value.substring(0, value.length() - 2) : value;
	}

	public String getCurrentDateFormatted() {
		return LocalDateTime.now(ZoneId.of("Europe/Rome"))
				.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
	}

	public String parseStringTime(long seconds) {
		long[] values = {seconds / 86400L, seconds % 86400L / 3600L, seconds % 3600L / 60L, seconds % 60L};
		String[] labels = {"d ", "h ", "m ", "s"};
		StringBuilder formattedTime = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			if (values[i] > 0L || !formattedTime.isEmpty())
				formattedTime.append(values[i]).append(labels[i]);
		}
		return formattedTime.toString();
	}

	public String formatTimer(long millis) {
		return String.format("%.1f", millis / 1000.0);
	}

	public long parseTimeString(String timeString) {
		long seconds = 0L;
		for (String part : timeString.split("\\s+"))
			seconds += Integer.parseInt(part.substring(0, part.length() - 1)) * (
					part.endsWith("d") ? 86400L : (part.endsWith("h") ? 3600L : (part.endsWith("m") ? 60L : 1L)));
		return seconds;
	}

	public String replace(String message, Object... params) {
		if (params.length % 2 != 0)
			throw new IllegalArgumentException("Parameters should be in key-value pairs.");

		for (int i = 0; i < params.length; i += 2)
			message = message.replace(params[i].toString(), params[i + 1].toString());

		return message;
	}
}