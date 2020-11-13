package de.rivex.challengeutils.challenges;

import de.rivex.challengeutils.main.Main;
import de.rivex.challengeutils.utils.SettingsGUI;
import de.rivex.challengeutils.utils.Timer;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ForceY {
    private static BukkitTask collectRunnableTask;
    private static BukkitTask chooseInstruction;
    private static int collectTime;
    private static BukkitTask collectTask;
    private static FileConfiguration config = Main.getPlugin().getConfig();

    public static void start() {
        if (SettingsGUI.forceYChallenge) {
            int min = config.getInt("forceY.minTime");
            int max = config.getInt("forceY.maxTime");
            int rndTime = new Random().nextInt((max - min) + 1) + min;
            chooseInstruction = Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
                int minY = config.getInt("forceY.minY");
                int maxY = config.getInt("forceY.maxY");
                int y = ThreadLocalRandom.current().nextInt(minY, maxY);
                Timer.showTimeTask.cancel();
                int minCollect = config.getInt("forceY.minCollectTime");
                int maxCollect = config.getInt("forceY.maxCollectTime");
                int rndTime2 = new Random().nextInt((maxCollect - minCollect) + 1) + minCollect;
                collectTime = rndTime2 / 20;
                collectRunnableTask = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), () -> {
                    collectTime--;
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§aAnweisung: Höhe §9" + y + " §ain §9" + convertedTime(collectTime)));
                    }
                }, 0L, 20L);
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage("§7[§bForceBlock§7] §aAnweisung: Stehe auf der Höhe §9" + y + " §ain §9" + convertedTime(collectTime));
                }
                collectTask = Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
                    Timer.showTime();
                    collectRunnableTask.cancel();
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        if (players.getLocation().getY() == y) {
                            players.sendMessage(config.getString("forceY.passedMessage"));
                            if (!chooseInstruction.isCancelled()) {
                                chooseInstruction.cancel();
                            }
                            if (!collectTask.isCancelled()) {
                                collectTask.cancel();
                            }
                            start();
                        } else {
                            Timer.time = 0;
                            Timer.reset();
                            players.sendMessage(config.getString("forceY.punishMessage"));
                            players.setGameMode(GameMode.SPECTATOR);

                            if (!chooseInstruction.isCancelled()) {
                                chooseInstruction.cancel();
                            }
                            if (!collectTask.isCancelled()) {
                                collectTask.cancel();
                            }
                        }
                    }
                }, rndTime2);
            }, rndTime);
        }
    }


    private static String convertedTime(int collectTime) {
        int minutes = collectTime % 3600 / 60;
        int seconds = collectTime % 60;
        return String.format("§b%02d§7:§b%02d", minutes, seconds);
    }
}
