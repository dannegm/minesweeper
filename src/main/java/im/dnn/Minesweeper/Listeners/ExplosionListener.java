package im.dnn.Minesweeper.Listeners;

import im.dnn.Minesweeper.Constants.Keys;
import im.dnn.Minesweeper.Constants.Permissions;
import im.dnn.Minesweeper.Managers.ExplosionManager;
import im.dnn.Minesweeper.Minesweeper;
import im.dnn.Minesweeper.Utils.Logger;
import im.dnn.Minesweeper.Utils.Settings;
import im.dnn.Minesweeper.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

public class ExplosionListener implements Listener {
    private final Minesweeper context;
    private final ExplosionManager explosionManager;
    private final List<Material> defuseTools;

    public ExplosionListener (Minesweeper context, ExplosionManager explosionManager) {
        this.context = context;
        this.explosionManager = explosionManager;
        this.defuseTools = new ArrayList<>();

        for(String defuseToolMaterial: Settings.getStringList(Keys.DEFUSED_TOOLS)) {
            this.defuseTools.add(Material.getMaterial(defuseToolMaterial));
        }
    }

    private void summonPlayerExplosion (Player player, Location explodingBlock) {
        if (explosionManager.isMine(explodingBlock)) {
            Utils.playAmbientSound(player, explodingBlock, Settings.getString(Keys.SOUND_ON_WILL_EXPLODE));
            if (!player.hasPermission(Permissions.BYPASS)) {
                explosionManager.summonExplosion(player.getWorld(), explodingBlock);
            } else {
                Logger.info(player.getName() + " has avoided explosion");
            }
        }
    }

    @EventHandler
    public void onPlace (BlockPlaceEvent event) {
        Material placedMaterial = event.getBlock().getBlockData().getMaterial();

        if (this.defuseTools.contains(placedMaterial)) {
            Location explodedBlock = event.getBlock().getRelative(BlockFace.DOWN).getLocation();
            if (explosionManager.isMine(explodedBlock)) {
                Player player = event.getPlayer();
                if (player.hasPermission(Permissions.DEFUSE)) {
                    explosionManager.defuseMine(player, explodedBlock);
                } else {
                    player.sendMessage(Settings.getString(Keys.MESSAGE_NOPERM_DEFUSE));
                }
            }
        }
    }

    @EventHandler
    public void onPlayerMove (PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location steppedBlock = event.getTo().getBlock().getRelative(BlockFace.DOWN).getLocation();
        summonPlayerExplosion(player, steppedBlock);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Location interactedBlock = event.getBlock().getLocation();
        summonPlayerExplosion(player, interactedBlock);
    }

    @EventHandler
    public void onExplode (BlockExplodeEvent event){
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.context, new Runnable(){
            @Override
            public void run(){
                for (Block explodedBlock : event.blockList()) {
                    chainExplosions(explodedBlock);
                }
            }
        }, 3L); // 20L = 20 ticks = 1 sec
    }

    private void chainExplosions (Block explodedBlock) {
        if (explosionManager.isMine(explodedBlock.getLocation())) {
            Logger.info("Chained explosion");
            explosionManager.summonExplosion(explodedBlock.getWorld(), explodedBlock.getLocation());
        }
    }
}
