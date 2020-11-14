package de.rivex.challengeutils.utils;

import de.rivex.challengeutils.challenges.*;
import de.rivex.challengeutils.main.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class Timer implements CommandExecutor {

    public static boolean timerRunning = false;
    public static int time;
    public static BukkitTask timer;
    public static BukkitTask showTimeTask;
    public static BukkitTask timerStarted;

    public static void resume() {
        if (!timerRunning) {
            time = 0;
            timerRunning = true;
            startTimer();
            showTime();
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.sendMessage("§7[§5Timer§7] §a§lTimer gestartet.");
                players.sendTitle("§a§lTimer gestartet", "", 20, 20, 20);
            }
            startForceChallenges();
            RandomDrops.randomize();
            RandomCrafting.randomize();
        } else Bukkit.broadcastMessage("§7[§5Timer§7] §c§lTimer läuft bereits.");
    }

    public static void reset() {
        if (timerRunning) {
            time = 0;
            timer.cancel();
            timerRunning = false;
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.sendMessage("§7[§5Timer§7] §a§lTimer zurückgesetzt.");
                players.sendTitle("§a§lTimer zurückgesetzt", "", 20, 20, 20);
            }
        } else Bukkit.broadcastMessage("§7[§5Timer§7] §c§lTimer läuft nicht.");
    }

    public static String convertedTime(int time) {
        int hours = time / 3600;
        int minutes = time % 3600 / 60;
        int seconds = time % 60;
        return String.format("§b%02d§7:§b%02d§7:§b%02d", hours, minutes, seconds);
    }

    private static void startForceChallenges() {
        ForceBlock.start();
        ForceItem.start();
        ForceY.start();
        ForceMLG.start();
    }

    private static void startTimer() {
        timer = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), () -> time++, 0L, 20L);
        timerRunning = true;
    }

    public static void showTime() {
        showTimeTask = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), () -> {
            for (Player players : Bukkit.getOnlinePlayers())
                players.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§6Challenge: §b" + ChatColor.GREEN + convertedTime(time)));
        }, 0L, 20L);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("resume")) {
                    resume();
                } else if (args[0].equalsIgnoreCase("reset")) {
                    reset();
                } else sender.sendMessage("§cSyntax: §7/timer <resume|reset>");
            } else sender.sendMessage("§cSyntax: §7/timer <resume|reset>");
        } else sender.sendMessage(ChatColor.RED + "Dazu musst du ein Spieler sein...");
        return false;
    }
}