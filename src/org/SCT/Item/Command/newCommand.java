package org.SCT.Item.Command;

import org.SCT.Item.Main;
import org.SCT.Item.Util.FileTool;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class newCommand implements CommandExecutor {

    public static Main plugin = Main.plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (args.length == 0) {
            for (String helpmsg : Main.plugin.language.getStringList("helpmsg")) {
                helpmsg = helpmsg.replace("&", "§");
                player.sendMessage(helpmsg);
            }
            //helpMsg(sender);
            return true;
        }
        if (player.isOp()) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("info")) {
                    SCT(sender);
                } else if (args[0].equalsIgnoreCase("reload")) {
                    plugin.load();
                    plugin.info("yes");
                    player.sendMessage(Main.plugin.language.getString("language.RealoadPlugin").replace("&", "§"));
                    // plugin.info(LangTool.ReloadPlugin);
                } else {
                    for (String helpmsg : Main.plugin.language.getStringList("helpmsg")) {
                        helpmsg = helpmsg.replace("&", "§");
                        player.sendMessage(helpmsg);
                    }
                }
            }
            if (args.length == 2 && args[0].equalsIgnoreCase("get")) {
                if (sender instanceof Player) FileTool.giveItem(player, args[1]);
                player.sendMessage(Main.plugin.language.getString("language.GetItem").replace("&", "§"));
            }
            if (args.length == 4 && args[0].equalsIgnoreCase("give")) {
                Player target = Bukkit.getPlayer(args[1]);
                if (!target.isOnline()) {//玩家不在线
                    Main.plugin.language.getString("language. PlayerNotOnline").replace("&", "§");
                }
                FileTool.giveItem(player, args[2]);
                Main.plugin.language.getString("language.SentItem").replace("&", "§");
            }
        } else Main.plugin.language.getString("language.NoPermission");
        return true;
    }
/*
    private void helpMsg(CommandSender sender) {
        sender.sendMessage("§f/sctitem get [物品名字]  §e-> §6给自己一个[物品]");
        sender.sendMessage("§f/sctitem give [玩家] [物品名字] [数量] §e-> §6给[玩家] [数量] 个 [物品]");
        sender.sendMessage("§f/sctitem reload  §e-> §6重载插件");
        sender.sendMessage("§f/sctitem info 查看SCT小组相关事宜/Q群");
    }
    */

    private void SCT(CommandSender sender) {
        sender.sendMessage("§fSCT小组审核群: 711743820");
        sender.sendMessage("§fBUG反馈、建议: Q2457043027");
        sender.sendMessage(" ");
        sender.sendMessage("§7========§aSCT全家桶计划进度§7========\"");
        sender.sendMessage("§f基础类");
        sender.sendMessage("§71. SCTItem(本插件) §a完成");
        sender.sendMessage("§72. SCTLevel §310%");
        sender.sendMessage("§73. SCTLogin §7筹备中");
        sender.sendMessage("§74. SCTMenu §7未开始");
        sender.sendMessage("§75. SCTPoint §7未开始");
        sender.sendMessage("§7敬请期待");
        sender.sendMessage("§f进阶类");
        sender.sendMessage("§7敬请期待");
    }
}
