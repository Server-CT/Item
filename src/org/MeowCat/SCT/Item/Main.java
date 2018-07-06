package org.MeowCat.SCT.Item;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main plugin;

    public void onEnable() {
        plugin = this;
        getServer().getConsoleSender().sendMessage("§f||§6=-=-=-=-=-=-=-=§7§l[Item]§6=-=-=-=-=-=-=-=-=§f||");
        getServer().getConsoleSender().sendMessage("§6          Group: §eSCT  §f| §6Version: §b1.0");
        getServer().getConsoleSender().sendMessage("§e                       插件加载完毕！");
        getServer().getConsoleSender().sendMessage("§a||=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=||");
    }

    public void onDisable() {
        getServer().getConsoleSender().sendMessage("§f||§6=-=-=-=-=-=-=-=§7§l[Item]§6=-=-=-=-=-=-=-=-=§f||");
        getServer().getConsoleSender().sendMessage("§6          Group: §eSCT  §f| §6Version: §b1.0");
        getServer().getConsoleSender().sendMessage("§e                       插件卸载完毕！");
        getServer().getConsoleSender().sendMessage("§a||=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=||");
    }
}
