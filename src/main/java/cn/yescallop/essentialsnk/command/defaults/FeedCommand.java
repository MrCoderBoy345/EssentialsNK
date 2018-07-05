package cn.yescallop.essentialsnk.command.defaults;

import cn.nukkit.Player;
import cn.nukkit.PlayerFood;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import cn.yescallop.essentialsnk.EssentialsAPI;
import cn.yescallop.essentialsnk.command.CommandBase;
import me.onebone.economyapi.EconomyAPI;

public class FeedCommand extends CommandBase {
    private EconomyAPI econAPI;
    public FeedCommand(EssentialsAPI api) {
        super("feed", api);
        this.setAliases(new String[]{"eat"});
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
            if (!sender.hasPermission("essentialsnk.feed.others")) {
                this.sendPermissionMessage(sender);
                return false;
            }
            player = api.getServer().getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage(TextFormat.RED + lang.translateString("commands.generic.player.notfound", args[0]));
                return false;
            }
        }
        double price = 200;
        econAPI = EconomyAPI.getInstance();
        if (econAPI.myMoney(player) < price){
            player.sendMessage(TextFormat.RED + "not enough money");
            return false;
        }
        PlayerFood foodData = player.getFoodData();
        foodData.setLevel(foodData.getMaxLevel());
        foodData.sendFoodLevel();
        player.sendMessage(lang.translateString("commands.feed.success"));
        sender.sendMessage(TextFormat.RED + "-" + econAPI.getMonetaryUnit() + price);
        econAPI.reduceMoney(player, price);
        if (sender != player) {
            sender.sendMessage(lang.translateString("commands.feed.success.other", player.getDisplayName()));
        }
        return true;
    }
}
