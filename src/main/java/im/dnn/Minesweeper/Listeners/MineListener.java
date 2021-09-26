package im.dnn.Minesweeper.Listeners;

import im.dnn.Minesweeper.Constants.Keys;
import im.dnn.Minesweeper.Constants.Permissions;
import im.dnn.Minesweeper.Managers.ExplosionManager;
import im.dnn.Minesweeper.Minesweeper;
import im.dnn.Minesweeper.Models.Mine;
import im.dnn.Minesweeper.Utils.Settings;
import im.dnn.Minesweeper.Utils.Utils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MineListener implements Listener {
    private final Minesweeper context;
    private final ExplosionManager explosionManager;
    private final List<Mine> mines;

    public MineListener (Minesweeper context, ExplosionManager explosionManager, List<Mine> mines) {
        this.context = context;
        this.explosionManager = explosionManager;
        this.mines = mines;
    }

    @EventHandler
    public void onPlace (BlockPlaceEvent event) {
        event.setCancelled(true);
        ItemStack item = event.getItemInHand();
        event.setCancelled(false);
        Player player = event.getPlayer();

        if (Mine.isMine(this.context, item)) {
            if (player.hasPermission(Permissions.PLACE)) {
                Location placedMine = event.getBlock().getLocation();
                this.explosionManager.placeMine(placedMine);
                Utils.playAmbientSound(player, placedMine, Settings.getString(Keys.SOUND_ON_PLACE));
            } else {
                player.sendMessage(Settings.getString(Keys.MESSAGE_NOPERM_PLACE));
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onCraftItem (CraftItemEvent event){
        int similarRecipes = 0;
        Player player = (Player) event.getWhoClicked();

        for(Mine mine: this.mines) {
            boolean isMine = event.getRecipe().getResult().isSimilar(mine.getRecipe().getResult());
            if (isMine) {
                similarRecipes += 1;
            }
        }

        if (similarRecipes > 0 && !player.hasPermission(Permissions.CRAFT)) {
            player.sendMessage(Settings.getString(Keys.MESSAGE_NOPERM_CRAFT));
            event.setCancelled(true);
        }
    }
}
