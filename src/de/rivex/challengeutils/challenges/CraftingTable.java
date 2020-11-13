package de.rivex.challengeutils.challenges;

import de.rivex.challengeutils.main.Main;
import de.rivex.challengeutils.utils.SettingsGUI;
import de.rivex.challengeutils.utils.Timer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryType;

public class CraftingTable implements Listener {

    @EventHandler
    public void onCrafting(CraftItemEvent event) {

        if (event.getWhoClicked() instanceof Player) {

            if (event.getInventory().getType().equals(InventoryType.WORKBENCH)) {

                if (SettingsGUI.craftingChallenge && Timer.timerRunning) {
                    Player player = (Player) event.getWhoClicked();
                    event.setCancelled(true);
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.sendMessage(Main.getPlugin().getConfig().getString("noBlockBreak.punishMessage")
                                .replace("<player>", player.getName()));
                        players.setGameMode(GameMode.SPECTATOR);
                    }
                    Timer.reset();
                }
            }
        }
    }
}
