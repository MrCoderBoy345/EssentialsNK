package cn.yescallop.essentialsnk.command.defaults;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Position;
import cn.nukkit.utils.TextFormat;
import cn.yescallop.essentialsnk.EssentialsAPI;
import cn.yescallop.essentialsnk.command.CommandBase;
import me.onebone.economyapi.EconomyAPI;

public class LightningCommand extends CommandBase {
    private EconomyAPI econAPI;
    public LightningCommand(EssentialsAPI api) {
        super("lightning", api);
        this.setAliases(new String[]{"strike", "smite", "thor", "shock"});
    }

    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!this.testPermission(sender)) {
            return false;
        }
        if (args.length > 1) {
            this.sendUsage(sender);
            return false;
        }
        Player player;
        if (args.length == 0) {
            if (!this.testIngame(sender)) {
                return false;
            }
            player = (Player) sender;
        } else {
            player = api.getServer().getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage(TextFormat.RED + lang.translateString("commands.generic.player.notfound", args[0]));
                return false;
            }
        }
        Position pos = args.length == 1 ? player : player.getTargetBlock(120);
        if (pos == null) {
            sender.sendMessage(TextFormat.RED + lang.translateString("commands.lightning.unreachable"));
            return false;
        }
        double price = 200;
        econAPI = EconomyAPI.getInstance();
        if (econAPI.myMoney(player) < price){
            player.sendMessage(TextFormat.RED + "not enough money");
            return false;
        }
        api.strikeLighting(pos);
        sender.sendMessage(lang.translateString("commands.lightning.success"));
        sender.sendMessage(TextFormat.RED + "-" + econAPI.getMonetaryUnit() + price);
        econAPI.reduceMoney(player, price);
        return true;
    }
}
