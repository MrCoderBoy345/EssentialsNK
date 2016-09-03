package cn.yescallop.essentialsnk.command.defaults;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.utils.TextFormat;
import cn.yescallop.essentialsnk.EssentialsNK;
import cn.yescallop.essentialsnk.command.CommandBase;

public class VanishCommand extends CommandBase {

    public VanishCommand(EssentialsNK plugin) {
        super("vanish", plugin);
    }

    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!this.testPermission(sender)) {
            return false;
        }
        Player player;
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(TextFormat.RED + lang.translateString("commands.generic.ingame"));
                return false;
            }
            player = (Player) sender;
        } else if (args.length == 1) {
            if (!sender.hasPermission("essentialsnk.vanish.other")) {
                sender.sendMessage(new TranslationContainer(TextFormat.RED + "%commands.generic.permission"));
                return false;
            }
            player = plugin.getServer().getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage(TextFormat.RED + lang.translateString("commands.generic.player.notFound", args[0]));
                return false;
            }
        } else {
            this.sendUsage(sender);
            return false;
        }
        boolean allow = plugin.switchVanish(player);
        player.sendMessage(allow ? lang.translateString("commands.vanish.enabled") : lang.translateString("commands.vanish.disabled"));
        if (sender != player) {
            sender.sendMessage(allow ? lang.translateString("commands.vanish.enabled.other", player.getName()) : lang.translateString("commands.vanish.disabled.other", player.getName()));
        }
        return true;
    }
}
