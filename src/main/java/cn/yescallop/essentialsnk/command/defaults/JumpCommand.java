package cn.yescallop.essentialsnk.command.defaults;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import cn.yescallop.essentialsnk.EssentialsAPI;
import cn.yescallop.essentialsnk.command.CommandBase;
import me.onebone.economyapi.EconomyAPI;

public class JumpCommand extends CommandBase {
    private EconomyAPI econAPI;
    public JumpCommand(EssentialsAPI api) {
        super("jump", api);
        this.setAliases(new String[]{"j", "jumpto"});
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
        Block block = player.getTargetBlock(120, EssentialsAPI.NON_SOLID_BLOCKS);
        if (block == null) {
            sender.sendMessage(TextFormat.RED + lang.translateString("commands.jump.unreachable"));
            return false;
        }
        double price = 150;
        econAPI = EconomyAPI.getInstance();
        if (econAPI.myMoney(player) < price){
            player.sendMessage(TextFormat.RED + "not enough money");
            return false;
        }
        player.teleport(api.getStandablePositionAt(block));
        sender.sendMessage(TextFormat.RED + "-" + econAPI.getMonetaryUnit() + price);
        econAPI.reduceMoney(player, price);
        return true;
    }
}
