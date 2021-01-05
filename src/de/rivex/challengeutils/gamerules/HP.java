package de.rivex.challengeutils.gamerules;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class HP {

    public static void updateHP(int i) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            players.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(i);
        }
    }
}
