package me.yirf.judge.menu;

import me.clip.placeholderapi.PlaceholderAPI;
import me.yirf.judge.Judge;
import me.yirf.judge.group.Group;
import me.yirf.judge.interfaces.Color;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import static org.bukkit.entity.Display.Billboard;

public class Display implements Color {
    public static void spawnMenu(Player player, Player target) {
        TextDisplay display = target.getWorld().spawn(target.getLocation(), TextDisplay.class);
        display.setShadowed(true);
        display.setBillboard(Billboard.CENTER);
        display.setVisibleByDefault(false);
        if (display == null) {
            Judge.instance.getLogger().severe("Unable to spawn display entity");
        }

        display.text(getShow(player, target));

        target.addPassenger(display);
        display.setTransformation(
                new Transformation(
                        new Vector3f(Judge.menuHoroz, Judge.menuVert, 0),
                        new AxisAngle4f(),
                        new Vector3f(Judge.menuScale),
                        new AxisAngle4f()
                )
        );

        player.showEntity(Judge.instance, display);
        Group.add(display, player);
    }

    public static TextComponent getShow(Player player, Player target) {
        TextComponent.Builder text = Component.text();

        for (String line : Judge.menuTexts) {
            if (Judge.hasPapi) {
                line = PlaceholderAPI.setPlaceholders(target, line);
            }
            line = Color.format(line).replaceAll("%player%", target.getName()).replaceAll("%viewer%", player.getName());

            TextComponent textComponent = Component.text(line);

            if (text == null) {
                text.append(textComponent);
            }

            text.appendNewline().append(textComponent);
        }

        return text.build();
    }
}
