package me.yirf.judge.interfaces;

import net.md_5.bungee.api.ChatColor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Color {
    Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
    char COLOR_CHAR = '\u00A7';

    static String format(String message) {
        if (message == null || message.isEmpty()) {
            return "";
        }

        String hexColored = formatHexColors(message);
        return formatTraditionalColors(hexColored);
    }

    static String formatHexColors(String message) {
        Matcher matcher = HEX_PATTERN.matcher(message);
        StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);
        while (matcher.find()) {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer, ChatColor.of("#" + group).toString());
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    static String formatTraditionalColors(String message) {
        char[] b = message.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == '&' && "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx".indexOf(b[i + 1]) > -1) {
                b[i] = COLOR_CHAR;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }
}