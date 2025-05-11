package me.yirf.judge.interfaces;

import net.md_5.bungee.api.ChatColor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Colored {
    Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
    Pattern MINI_MESSAGE_HEX_PATTERN = Pattern.compile("<#([A-Fa-f0-9]{6})>");
    char COLOR_CHAR = '\u00A7';

    static String format(String message) {
        if (message == null || message.isEmpty()) {
            return "";
        }

        String hexFormatted = formatMiniMessageHex(formatHexColors(message));
        return formatTraditionalColors(hexFormatted);
    }

    static String formatHexColors(String message) {
        Matcher matcher = HEX_PATTERN.matcher(message);
        StringBuilder buffer = new StringBuilder(message.length() + 8);
        while (matcher.find()) {
            String hexCode = matcher.group(1);
            matcher.appendReplacement(buffer, ChatColor.of("#" + hexCode).toString());
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    static String formatMiniMessageHex(String message) {
        Matcher matcher = MINI_MESSAGE_HEX_PATTERN.matcher(message);
        StringBuilder buffer = new StringBuilder(message.length() + 8);
        while (matcher.find()) {
            String hexCode = matcher.group(1);
            matcher.appendReplacement(buffer, ChatColor.of("#" + hexCode).toString());
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    static String formatTraditionalColors(String message) {
        char[] chars = message.toCharArray();
        for (int i = 0; i < chars.length - 1; i++) {
            if (chars[i] == '&' && "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx".indexOf(chars[i + 1]) > -1) {
                chars[i] = COLOR_CHAR;
                chars[i + 1] = Character.toLowerCase(chars[i + 1]);
            }
        }
        return new String(chars);
    }
}
