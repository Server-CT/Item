package org.MeowCat.SCT.Item;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main plugin;

    public void A() {
        getServer().getConsoleSender().sendMessage("§f||§6=-=-=-=-=-=-=-=§7§l[Item]§6=-=-=-=-=-=-=-=-=§f||");
        getServer().getConsoleSender().sendMessage("§6           Group: §eSCT  §f| §6Version: §b1.0");
        getServer().getConsoleSender().sendMessage("§e                       插件加载完毕！");
        getServer().getConsoleSender().sendMessage("§x||=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=§x||");
    }

    public void onEnable() {
        plugin = this;
        A();
    }

    public void onDisable() {

    }
}
