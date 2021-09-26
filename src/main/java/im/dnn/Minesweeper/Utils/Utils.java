package im.dnn.Minesweeper.Utils;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.List;

public class Utils {
    public static void playAmbientSound (Player player, Location soundOrigin, String sound) {
        player.playSound(soundOrigin, Sound.valueOf(sound), SoundCategory.AMBIENT, 1, 1);

        List<Entity> nearbyEntitis = player.getNearbyEntities(0,0,0);

        for (Entity entity: nearbyEntitis) {
            if (entity.getType() == EntityType.PLAYER) {
                Player nearbyPlayer = (Player) entity;
                nearbyPlayer.playSound(soundOrigin, Sound.valueOf(sound), SoundCategory.AMBIENT, 1, 1);
            }
        }
    }
}
