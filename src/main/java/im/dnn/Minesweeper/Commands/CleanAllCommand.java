package im.dnn.Minesweeper.Commands;

import im.dnn.Minesweeper.Constants.Keys;
import im.dnn.Minesweeper.Constants.Permissions;
import im.dnn.Minesweeper.Managers.ExplosionManager;
import im.dnn.Minesweeper.Utils.Settings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CleanAllCommand implements CommandExecutor {
    private final ExplosionManager explosionManager;

    public CleanAllCommand (ExplosionManager explosionManager) {
        this.explosionManager = explosionManager;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (!player.hasPermission(Permissions.CLEAN_ALL)) {
                player.sendMessage(Settings.getString(Keys.MESSAGE_NOPERM_CLEAN_ALL));
                return false;
            }

            this.explosionManager.cleanAllMines();
            return true;
        }
        return false;
    }
}
