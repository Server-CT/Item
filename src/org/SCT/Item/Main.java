package org.SCT.Item;

import org.SCT.Item.Command.newCommand;
import org.SCT.Item.Util.FileTool;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.net.URL;

/**
 * SCTItem Main
 *
 * @author SCT_鹦鹉
 * @author TangYuan
 * @author SCT_Alchemy
 * @author MeowCat_MlgmXyysd
 * Copyright (C) 2018 Server CT All Rights Reserved.
 */
public class Main extends JavaPlugin {

    private String latestVer;
    public static Main plugin;
    private File folder = getDataFolder();
    private File lang = new File(folder, "lang.yml");
    public Boolean isSentConsoleMsg;

    public Main() {
        if (plugin == null) plugin = this;
        else throw new NullPointerException("Hahhah, surprise mother fucker!");
    }

    public YamlConfiguration language = YamlConfiguration.loadConfiguration(lang);

    @Override
    public void onEnable() {
        isSentConsoleMsg = getConfig().getBoolean("isSentConsoleMsg");
        load();
        Bukkit.getServer().getConsoleSender().sendMessage("SCTItem加载成功!");
        getServer().getPluginCommand("sctitem").setExecutor(new newCommand());
        updateChecker();
    }

    public void load() {
        FileTool.reload();
        saveResource("items.yml", false);
        try {
            if (!lang.exists())
                lang.mkdir();
            else
                getConfig().save(lang);
        } catch (IOException e) {
            warn(e.getMessage());
        }
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getConsoleSender().sendMessage("卸载完成，感谢使用");
    }

    public static void info(String msg) {
        plugin.getLogger().info(msg);
    }

    private static void warn(String msg) {
        plugin.getLogger().warning(msg);
    }

    public static Main getPlugin() {
        return plugin;
    }

    private String getLatestVersion() {
        String ver = null;
        try {
            URL url = new URL("http://sct.meowcat.org/version/item.mct");
            InputStream is = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            ver = br.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        latestVer = ver;
        return ver;
    }

    private boolean isLatestVersion() {
        boolean islatest = false;
        String latest = getLatestVersion();
        String current = getDescription().getVersion();
        if (latest.equalsIgnoreCase(current)) {
            islatest = true;
        }
        return islatest;
    }

    private void updateChecker() {
        new BukkitRunnable() {
            public void run() {
                if (isLatestVersion()) {
                    Bukkit.getConsoleSender().sendMessage("已是最新版本");
                } else {
                    Bukkit.getConsoleSender()
                            .sendMessage("最新版本为：" + latestVer);
                }
            }
        }.runTaskAsynchronously(this);
    }
}
