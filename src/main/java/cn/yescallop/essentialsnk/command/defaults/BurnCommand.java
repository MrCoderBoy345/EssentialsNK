package cn.yescallop.essentialsnk.command.defaults;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import cn.yescallop.essentialsnk.EssentialsAPI;
import cn.yescallop.essentialsnk.command.CommandBase;
import me.onebone.economyapi.EconomyAPI;

public class BurnCommand extends CommandBase {
    private EconomyAPI econAPI;
    public BurnCommand(EssentialsAPI api) {
        super("burn", api);
    }

    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!this.testPermission(sender)) {
            return false;
        }
        if (args.length != 2) {
            this.sendUsage(sender);
            return false;
        }
        Player player = api.getServer().getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(TextFormat.RED + lang.translateString("commands.generic.player.notfound", args[0]));
            return false;
        }
        int time;
        try {
            time = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(TextFormat.RED + lang.translateString("commands.generic.number.invalidinteger", args[1]));
            return false;
        }
        if (time <= 0) {
            sender.sendMessage(TextFormat.RED + lang.translateString("commands.generic.number.invalidinteger", args[1]));
            return false;
        }
        Player playersender =(Player) sender;
        double price = (double) time*50;
        econAPI = EconomyAPI.getInstance();
        if (econAPI.myMoney(playersender) < price){
            player.sendMessage(TextFormat.RED + "not enough money");
            return false;
        }
        player.setOnFire(time);
        sender.sendMessage(lang.translateString("commands.burn.success", player.getDisplayName()));
        sender.sendMessage(TextFormat.RED + "-" + econAPI.getMonetaryUnit() + price);
        econAPI.reduceMoney(playersender, price);
        return true;
    }
}
