package cn.yescallop.essentialsnk.command.defaults;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Location;
import cn.nukkit.utils.TextFormat;
import cn.yescallop.essentialsnk.EssentialsAPI;
import cn.yescallop.essentialsnk.command.CommandBase;
import me.onebone.economyapi.EconomyAPI;

public class BackCommand extends CommandBase {
    private EconomyAPI econApi;
    public BackCommand(EssentialsAPI api) {
        super("back", api);
        this.setAliases(new String[]{"return"});
    }
   
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!this.testPermission(sender)) {
            return false;
        }
        if (!this.testIngame(sender)) {
            return false;
        }
        if (args.length != 0) {
            this.sendUsage(sender);
            return false;
        }
        Player player = (Player) sender;
        Location pos = api.getLastLocation(player);
        if (pos == null) {
            sender.sendMessage(TextFormat.RED + lang.translateString("commands.back.notavalible"));
            return false;
        }
        econApi = EconomyAPI.getInstance();
        if (econApi.myMoney(player) < 150){
            player.sendMessage(TextFormat.RED + "not enough money");
            return false;
        }
        player.teleport(pos);
        sender.sendMessage(lang.translateString("commands.generic.teleporting"));
        player.sendMessage("-" + econApi.getMonetaryUnit() + "150");
        econApi.reduceMoney(player,150);
        return true;
    }
}
