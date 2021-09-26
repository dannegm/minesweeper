
package im.dnn.Minesweeper.Commands;

import im.dnn.Minesweeper.Constants.Keys;
import im.dnn.Minesweeper.Constants.Permissions;
import im.dnn.Minesweeper.Minesweeper;
import im.dnn.Minesweeper.Models.Mine;
import im.dnn.Minesweeper.Utils.Settings;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetMineCommand implements CommandExecutor {
    private final Minesweeper context;

    public GetMineCommand (Minesweeper context) {
        this.context = context;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (!player.hasPermission(Permissions.GET)) {
                player.sendMessage(Settings.getString(Keys.MESSAGE_NOPERM_GET));
                return false;
            }

            Material requestedMaterial = args.length > 0 ? Material.getMaterial(args[0]) : Material.GRASS_BLOCK;

            int amount = args.length > 0 ? Integer.parseInt(args[1]) : 1;
            Mine requestedMine = new Mine(this.context, requestedMaterial);
            player.getInventory().addItem(requestedMine.getItem(amount));
            return true;
        }
        return false;
    }
}
