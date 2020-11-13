package de.rivex.challengeutils.main;

import de.rivex.challengeutils.challenges.*;
import de.rivex.challengeutils.gamerules.DamageIndicator;
import de.rivex.challengeutils.listeners.JoinListener;
import de.rivex.challengeutils.listeners.QuitListener;
import de.rivex.challengeutils.utils.Reset;
import de.rivex.challengeutils.utils.SettingsGUI;
import de.rivex.challengeutils.utils.Timer;
import de.rivex.challengeutils.utils.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {
    public static boolean devMode;
    private static Main plugin;

    public static Main getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        register();
        if (getConfig().getBoolean("reset")) {
            try {
                FileUtils.deleteDirectory(new File(getServer().getWorldContainer().getAbsolutePath() + "/" + getConfig().getString("worldName")));
                FileUtils.deleteDirectory(new File(getServer().getWorldContainer().getAbsolutePath() + "/" + getConfig().getString("worldName") + "_nether"));
                FileUtils.deleteDirectory(new File(getServer().getWorldContainer().getAbsolutePath() + "/" + getConfig().getString("worldName") + "_the_end"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            getConfig().set("reset", false);
            saveConfig();

            UpdateChecker.init(this, 85731);
        }
    }

    private void register() {
        getCommand("timer").setExecutor(new Timer());
        getCommand("settings").setExecutor(new SettingsGUI());
        getCommand("reset").setExecutor(new Reset());

        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new QuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new SettingsGUI(), this);
        Bukkit.getPluginManager().registerEvents(new Place(), this);
        Bukkit.getPluginManager().registerEvents(new DamageIndicator(), this);
        Bukkit.getPluginManager().registerEvents(new CraftingTable(), this);
        Bukkit.getPluginManager().registerEvents(new Break(), this);
        Bukkit.getPluginManager().registerEvents(new Sneak(), this);
        Bukkit.getPluginManager().registerEvents(new RandomDrops(), this);
    }
}