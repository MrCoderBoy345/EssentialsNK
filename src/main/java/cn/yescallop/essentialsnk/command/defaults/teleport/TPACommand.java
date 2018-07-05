package cn.yescallop.essentialsnk.command.defaults.teleport;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import cn.yescallop.essentialsnk.EssentialsAPI;
import cn.yescallop.essentialsnk.command.CommandBase;
import me.onebone.economyapi.EconomyAPI;

public class TPACommand extends CommandBase {
    private EconomyAPI econAPI;
    public TPACommand(EssentialsAPI api) {
        super("tpa", api);
        this.setAliases(new String[]{"call", "tpask"});
    }

    public boolean execute(CommandSender sender, String label, String[] args) {
        double price = 50;
        econAPI = EconomyAPI.getInstance();
        sender.sendMessage(TextFormat.RED + "-" + econAPI.getMonetaryUnit() + price);
        econAPI.reduceMoney((Player) sender, price);
        if (!this.testPermission(sender)) {
            return false;
        }
        if (!this.testIngame(sender)) {
            return false;
        }
        if (args.length != 1) {
            this.sendUsage(sender);
            return false;
        }
        Player player = api.getServer().getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(TextFormat.RED + lang.translateString("commands.generic.player.notfound", args[0]));
            return false;
        }
        if (sender == player) {
            sender.sendMessage(TextFormat.RED + lang.translateString("commands.tpa.self"));
            return false;
        }
        api.requestTP((Player) sender, player, true);
        player.sendMessage(lang.translateString("commands.tpa.invite", ((Player) sender).getName()));
        sender.sendMessage(lang.translateString("commands.tpa.success", player.getDisplayName()));
        return true;
    }
}
