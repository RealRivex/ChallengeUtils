package de.rivex.challengeutils.utils;

import de.rivex.challengeutils.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Reset implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (Main.getPlugin().getConfig().getBoolean("resetConfirm")) {
                if (args.length == 1 && args[0].equalsIgnoreCase("confirm")) {
                    Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
                        Main.getPlugin().getConfig().set("reset", true);
                        Main.getPlugin().saveConfig();
                        for (Player players : Bukkit.getOnlinePlayers()) {
                            players.kickPlayer(Main.getPlugin().getConfig().getString("resetMessage"));
                        }
                        Bukkit.spigot().restart();
                    }, 60L);
                } else player.sendMessage("§cSyntax: §7/reset <confirm>");
            } else {
                Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
                    Main.getPlugin().getConfig().set("reset", true);
                    Main.getPlugin().saveConfig();
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.kickPlayer(Main.getPlugin().getConfig().getString("resetMessage"));
                    }
                    Bukkit.spigot().restart();
                }, 60L);
            }
        } else sender.sendMessage("Dazu musst du ein Spieler sein...");
        return false;
    }
}
