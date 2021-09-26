package im.dnn.Minesweeper.Managers;

import im.dnn.Minesweeper.Constants.Commons;
import im.dnn.Minesweeper.Constants.Keys;
import im.dnn.Minesweeper.Minesweeper;
import im.dnn.Minesweeper.Utils.Settings;
import im.dnn.Minesweeper.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Set;

public class ExplosionManager {
    private final Minesweeper context;
    HashMap<String, Location> placedMines;

    public ExplosionManager (Minesweeper context) {
        this.context = context;
        placedMines = new HashMap<>();
    }

    private String getLocationKey (Location location) {
        String world = location.getWorld().toString();
        String x = String.valueOf(location.getBlockX());
        String y = String.valueOf(location.getBlockY());
        String z = String.valueOf(location.getBlockZ());
        return String.format(Commons.LOCATION_TEMPLATE, world, x, y, z);
    }

    public void cleanAllMines () {
        placedMines.clear();
    }

    public void placeMine (Location location) {
        placedMines.put(getLocationKey(location), location);
    }

    public boolean isMine (Location location) {
        return placedMines.containsKey(getLocationKey(location));
    }

    public void summonExplosion(World world, Location steppedBlock) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.context, new Runnable(){
            @Override
            public void run(){
                placedMines.remove(getLocationKey(steppedBlock));
                float explosionRatio = (float) Settings.getDouble(Keys.MINES_EXPLOSION_RATIO);
                world.createExplosion(steppedBlock, explosionRatio, false, true);
            }
        }, 5L); // 20L = 20 ticks = 1 sec
    }

    public void defuseMine (Player player, Location mineLocation) {
        player.sendTitle(Settings.getString(Keys.MESSAGE_DEFUSED_TITLE), null, 5, 15 , 5);
        placedMines.remove(getLocationKey(mineLocation));
        Utils.playAmbientSound(player, mineLocation, Settings.getString(Keys.SOUND_ON_DEFUSE));
    }
}
